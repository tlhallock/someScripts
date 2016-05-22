package files.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import files.app.Application;
import files.app.ExtensionManager.FileType;
import files.app.Logger.LogLevel;
import files.gui.DetailsHeaderView.Column;
import files.gui.DetailsHeaderView.Columns;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeValue;

public class DetailsFileView extends JPanel
{
	private static final int COLUMN_DIVIDE_WIDTH = 2;
	
	private FileEntry entry;
	private Columns columns;
	private FileInteraction interaction;
	
	private boolean even;
	private boolean highlighted;
	private boolean marked;
	
	public DetailsFileView(Columns columns, FileEntry entry, FileInteraction intera)
	{
		this.entry = entry;
		this.columns = columns;
		this.interaction = intera;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() < 2)
					return;
				interaction.doubleClick(entry);
			}
		});
		
		JPanel p = this;
		JPopupMenu popup = new JPopupMenu();
		JMenuItem item = new JMenuItem("Set File type options...");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExtensionViewer launchFileTypeOptions = Application.getApplication().launchFileTypeOptions(p.getLocationOnScreen());
				String extension = entry.getExtension();
				if (extension == null) return;
				FileType type = Application.getApplication().getKnownFileTypes().getFileTypeByExtension(extension);
				if (type == null)
				{
					type = Application.getApplication().getKnownFileTypes().createNewFileType();
					Application.getApplication().getLogger().log(LogLevel.Normal, Application.getApplication().getKnownFileTypes().addIfCompatible(type));
					Application.getApplication().getKnownFileTypes().setIfCompatible(type, Collections.singleton(extension));
				}
				launchFileTypeOptions.select(type);
			}
		});
		popup.add(item);
		setComponentPopupMenu(popup);
	}
	
	FileEntry getEntry()
	{
		return entry;
	}
	
	void setEven(boolean e)
	{
		even = e;
	}
	void setMarked(boolean sel)
	{
		this.marked = sel;
	}
	void setHighlighted(boolean sel)
	{
		this.highlighted = sel;
	}
	
	public void setInteraction(FileInteraction interaction) {
		this.interaction = interaction;
	}
	
	@Override
	public void paint(Graphics graphics)
	{
		Graphics2D g = (Graphics2D) graphics;

		
		Color fileBackgroundColor = Application.getApplication().getColorSelector().getFileBackgroundColor(even, marked, highlighted);
		g.setColor(fileBackgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Application.getApplication().getColorSelector().getFileForegroundColor(even, marked, highlighted));
		for (int i = 0; i < columns.size(); i++)
		{
			Column column = columns.get(i);
			if (!column.visible)
				continue;
			FileEntryAttributeValue value = entry.get(column.key);
			if (value == null)
				continue;
			
			PaintUtils.paintString(g, value.display(),
					column.start + 2 * COLUMN_DIVIDE_WIDTH,
					(i == columns.size()-1 ? getWidth() : columns.get(i+1).start),
					getHeight(), true, false, false);
		}
		

		g.setColor(Application.getApplication().getColorSelector().getFileDividerColor(even, marked, highlighted));
		for (int i = 0; i < columns.size(); i++)
		{
			if (i==0)
				continue;
			Column column = columns.get(i);
			if (!column.visible)
				continue;
			
			g.fillRect(column.start, 0, COLUMN_DIVIDE_WIDTH, getHeight());
		}

		PaintUtils.paintBorder(g, getSize(), fileBackgroundColor);
	}

	public FileEntryAttributeValue get(int column) {
		return entry.get(columns.get(column).key);
	}
}
