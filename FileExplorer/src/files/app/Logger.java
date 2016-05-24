package files.app;

import java.util.Comparator;
import java.util.TreeSet;

import files.gui.RootViewConsole;
import files.util.ComponentShowingListener;


public class Logger
{
	TreeSet<RootViewConsole> consoles = new TreeSet<>(new Comparator<RootViewConsole>() {
		@Override
		public int compare(RootViewConsole o1, RootViewConsole o2) {
			return Integer.compare(System.identityHashCode(o1), System.identityHashCode(o2));
		}
	});
	
	public void log(LogLevel level, String text)
	{
		if (!getLogLevel().logs(level))
			return;

		if (text.charAt(text.length()-1) != '\n')
			text = text + '\n';
		
		for (RootViewConsole console : consoles)
			console.log(level, text);
		
		System.out.println(text);
	}

	public void log(LogLevel level, String text, Throwable t)
	{
		if (!getLogLevel().logs(level))
			return;

		if (text.charAt(text.length()-1) != '\n')
			text = text + '\n';
		
		text += org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(t);
		
		for (RootViewConsole console : consoles)
			console.log(level, text);
		
		System.out.println(text);
		t.printStackTrace();
	}
	
	public LogLevel getLogLevel()
	{
		ExplorerSettings settings = Application.getApplication().getSettings();
		if (settings == null)
		{
			return LogLevel.Verbose;
		}
		
		return settings.getCurrentLogLevel();
	}

	public RootViewConsole createConsole()
	{
		RootViewConsole console = new RootViewConsole();
		
		new ComponentShowingListener(console) {
			@Override
			protected void isShowing() {
				consoles.add(console);
				log(LogLevel.Normal, "console activated");
			}
			@Override
			protected void isNotShowing() {
				consoles.remove(console);
				log(LogLevel.Normal, "console deactivated");
			}
		};
		
		return console;
	}
	
	
	
	
	
    public enum LogLevel
    {
        Verbose,
        Normal,
        Minimal,
        None;
        
		public boolean logs(LogLevel other) {
			switch (this) {
			case Verbose:
				return true;
			case Normal:
				return !other.equals(Verbose);
			case Minimal:
				return !other.equals(Verbose) && !other.equals(Normal);
			case None:
				return false;
			default:
				throw new RuntimeException("Missing level!");
			}
        }
    }
}
