package files.gui;

import java.awt.Dimension;
import java.util.LinkedList;

import files.model.FileEntryAttributes.FileEntryAttributeKey;

public abstract class ListViewHorizontal<T extends FileViewHorizontal> extends ListView<T>
{
	protected ListViewHorizontal(FileEntryAttributeKey[] attributes)
	{
		super(attributes);
	}

	protected int getMinimalDesiredWidth(FileEntryAttributeKey key)
	{
		synchronized (details)
		{
			int max = -1;
			for (FileViewHorizontal fileView : details)
			{
				max = Math.max(max, fileView.getWidth(key));
			}
			return max;
		}
	}

        
        
	protected Dimension setLocations(LinkedList<T> toShow, int w)
	{
		int y = 0;
		boolean even = false;
		for (FileViewHorizontal detail : toShow)
		{
			detail.setBounds(0, y, w, height);
			detail.setEven(even = !even);
			y += height;
		}

		return new Dimension(w, y);
	}
}
