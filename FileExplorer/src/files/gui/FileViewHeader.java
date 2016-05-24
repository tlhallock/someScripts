package files.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import files.app.Application;
import files.model.FileEntryAttributes.FileEntryAttributeKey;

public class FileViewHeader extends JPanel implements MouseListener, MouseMotionListener
{
	private int radius = 10;
	private Columns columns;
	
	private Column dragging;
	private int draggingX;
	
	private boolean inited;
	private ColumnManager painter;

	private int[] currentWidths = new int[FileEntryAttributeKey.values().length];
	
	private SpacingPolicy policy;
	
	private Column sortedIdx = null;
	private boolean sortedReverse = false;
    
	private boolean highlighted;
	
	private int entryW = -1;
        
        private boolean showNameFirst;
        private boolean fixedColumns;
	
	private final JMenuItem equallySpaceMenuItem = new JMenuItem("Equally Space");
	{
		equallySpaceMenuItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			policy = SpacingPolicy.EQUAL;
			policy.apply(columns);
			painter.repaint();
		}});
	}
	private final JMenuItem minimumSpaceMenuItem = new JMenuItem("Minimally Space");
	{
		minimumSpaceMenuItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			policy = SpacingPolicy.MINIMAL;
			policy.apply(columns);
			painter.repaint();
		}});
	}
	
	public FileViewHeader(ColumnManager painter, boolean showNameFirst, boolean fixedColumns)
	{
            this.showNameFirst = showNameFirst;
            this.fixedColumns = fixedColumns;
            this.painter = painter;
            this.columns = this.new Columns();
            
            policy = fixedColumns ? SpacingPolicy.EQUAL : Application.getApplication().getSettings().getDefaultSpacingPolicy();
		
		addMouseListener(this);
                if (!fixedColumns)
                    addMouseMotionListener(this);
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				policy.apply(columns);
				painter.repaint();
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				policy.apply(columns);
				painter.repaint();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		setPopup();
	}
	
	void setEntryWidth(int w)
	{
		entryW = w;
		policy.apply(columns);
	}
	private int getEntryWidth()
	{
		if (entryW < 0)
			return getWidth();
		else
			return entryW;
	}

	void setFolderHighlighted(boolean val)
	{
		boolean changed = highlighted != val;
		this.highlighted = val;
		if (changed)
			painter.repaint();
	}
	
	private Column getColumn(FileEntryAttributeKey key)
	{
		for (Column column : columns)
			if (column.key.equals(key))
				return column;
		return null;
	}
	
	private void toggle(FileEntryAttributeKey key)
	{
		Column c = getColumn(key);
		if (c != null)
		{
			columns.remove(c);
			columns.order();
		}
		else
		{
			columns.add(new Column(Integer.MAX_VALUE, key));
		}
		
		if (policy == SpacingPolicy.USER_MODIFIED)
			policy = Application.getApplication().getSettings().getDefaultSpacingPolicy();
		policy.apply(columns);
		painter.repaint();
		setPopup();
	}
	
	private static Comparator<FileEntryAttributeKey> comparator =  new Comparator<FileEntryAttributeKey>(){
		@Override
		public int compare(FileEntryAttributeKey o1, FileEntryAttributeKey o2) {
			return o1.getDisplay().compareTo(o2.getDisplay());
		}};
		

	private void setPopup()
	{
		JPopupMenu popup = new JPopupMenu();
		if (!fixedColumns)
		popup.add(equallySpaceMenuItem);
                if (!fixedColumns)
		popup.add(minimumSpaceMenuItem);
		
		// Could make this an option...
		LinkedList<FileEntryAttributeKey> sortedList = new LinkedList<>();
		for (FileEntryAttributeKey key : FileEntryAttributeKey.values())
			sortedList.add(key);
		
		Collections.sort(sortedList, comparator);
		
		for (FileEntryAttributeKey key : sortedList)
		{
                    if (!showNameFirst && key.equals(FileEntryAttributeKey.All_Name))
                        continue;
                        
			JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(key.getDisplay());
			menuItem.setSelected(getColumn(key) != null);
			menuItem.addActionListener(e -> toggle(key));
			popup.add(menuItem);
		}
		
		setComponentPopupMenu(popup);
	}

	public void addColumn(Column column)
	{
		columns.add(column);
		setPopup();
                if (policy == SpacingPolicy.USER_MODIFIED)
                    policy = Application.getApplication().getSettings().getDefaultSpacingPolicy();
                policy.apply(columns);
		painter.repaint();
	}

	// should be private
	Columns getColumns() {
		return columns;
	}
	
	
	
	@Override
	public void paint(Graphics graphics)
	{
		Graphics2D g = (Graphics2D) graphics;
		
		boolean cutColumns = Application.getApplication().getSettings().cutColumns();
		
		g.setColor(Application.getApplication().getColorSelector().getColumnHeaderBackgroundColor(highlighted));
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Application.getApplication().getColorSelector().getColumnHeaderForegroundColor(highlighted));
		for (int i = columns.size() - 1; i >= 0 ; i--)
		{
			if (i == 0) continue;
			Column column = columns.get(i);
			
			if (sortedIdx == null || !column.equals(sortedIdx))
			{
				g.fillOval(column.start - radius, getHeight() / 2 - radius, 2 * radius, 2 * radius);
			}
			else
			{
				if (sortedReverse)
				{
					int v1x = column.start;
					int v1y = getHeight() / 2 - radius;

					int v2x = v1x - radius;
					int v2y = v1y + 2 * radius;

					int v3x = v1x + radius;
					int v3y = v1y + 2 * radius;
					
					g.fillPolygon(
							new int[] {v1x, v2x, v3x},
							new int[] {v1y, v2y, v3y}, 
							3);
				}
				else
				{
					int v1x = column.start;
					int v1y = getHeight() / 2 + radius;

					int v2x = v1x - radius;
					int v2y = v1y - 2 * radius;

					int v3x = v1x + radius;
					int v3y = v1y - 2 * radius;
					
					g.fillPolygon(
							new int[] {v1x, v2x, v3x},
							new int[] {v1y, v2y, v3y}, 
							3);
				}
			}
		}

		g.setColor(Application.getApplication().getColorSelector().getColumnHeaderForegroundColor(highlighted));
		for (int i = 0; i < columns.size(); i++)
		{
			Column column = columns.get(i);
			

			int xEnd = i == columns.size() - 1? getEntryWidth() : columns.get(i+1).start;
			int xBegin = column.start;
			String text = column.key.getDisplay();
			
			int width = PaintUtils.paintString(g, text, xBegin, xEnd, getHeight(), cutColumns, cutColumns, true);
			column.visible = !cutColumns || width > xEnd - xBegin;
			column.nameWidth = width + 2 * radius;
		}
		
		if (dragging != null)
		{
			g.setColor(Application.getApplication().getColorSelector().getColumnHeaderDragColor());
			g.fillOval(draggingX - radius, getHeight() / 2 - radius, 2 * radius, 2 * radius);
		}
		
		if (!inited)
		{
			columns.order();
			painter.repaint();
			inited = true;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		policy = SpacingPolicy.USER_MODIFIED;
		if (dragging != null)
			draggingX = e.getX();
		
//		int newStart = e.getX();
//		dragging.start = newStart;
//		
//		columns.order();
		repaint();
	}



	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() != 2)
			return;
		int x = e.getX();

		int i;
		Column c = null;
		for (i=0;i<columns.size()-1; i++)
			if (columns.get(i+1).start > x)
			{
				c = columns.get(i);
				break;
			}
		if (c == null) c = columns.get(columns.size()-1);

		if (sortedIdx != null && sortedIdx.equals(c))
			sortedReverse = !sortedReverse;
		else
			sortedReverse = false;
		sortedIdx = c;
		painter.sort(i, sortedReverse);
	}

	public Column getColumn(int x, int y)
	{
		for (Column column : columns)
		{
			double cx = column.start;
			double cy = getHeight() / 2;
			
			double dx = cx - x;
			double dy = cy - y;
			if (Math.sqrt(dx*dx + dy*dy) < radius)
			{
				return column;
			}
		}
		
		return null;
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
            if (!fixedColumns)
		dragging = getColumn(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (dragging == null)
			return;
		
		int newStart = e.getX();
		dragging.start = newStart;
		
		columns.order();
		policy.apply(columns);
		painter.repaint();
		
		dragging = null;
		draggingX = -1;
		repaint();
	}




	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class Column implements Comparable<Column>
	{
		int start;
		FileEntryAttributeKey key;
		double nameWidth = -1;
		boolean visible = true;
		
		public Column(int start, FileEntryAttributeKey name) {
			super();
			this.start = start;
			this.key = name;
		}

		@Override
		public int compareTo(Column o) {
			return Integer.compare(start, o.start);
		}
		public FileEntryAttributeKey getKey()
		{
			return key;
		}
	}
	
	public class Columns extends ArrayList<Column>
	{
		private void order()
		{
			Collections.sort(this);
                        if (showNameFirst && !get(0).key.equals(FileEntryAttributeKey.All_Name))
                        {
                            Column c = null;
                            for (Column column : this)
                                if (column.key.equals(FileEntryAttributeKey.All_Name))
                                {
                                    c = column;
                                    break;
                                }
                            if (c == null)
                            {
                                throw new RuntimeException("Name column not found!");
                            }
                            remove(c);
                            add(0, c);
                                    
                        }
			if (!isEmpty())
				get(0).start = 0;
			
			boolean cutColumns = Application.getApplication().getSettings().cutColumns();
			
			Column prev = null;
			for (Column column : this)
			{
				if (cutColumns && prev != null && prev.nameWidth > 0)
					column.start = Math.max((int) Math.ceil(prev.start + prev.nameWidth + radius), column.start);
				column.start = Math.max(0, Math.min(getEntryWidth(), column.start));
				prev = column;
			}
		}
		
		public void minimalSpace()
		{
			int[] starts = new int[size()];
			
			boolean notFilled = false;
			int current = 0;
			for (int i=0;i<size() && !notFilled;i++)
			{
				int desiredWidth = Math.max(painter.getDesiredWidth(get(i).key), (int) Math.ceil(get(i).nameWidth));
				
				starts[i] = current;
				current += desiredWidth;
				if (desiredWidth < 0)
					notFilled = true;
			}
			
			if (notFilled)
			{
				equallySpace();
				return;
			}
			
			int diff = Math.max(0, getEntryWidth() - current);
			for (int i = 1; i < size(); i++)
			{
				get(i).start = starts[i] + diff;
			}
		}

		public void equallySpace()
		{
			int width = getEntryWidth();
			
			int size = size();
			for (int i = 0; i < size; i++)
				get(i).start = (int) (i * width / (double) size);
		}
	}

	public enum SpacingPolicy
	{
		EQUAL,
		MINIMAL,
		USER_MODIFIED,
		
		; 
		
		void apply(Columns columns)
		{
			switch (this)
			{
			case EQUAL:
				columns.equallySpace();
				return;
			case MINIMAL:
				columns.minimalSpace();
				return;
			case USER_MODIFIED:
				return;
			}
		}
	};
	
	public interface ColumnManager
	{
		void sort(int column, boolean reverse);
		void repaint();
		int getDesiredWidth(FileEntryAttributeKey key);
	}
}
