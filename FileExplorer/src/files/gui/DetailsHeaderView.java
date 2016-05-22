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

public class DetailsHeaderView extends JPanel implements MouseListener, MouseMotionListener
{
	private int radius = 10;
	private Columns columns;
	
	private Column dragging;
	private int draggingX;
	
	private boolean inited;
	private ColumnManager painter;
	
	private boolean userModified;
	
	private Column sortedIdx = null;
	private boolean sortedReverse = false;
    
    boolean highlighted;
	
	private final JMenuItem equallySpaceMenuItem = new JMenuItem("Equally Space");
	{
		equallySpaceMenuItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			columns.equallySpace();
			userModified = true;
			painter.repaint();
		}});
	}
	
	public DetailsHeaderView(ColumnManager painter)
	{
		this.painter = painter;
		this.columns = this.new Columns();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				if (userModified) return;
				columns.equallySpace();
				painter.repaint();
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				if (userModified) return;
				columns.equallySpace();
				painter.repaint();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		setPopup();
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
		userModified = false;
		Column c = getColumn(key);
		if (c != null)
		{
			columns.remove(c);
			columns.order();
			columns.equallySpace();
		}
		else
		{
			columns.add(new Column(Integer.MAX_VALUE, key));
			columns.equallySpace();
		}

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
		
		popup.add(equallySpaceMenuItem);
		
		// Could make this an option...
		LinkedList<FileEntryAttributeKey> sortedList = new LinkedList<>();
		for (FileEntryAttributeKey key : FileEntryAttributeKey.values())
			sortedList.add(key);
		
		Collections.sort(sortedList, comparator);
		
		for (FileEntryAttributeKey key : sortedList)
		{
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
		if (!userModified)
			columns.equallySpace();
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
//		if (!cutColumns)
//			for (Column c : columns)
//				if (c.nameWidth < 0)
//					c.nameWidth = PaintUtils.getWidth(g, c.key.name());
		
		
		
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
			

			int xEnd = i == columns.size() - 1? getWidth() : columns.get(i+1).start;
			int xBegin = column.start;
			String text = column.key.getDisplay();
			
			int width = PaintUtils.paintString(g, text, xBegin, xEnd, getHeight(), cutColumns, cutColumns, true);
			column.visible = !cutColumns || width > xEnd - xBegin;
			column.nameWidth = width;
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
		userModified = true;
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
		dragging = getColumn(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (dragging == null)
			return;
		
		int newStart = e.getX();
		newStart = 
		dragging.start = newStart;
		
		columns.order();
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
	}
	
	public class Columns extends ArrayList<Column>
	{
		private void order()
		{
			Collections.sort(this);
			if (!isEmpty())
				get(0).start = 0;
			
			boolean cutColumns = Application.getApplication().getSettings().cutColumns();
			
			Column prev = null;
			for (Column column : this)
			{
				if (cutColumns && prev != null && prev.nameWidth > 0)
					column.start = Math.max((int) Math.ceil(prev.start + prev.nameWidth + 2 * radius), column.start);
				column.start = Math.max(0, Math.min(getWidth(), column.start));
				prev = column;
			}
		}
		
		public void equallySpace()
		{
			int width = getWidth();
			
			int size = size();
			for (int i=0;i<size;i++)
				get(i).start = (int) (i * width / (double) size);
		}
	}
	
	public interface ColumnManager
	{
		void sort(int column, boolean reverse);
		void repaint();
	}
}
