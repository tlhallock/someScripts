package files.gui;

import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;

public class FileViewGrid extends FileView
{

	public FileViewGrid(Columns columns, FileEntry entry, FileInteractionIF interaction)
	{
		super(columns, entry, interaction);
	}

}
