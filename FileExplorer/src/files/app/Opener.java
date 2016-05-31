package files.app;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import files.app.ExtensionManager.FileType;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import files.model.FileEntryAttributes.FileEntryAttributeValue;

public interface Opener
{

	public boolean open(FileEntry entry);
	

	static class DefaultOpener implements Opener
	{
		private Desktop desktop;
		
		private DefaultOpener(Desktop desktop)
		{
			this.desktop = desktop;
		}

		public boolean open(FileEntry entry)
		{
			try
			{
				desktop.open(entry.getPath().toFile());
				return true;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}
	}
	

	static class FiletypesOpener implements Opener
	{
		private FiletypesOpener() {}
		
		public boolean open(FileEntry entry)
		{
			FileEntryAttributeValue fileEntryAttributeValue = entry.get(FileEntryAttributeKey.File_Extension);
			if (fileEntryAttributeValue == null)
				return false;
			String val = fileEntryAttributeValue.toString();
			ExtensionManager manager = Application.getApplication().getKnownFileTypes();
			FileType fileTypeByExtension = manager.getFileTypeByExtension(val);
			if (fileTypeByExtension == null)
				return false;
			
			LinkedList<String> command = new LinkedList<>();
			fileTypeByExtension.getCommands().stream().forEach(x -> command.addLast(x));
			command.add(entry.getPath().toString());
			

			ProcessBuilder builder = new ProcessBuilder(command);
			builder.directory(new File(FileEntry.getParentPath(entry.getPath())));
			try
			{
				builder.start();
				return true;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return false;
			}

		}
	}
	
	static class ChainOpener implements Opener
	{
		Opener parent;
		Opener current;

		private ChainOpener(Opener parent, Opener current)
		{
			this.parent = parent;
			this.current = current;
		}

		public boolean open(FileEntry entry)
		{
			if (current.open(entry))
			{
				return true;
			}
			else
			{
				return parent.open(entry);
			}
		}
	}

	static Opener createOpener()
	{
		Opener opener = new FiletypesOpener();
		
		if (Desktop.isDesktopSupported())
		{
			Desktop desktop = Desktop.getDesktop();
			DefaultOpener dopener = new DefaultOpener(desktop);
			opener = new ChainOpener(dopener, opener);
		}
		
		return opener;
	}
}
