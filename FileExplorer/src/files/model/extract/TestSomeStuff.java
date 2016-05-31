package files.model.extract;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import files.gui.CopyTaskCompletionPanel;
import files.model.extract.Nothingness.DebugCopyTaskCompletionListener;
import files.model.extract.Nothingness.TimedCopyTaskCompletionListener;
import files.util.SinglePanelFrame;

public class TestSomeStuff
{

	public static void main(String[] args) throws IOException
	{

		PrintStream ps = new PrintStream(new File("log.txt"));
		Path source		= Paths.get("/home/thallock/Documents/Source/temp/someScripts/FileExplorer/test_directories");
		Path destination 	= Paths.get("/home/thallock/Documents/Source/temp/someScripts/FileExplorer/test_output");
		
		
		CopyTask copyTask = new CopyTask(source, destination);
		copyTask.addListener(new Nothingness.TimedCopyTaskCompletionListener(1000, new Nothingness.DebugCopyTaskCompletionListener() {
			@Override
			void handle(String string)
			{
				ps.println(string);
				System.out.println(string);
				
			}}));
		

		CopyTaskCompletionPanel panel = new CopyTaskCompletionPanel();
		SinglePanelFrame.showPanel(panel, new Rectangle(50,50,500,500), "Progress");

		copyTask.addListener(new Nothingness.TimedCopyTaskCompletionListener(1000, panel));
		
		copyTask.run();
		
		ps.close();
	}
}