package files.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import files.app.Logger.LogLevel;

public class TemplateManager
{
	private TreeSet<Template> knownTemplates = new TreeSet<>();

	@JsonIgnore
	private LinkedList<TemplatesListener> templateListeners = new LinkedList<>();

	private TemplateManager()
	{

	}

	public void add(TemplatesListener t)
	{
		templateListeners.add(t);
	}

	public void remove(TemplatesListener t)
	{
		templateListeners.remove(t);
	}

	private void templatesChanged()
	{
		save();
		for (TemplatesListener listener : templateListeners)
		{
			try
			{
				listener.templatesChanged();
			} catch (Exception ex)
			{
				Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to notify of template change.", ex);
			}
		}
	}

	public void addTemplate(Template template)
	{
		knownTemplates.add(template);
		templatesChanged();
	}

	public void removeTemplate(Template template)
	{
		knownTemplates.remove(template);
		templatesChanged();
	}

	public void removeAll(Collection<Template> toDelete)
	{
		knownTemplates.removeAll(toDelete);
		templatesChanged();
	}

	public Collection<Template> getTemplates()
	{
		return knownTemplates;
	}

	public void replace(Template oldTemplate, Template newTemplate)
	{

		knownTemplates.remove(oldTemplate);
		knownTemplates.add(newTemplate);
		templatesChanged();
	}

	public boolean hasTemplate(Template newFileType)
	{
		return knownTemplates.contains(newFileType);
	}

	public static class Template implements Comparable<Template>
	{
		private String displayName;
		private String fileLocation;

		private Template() {}
		public Template(String displayName, String p)
		{
			this.displayName = displayName;
			this.fileLocation = p;
		}

		@Override
		public boolean equals(Object other)
		{
			if (!(other instanceof Template))
				return false;
			return ((Template) other).displayName.equals(displayName);
		}

		@Override
		public int compareTo(Template o)
		{
			return displayName.compareTo(o.displayName);
		}

		@Override
		public int hashCode()
		{
			return displayName.hashCode();
		}

		public String getDisplayName()
		{
			return displayName;
		}

		public String getFileLocation()
		{
			return fileLocation;
		}
		
		public String toString()
		{
			return displayName;
		}
		

		public void create(Path displaying)
		{
			File templateFile = new File(getFileLocation());
			if (!templateFile.exists() || !templateFile.isFile())
			{
				Application.getApplication().getLogger().log(LogLevel.Normal, "Template file does not exist: " + templateFile);
				return;
			}
			String name2 = templateFile.getName();
			if (name2 == null)
			{
				Application.getApplication().getLogger().log(LogLevel.Normal, "Template file has no name: " + templateFile);
				return;
			}

			String extension = "";
			
			int extIndex = name2.lastIndexOf('.');
			if (extIndex > 0 && extIndex < name2.length())
			{
				extension = name2.substring(extIndex);
				name2 = name2.substring(0, extIndex);
				
			}
			
			int index = 0;
			File destinationFile = null;
			do
			{
				destinationFile = new File(displaying.toString() + File.separator + name2 + (index == 0 ? "" : ("_" + index)) + extension);
				index++;
			}
			while (destinationFile.exists());
			
			try
			{
				FileUtils.copyFile(templateFile, destinationFile);
				Application.getApplication().getLogger().log(LogLevel.Normal, "File " + destinationFile + " created.");
			} catch (IOException e1)
			{
				Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to copy file: " + templateFile + " to " + destinationFile, e1);
			}
		}
	}

	
	
	
	public static interface TemplatesListener
	{
		public void templatesChanged() throws Exception;
	}

	
	
	
	
	
	
	public static TemplateManager loadHumanReadable(Path path) throws IOException
	{
		return Serialization.writer.read(path.toFile(), TemplateManager.class);
	}

	public void writeHumanReadable(Path path) throws IOException
	{
		Serialization.writer.write(path.toFile(), this);
	}

	public void save()
	{
		try
		{
			writeHumanReadable(Paths.get(Application.getApplication().getSettings().getTemplatesPath()));
		}
		catch (IOException e)
		{
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to write extensions file", e);
		}
	}

	public static TemplateManager load()
	{
		Path path = Paths.get(Application.getApplication().getSettings().getTemplatesPath());
		try
		{
			return loadHumanReadable(path);
		}
		catch (IOException e)
		{
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to read extensions file");
			e.printStackTrace();
			try
			{
				new TemplateManager().save();
				return loadHumanReadable(path);
			}
			catch (IOException e2)
			{
				Application.getApplication().getLogger().log(LogLevel.Minimal, "Unable to read extensions file", e2);
				return new TemplateManager();
			}
		}
	}

}
