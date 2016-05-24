package files.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import files.app.Application;
import files.gui.FileViewHeader.Column;
import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import files.model.FileEntryAttributes.FileEntryAttributeValue;

public class FileViewHorizontal extends FileView
{
	private static final int COLUMN_DIVIDE_WIDTH = 2;

	private static final int WIDTH_BUFFER = 10;
	
	private boolean even;
	
	private int[] currentWidths = new int[FileEntryAttributeKey.values().length];
	
	public FileViewHorizontal(Columns columns, FileEntry entry, FileInteractionIF intera)
	{
		super(columns, entry, intera);
		
		for (int i=0;i<currentWidths.length;i++)
			currentWidths[i] = -1;
	}
	
	void setEven(boolean e)
	{
		even = e;
	}
	
	int getWidth(FileEntryAttributeKey key)
	{
		if (filtered) return 0;
		return currentWidths[key.ordinal()] + WIDTH_BUFFER;
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
			
			
			FileEntryAttributeValue value = entry.get(column.key);
			if (value == null)
			{
				currentWidths[column.getKey().ordinal()] = 0;
				continue;
			}
			String display = value.display();
			if (value == null || value.display() == null)
			{
				currentWidths[column.getKey().ordinal()] = 0;
				continue;
			}

			if (!column.visible)
				continue;

			
			int xB = column.start + 2 * COLUMN_DIVIDE_WIDTH;
			if (i == 0)
				xB += getStart();

			// Don't have to do this every time
			currentWidths[column.getKey().ordinal()] = xB - column.start + PaintUtils.getWidth(g, display);
			int a = PaintUtils.paintString(g, display,
					xB,
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

			int xB = column.start;
			if (i == 0)
			{
				xB += getStart();
			}
			g.fillRect(xB, 0, COLUMN_DIVIDE_WIDTH, getHeight());
		}

		PaintUtils.paintBorder(g, getSize(), fileBackgroundColor);
	}
	
	protected int getStart() { return 0; }
}
