package files.app;

import java.util.LinkedList;

import files.app.Logger.LogLevel;
import files.gui.FileViewHeader.SpacingPolicy;
import files.gui.FolderViewListTree;
import files.gui.RootViewFolderView;
import files.model.FileEntry;
import files.model.FileEntryAttributes;

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
        
        
        
        
        
        
        
        
        
        
    public FileEntryAttributes.FileEntryAttributeKey[] getDefaultTreeColumns() {
        return new FileEntryAttributes.FileEntryAttributeKey[]{
            FileEntryAttributes.FileEntryAttributeKey.All_Name,
            FileEntryAttributes.FileEntryAttributeKey.File_Length};
    }

    public FileEntryAttributes.FileEntryAttributeKey[] getDefaultColumnColumns() {
        return new FileEntryAttributes.FileEntryAttributeKey[]{
            FileEntryAttributes.FileEntryAttributeKey.All_Name,
            FileEntryAttributes.FileEntryAttributeKey.File_Length,};
    }

    public FileEntryAttributes.FileEntryAttributeKey[] getDefaultDetailsColumns() {
        return new FileEntryAttributes.FileEntryAttributeKey[]{
            FileEntryAttributes.FileEntryAttributeKey.All_Name,
            FileEntryAttributes.FileEntryAttributeKey.File_Length,
            FileEntryAttributes.FileEntryAttributeKey.File_LastModified,
            FileEntryAttributes.FileEntryAttributeKey.File_Created,
            FileEntryAttributes.FileEntryAttributeKey.File_Permissions,
            FileEntryAttributes.FileEntryAttributeKey.File_Type,};
    }

    public FileEntryAttributes.FileEntryAttributeKey[] getDefaultGridColumns() {
        return new FileEntryAttributes.FileEntryAttributeKey[]{
            FileEntryAttributes.FileEntryAttributeKey.All_Name
        };
    }
}
