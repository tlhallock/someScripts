package files.gui;

import java.util.Collections;
import java.util.Comparator;

import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import files.model.FileEntryAttributes.FileEntryAttributeValue;

public class FolderViewListDetails extends FolderViewListHorizontal<FileViewHorizontal>
{
	FolderViewListDetails(FileEntryAttributeKey[] attributes)
	{
		super(attributes);
	}
    
	protected FileViewHorizontal createFileView(FileViewHeader.Columns columns, FileEntry entry, FileInteractionIF interaction, FileFilterer filterer)
	{
		return new FileViewHorizontal(columns, entry, interaction, filterer);
	}

	@Override
	protected boolean getShowNameFirst()
	{
		return false;
	}

	@Override
	protected boolean getFixedColumns()
	{
		return false;
	}
	
	protected void sortColumns(int column, boolean reverse)
	{
		synchronized(details)
		{
			Collections.sort(details, new Comparator<FileViewHorizontal>(){
				@Override
				public int compare(FileViewHorizontal o1, FileViewHorizontal o2) {
					FileEntryAttributeValue display1 = o1.get(column);
					FileEntryAttributeValue display2 = o2.get(column);
					int cmp = 0;
					if (display1 == null)
						cmp = display2 == null ? 0 : 1;
					else if (display2 == null)
						cmp = -1;
					else
						cmp = display1.compareTo(display2);
					return (reverse ? -1 : 1) * cmp;
				}});
		}
	}
}
