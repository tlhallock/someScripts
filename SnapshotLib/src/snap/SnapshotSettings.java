package snap;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class SnapshotSettings {
	LinkedList<SettingsListener> listeners = new LinkedList<>();

	boolean overwrite;
	String saveFormat;

	boolean askToSave;
	String saveDirectory;

	Rectangle currentLens;
	
	SnapshotSettings()
	{
		overwrite = false;
		saveFormat = "png";
		askToSave = true;
		saveDirectory = System.getProperty("user.home");
		currentLens = new Rectangle(500, 500, 50, 50);
	}

	void add(SettingsListener listener) {
		listeners.add(listener);
	}

	void remove(SettingsListener listener) {
		listeners.remove(listener);
	}

	void setOverwrite(boolean overwrite) {
		if (this.overwrite == overwrite)
			return;
		this.overwrite = overwrite;

		for (SettingsListener listener : listeners) {
			try {
				listener.setOverwrite(overwrite);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void setFormat(String format) {
		if (this.saveFormat.equals(format))
			return;
		this.saveFormat = format;

		for (SettingsListener listener : listeners) {
			try {
				listener.setFormat(format);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void setAskToSave(boolean value) {
		if (askToSave == value)
			return;
		askToSave = value;

		for (SettingsListener listener : listeners) {
			try {
				listener.setAskToSave(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void setSaveDirectory(String directory) {
		if (saveDirectory.equals(directory))
			return;
		saveDirectory = directory;

		for (SettingsListener listener : listeners) {
			try {
				listener.setSaveDirectory(directory);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void setLens(Rectangle rec) {
		if (currentLens.equals(rec))
			return;

		currentLens = rec;

		for (SettingsListener listener : listeners) {
			try {
				listener.setLens(rec);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
        
        
        
        public static String[] getImageFormatOptions()
        {
		String[] fileTypes = ImageIO.getReaderFormatNames();
		Arrays.sort(fileTypes, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.toLowerCase().compareTo(o2.toLowerCase());
			}
		});
                return fileTypes;
        }

	interface SettingsListener {
		void setOverwrite(boolean overwrite) throws Exception;

		void setFormat(String format) throws Exception;

		void setAskToSave(boolean value) throws Exception;

		void setSaveDirectory(String directory) throws Exception;

		void setLens(Rectangle rec) throws Exception;
	}
}
