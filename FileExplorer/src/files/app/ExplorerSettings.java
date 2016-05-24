package files.app;

import java.util.LinkedList;

import files.app.Logger.LogLevel;
import files.gui.FileViewHeader.SpacingPolicy;

public class ExplorerSettings {

	private LinkedList<SettingsListener> listeners = new LinkedList<>();

	private LogLevel level = LogLevel.Normal;
	private int defaultConsoleLines = 50;
	
	private boolean cutColumns = false;
	private int fontSize = 25;
	
	
	private String knownExtensionsPath = "knownExtensions.json";
	private String colorsPath = "colors.json";
	private String templatesPath = "templates.json";

	public ExplorerSettings()
	{

	}
	
	public boolean cutColumns()
	{
		return cutColumns;
	}
	
	public String getKnownExtensionsPath()
	{
		return knownExtensionsPath;
	}

	void setLogLevel(LogLevel level) {
		if (level.equals(this.level))
			return;

		this.level = level;

		for (SettingsListener listener : listeners) {
			listener.loggingChanged();
		}
	}

	LogLevel getCurrentLogLevel() {
		return level;
	}

	int getDefaultConsoleLines() {
		return defaultConsoleLines;
	}

	void setDefaultConsoleLines(int newVal) {
		defaultConsoleLines = newVal;
	}

	public interface SettingsListener {
		public void loggingChanged();
	}

	public int getFontSize() {
		return fontSize;
	}

	public String getColorsPath() {
		return colorsPath;
	}

	public String getTemplatesPath()
	{
		return templatesPath ;
	}

	public SpacingPolicy getDefaultSpacingPolicy()
	{
		return SpacingPolicy.EQUAL;
	}
}
