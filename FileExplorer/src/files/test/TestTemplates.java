package files.test;

import java.awt.Rectangle;
import java.io.IOException;

import files.app.Application;
import files.gui.RootViewTemplates;
import files.util.SinglePanelFrame;

public class TestTemplates
{
	public static void main(String[] args) throws IOException
	{
		Application.launchApplication();
		
		RootViewTemplates folderView1 = new RootViewTemplates();
		SinglePanelFrame.showPanel(folderView1, new Rectangle(500,500,500,500), "viewer1");

		RootViewTemplates folderView2 = new RootViewTemplates();
		SinglePanelFrame.showPanel(folderView2, new Rectangle(500,550,550,500), "viewer2");
	}
}
