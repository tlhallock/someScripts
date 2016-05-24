package files.app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import files.app.Logger.LogLevel;

public final class ExtensionManager {
	

	public static final String UNKNOWN_DISPLAY_TYPE = "Unkown";
	public static final String DIRECTORY_DISPLAY_TYPE = "Directory";
	

	@JsonIgnore
	private HashMap<String, FileType> typesByName = new HashMap<>();
	@JsonIgnore
	private HashMap<String, FileType> typesByExtension = new HashMap<>();
	@JsonIgnore
	private LinkedList<ExtensionsListener> listeners = new LinkedList<>();
	
	private TreeSet<FileType> fileTypes = new TreeSet<>();
	
	private ExtensionManager()
	{
		
	}

	public String getDisplay(String ext) {
		if (ext == null) return null;
		FileType fileType = typesByExtension.get(ext);
		if (fileType == null)
		{
			return UNKNOWN_DISPLAY_TYPE;
		}
		return fileType.name;
	}
	
	void reset()
	{
		typesByName.clear();
		typesByExtension.clear();
		for (FileType fileType : fileTypes)
		{
			typesByName.put(fileType.name, fileType);
			for (String extension : fileType.extenions)
			{
				typesByExtension.put(extension, fileType);
			}
		}
	}
	

	public String setIfCompatible(FileType toOwn, Collection<String> exts)
	{
        for (String ext : exts)
        {
        	ext = ext.toLowerCase();
            FileType other = getFileTypeByExtension(ext);
            if (other != null && !other.equals(toOwn))
            {
                return "The extension \"" + ext + "\" is already of type " + other.getName();
            }
        }
        toOwn.setExtension(exts);
        typesChanged();
        return "Updated extensions.";
	}
	
	
	public String addIfCompatible(FileType fileType)
	{
		if (fileTypes.contains(fileType))
		{
			return fileType + " already present";
		}
		
		typesByName.put(fileType.name, fileType);
		for (String extension : fileType.extenions)
		{
			typesByExtension.put(extension, fileType);
		}
		fileTypes.add(fileType);
		typesChanged();
		return fileType + " added.";
	}
	public void remove(FileType fileType)
	{
		removeAll(Collections.singleton(fileType));
	}
	public void removeAll(Collection<FileType> toDelete) {
		fileTypes.removeAll(toDelete);
		typesChanged();
	}
	
	
	public FileType getFileTypeByExtension(String extension)
	{
		return typesByExtension.get(extension.toLowerCase().trim());
	}
	public FileType getFileTypeByName(String name)
	{
		return typesByName.get(name);
	}

	private void typesChanged() {
		reset();
		save();
		for (ExtensionsListener listener : listeners)
		{
			try {
				listener.extensionsChanged();
			} catch (Exception e) {
				Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to notify listener", e);
			}
		}
	}
	



	public Collection<FileType> getFileTypes() {
		return fileTypes;
	}

	public boolean hasFileType(FileType newFileType) {
		return fileTypes.contains(newFileType);
	}
	
	
	
	
	
	
	

	public void add(ExtensionsListener listener)
	{
		listeners.add(listener);
	}
	public void remove(ExtensionsListener listener)
	{
		listeners.remove(listener);
	}

    public String updateNameIfPossible(FileType currentlyViewing, String text) {
        if (text == null || text.length() == 0)
        {
            return "No name!!!";
        }
        if (currentlyViewing == null)
        {
            return "No file type selected!";
        }
        if (currentlyViewing.name.equals(text))
        {
            return "This is already the name!";
        }
        
        FileType type = typesByName.get(text);
        if (type != null && !type.equals(currentlyViewing))
        {
            return "Type already exists!";
        }
        
        String oldName = currentlyViewing.name;
        fileTypes.remove(currentlyViewing);
        currentlyViewing.name = text;
        fileTypes.add(currentlyViewing);
        typesChanged();
        return "Type name changed from \"" + oldName + "\" to \"" + text + "\"";
    }
	
	
	
	
	
	
	
    

	public static ExtensionManager loadHumanReadable(Path path) throws IOException
	{
		return Serialization.writer.read(path.toFile(), ExtensionManager.class);
	}

	public void writeHumanReadable(Path path) throws IOException
	{
		Serialization.writer.write(path.toFile(), this);
	}
	public void save()
	{
		try {
			writeHumanReadable(Paths.get(Application.getApplication().getSettings().getKnownExtensionsPath()));
		} catch (IOException e) {
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to write extensions file", e);
		}
	}
	
	
	
	
	public static ExtensionManager load()
	{
		Path path = Paths.get(Application.getApplication().getSettings().getKnownExtensionsPath());
		try {
			return loadHumanReadable(path);
		} catch (IOException e) {
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to read extensions file");
			e.printStackTrace();
			try {
				new ExtensionManager().save();
				return loadHumanReadable(path);
			} catch (IOException e2) {
				Application.getApplication().getLogger().log(LogLevel.Minimal, "Unable to read extensions file", e2);
				return new ExtensionManager();
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final class FileType implements Comparable<FileType>
	{
		private String name;
		private LinkedList<String> launchArguments = new LinkedList<>();
		private TreeSet<String> extenions = new TreeSet<>();

		public FileType(String name)
		{
			this.name = name;
		}

		private FileType()
		{
		}

		public void setExtension(Collection<String> exts)
		{
			extenions.clear();
			for (String ext : exts)
			{
				extenions.add(ext.toLowerCase().trim());
			}
		}

		@Override
		public boolean equals(Object other)
		{
			if (!(other instanceof FileType))
				return false;
			FileType o = (FileType) other;
			return o.name.equals(name);
		}

		@Override
		public int hashCode()
		{
			return name.hashCode();
		}

		@Override
		public String toString()
		{
			return name;
		}

		public Collection<String> getExtensions()
		{
			LinkedList<String> sorted = new LinkedList<>();
			sorted.addAll(extenions);
			Collections.sort(sorted);
			return sorted;
		}

		public String getName()
		{
			return name;
		}

		public Collection<String> getCommands()
		{
			return launchArguments;
		}
		
		public void setName(String text)
		{
			name = text;
			Application.getApplication().getKnownFileTypes().typesChanged();
		}

		public void setCommands(String text)
		{
			launchArguments.clear();
			launchArguments.add(text);
			Application.getApplication().getKnownFileTypes().typesChanged();
		}

		@Override
		public int compareTo(FileType o)
		{
			return name.compareTo(o.name);
		}
	}
	
	
	
	public interface ExtensionsListener
	{
		public void extensionsChanged() throws Exception;
	}



	public FileType createNewFileType() {
		return new FileType("New file type " + new Date(System.currentTimeMillis()));
	}
}
