package files.test;

import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import files.app.Application;
import files.gui.ListView;
import files.gui.FileInteractionIF;
import files.gui.ListViewDetails;
import files.model.FileEntry;
import files.util.SinglePanelFrame;

public class TestColumnHeader {

	
	public static void main(String[] args) throws IOException
	{
		Application.launchApplication();
		
		ListView folderView = new ListViewDetails();
		
		FileInteractionIF interaction = new FileInteractionIF() {
			@Override
			public void doubleClick(FileEntry entry) {
				Path p = entry.getPath();
				
				if (Files.isDirectory(p))
				{
					folderView.show(p);
				}
				else if (Files.isRegularFile(p))
				{
					System.out.println("Open " + p);
				}
			}};
		folderView.setInteraction(interaction);
			
			
			
			
			
		SinglePanelFrame.showPanel(folderView, new Rectangle(500,500,500,500), "columns");
		folderView.show(Paths.get("."));
	}
}
