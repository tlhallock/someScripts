package files.gui;

import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import java.awt.Dimension;
import java.util.LinkedList;

public class ListViewGrid extends ListView<FileViewGrid>
{

	public ListViewGrid(FileEntryAttributeKey[] attributes)
	{
		super(attributes);
	}

    @Override
    protected boolean getShowNameFirst() {
        return true;
    }

    @Override
    protected boolean getFixedColumns() {
        return true;
    }
    

	protected FileViewGrid createFileView(Columns columns, FileEntry entry, FileInteractionIF interaction)
	{
		return new FileViewGrid(columns, entry, interaction);
	}

    @Override
    protected Dimension setLocations(LinkedList<FileViewGrid> toShow, int w) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void sortColumns(int column, boolean reverse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
