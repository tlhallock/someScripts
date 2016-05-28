package files.test;

import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import files.app.Application;
import files.gui.FolderViewList;
import files.gui.FileInteractionIF;
import files.gui.FolderExplorers;
import files.gui.FolderViewListDetails;
import files.model.FileEntry;
import files.util.SinglePanelFrame;

public class TestAllTheViews {

	
	public static void main(String[] args) throws IOException
	{
		Application.launchApplication();
                FileEntry entry = FileEntry.load(Paths.get("."));
		SinglePanelFrame.showPanel(FolderExplorers.createColumnsView(entry), new Rectangle(500,500,500,500), "columns");
		SinglePanelFrame.showPanel(FolderExplorers.createDetailsView(entry), new Rectangle(510,500,500,500), "detials");
		SinglePanelFrame.showPanel(FolderExplorers.createTreeView(entry), new Rectangle(520,500,500,500), "tree");
//		SinglePanelFrame.showPanel(FolderExplorers.createGridView(entry), new Rectangle(530,500,500,500), "grid");
	}
}
