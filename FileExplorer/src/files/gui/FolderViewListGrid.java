package files.gui;

import java.awt.Dimension;
import java.util.LinkedList;

import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;

public class FolderViewListGrid extends FolderViewList<FileViewGrid>
{
    FolderViewListGrid(FileEntryAttributeKey[] attributes) {
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

    protected FileViewGrid createFileView(Columns columns, FileEntry entry, FileInteractionIF interaction, FileFilterer filterer) {
        return new FileViewGrid(columns, entry, interaction, filterer);
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
