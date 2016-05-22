package files.test;

import java.awt.Rectangle;
import java.io.IOException;

import files.app.Application;
import files.gui.ColumnView;
import files.util.SinglePanelFrame;

public class TestMultiplePanes {

	public static void main(String[] args) throws IOException
	{
		Application.launchApplication();
		
		ColumnView columnView = new ColumnView();
		
		SinglePanelFrame.showPanel(columnView, new Rectangle(500,500,500,500), "columns");
	}
}
