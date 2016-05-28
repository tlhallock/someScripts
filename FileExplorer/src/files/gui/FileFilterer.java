package files.gui;

import files.model.FileEntry;

public interface FileFilterer
{
	boolean filter(FileEntry entry);
}
