package files.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import files.app.Application;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import files.model.FileEntryAttributes.FileEntryAttributeValue;

public class FileViewSimple extends JPanel {
	private FileEntry entry;

	public FileViewSimple(FileEntry entry) {
		this.entry = entry;
	}

	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;

		Color color = Application.getApplication().getColorSelector().getSimpleViewBackground();
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());

		FileEntryAttributeValue value = entry.get(FileEntryAttributeKey.All_Name);
		if (value == null)
			return;

		PaintUtils.paintString(g, value.display(), 0, getWidth(), getHeight(), true, false, false);
		PaintUtils.paintBorder(g, getSize(), color);
	}
}
