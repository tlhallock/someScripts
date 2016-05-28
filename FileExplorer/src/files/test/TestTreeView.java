//package files.test;
//
//import java.awt.Rectangle;
//import java.io.IOException;
//import java.nio.file.Paths;
//
//import files.app.Application;
//import files.gui.RootViewExplorerTree;
//import files.model.FileEntry;
//import files.util.SinglePanelFrame;
//
//public class TestTreeView
//{
//
//	public static void main(String[] args) throws IOException
//	{
//		Application.launchApplication();
//		
//		RootViewExplorerTree folderView = new RootViewExplorerTree();
//		SinglePanelFrame.showPanel(folderView, new Rectangle(500,500,500,500), "columns");
//		folderView.show(FileEntry.load(Paths.get(".")));
//	}
//}
