package snap;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class SnapshotManager
 {
	private Lens lens;
	private SnapshotSettings settings;
	private Robot camera;

	public SnapshotManager(Lens lens, SnapshotSettings settings, Robot camera) {
		this.lens = lens;
		this.settings = settings;
		this.camera = camera;
	}

	public Temp showAGui() {
		Temp temp = new Temp(this);
		temp.setVisible(true);
		return temp;
	}

	public void showSettings(Rectangle bounds) {
		SettingsPanel panel = new SettingsPanel(settings);
		SinglePanelFrame.showPanel(panel, bounds, "Settings");
	}

	private void save(BufferedImage image, File path) {
		if (path.exists()) {
			System.out.println("A file already exists at " + path);
			if (settings.overwrite) {
				System.out.println("deleting...");
			} else {
				System.out.println("aborting...");
				return;
			}
		}
		try {
			ImageIO.write(image, settings.saveFormat, path);
			System.out.println("Saved image to " + path);
		} catch (IOException e) {
			System.out.println("Saving failed.");
			e.printStackTrace();
		}
	}

	public void snap() {
		BufferedImage createScreenCapture = camera.createScreenCapture(lens.getWindow());

		if (!settings.askToSave) {
			save(createScreenCapture, new File(settings.saveDirectory + File.separator + System.currentTimeMillis() + "." + settings.saveFormat));
			return;
		}

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(settings.saveDirectory));
		if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		save(createScreenCapture, chooser.getSelectedFile());
	}

	public void showLens() {
		lens.focus();
	}

	public void hideLens() {
		lens.cover();
	}

	public static SnapshotManager launchSnapshotManager() throws AWTException {

		Lens lens = new Lens();
		SnapshotSettings settings = new SnapshotSettings();
		SnapshotManager manager = new SnapshotManager(lens, settings, new Robot());
		return manager;
	}

    void dockLens()
    {
        Point location = MouseInfo.getPointerInfo().getLocation();
        lens.setBounds(new Rectangle(location.x, location.y, 0, 0));
    }

    void setLensOnTop(boolean selected) {
        lens.setOnTop(selected);
    }
}
