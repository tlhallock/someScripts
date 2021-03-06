package files.test;

import java.awt.Rectangle;
import java.io.IOException;

import files.app.Application;
import files.gui.RootViewExtensions;
import files.util.SinglePanelFrame;

public class TestExtensionViewer {

	
	public static void main(String[] args) throws IOException
	{
		Application.launchApplication();
		
		RootViewExtensions folderView1 = new RootViewExtensions();
		SinglePanelFrame.showPanel(folderView1, new Rectangle(500,500,500,500), "viewer1");

		RootViewExtensions folderView2 = new RootViewExtensions();
		SinglePanelFrame.showPanel(folderView2, new Rectangle(500,550,550,500), "viewer2");
	}
}
