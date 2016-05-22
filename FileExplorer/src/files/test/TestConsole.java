package files.test;
import java.awt.Rectangle;

import junit.framework.TestCase;

import org.junit.Assert;

import files.app.Application;
import files.app.Logger.LogLevel;
import files.util.SinglePanelFrame;


public class TestConsole extends TestCase
{
	
	public static void main(String[] args) throws InterruptedException
	{
		Application.launchApplication();
		
		SinglePanelFrame.showPanel(Application.getApplication().getLogger().createConsole(), new Rectangle(500,500,500,500), "console 1");
		SinglePanelFrame.showPanel(Application.getApplication().getLogger().createConsole(), new Rectangle(550,550,500,500), "console 2");
		
		
		for (int i=0;i<1000;i++)
		{
			Application.getApplication().getLogger().log(LogLevel.Normal, "foobar");
			Thread.sleep(1000);
		}
	}
	
	public void testLogLevels()
	{
		Assert.assertFalse(LogLevel.None.logs(LogLevel.None));
		Assert.assertFalse(LogLevel.None.logs(LogLevel.Minimal));
		Assert.assertFalse(LogLevel.None.logs(LogLevel.Normal));
		Assert.assertFalse(LogLevel.None.logs(LogLevel.Verbose));
		

		Assert.assertTrue(LogLevel.Minimal.logs(LogLevel.None));
		Assert.assertTrue(LogLevel.Minimal.logs(LogLevel.Minimal));
		Assert.assertFalse(LogLevel.Minimal.logs(LogLevel.Normal));
		Assert.assertFalse(LogLevel.Minimal.logs(LogLevel.Verbose));
		

		Assert.assertTrue(LogLevel.Normal.logs(LogLevel.None));
		Assert.assertTrue(LogLevel.Normal.logs(LogLevel.Minimal));
		Assert.assertTrue(LogLevel.Normal.logs(LogLevel.Normal));
		Assert.assertFalse(LogLevel.Normal.logs(LogLevel.Verbose));
		

		Assert.assertTrue(LogLevel.Verbose.logs(LogLevel.None));
		Assert.assertTrue(LogLevel.Verbose.logs(LogLevel.Minimal));
		Assert.assertTrue(LogLevel.Verbose.logs(LogLevel.Normal));
		Assert.assertTrue(LogLevel.Verbose.logs(LogLevel.Verbose));
	}
}
