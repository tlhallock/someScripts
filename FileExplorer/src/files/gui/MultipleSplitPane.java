package files.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import files.app.Application;

public class MultipleSplitPane extends JPanel implements ComponentListener
{
	private static final int DIVIDER_WIDTH = 10;
	
	int currentWidth;
	private ArrayList<EntryNode> components = new ArrayList<>();
	
	
	private EntryNode dragEntryNode = null;
	private int dragBarX = -1;
	private int dragBarOffset = -1;

	private int defaultNewSize = 200;
	
	public MultipleSplitPane(int defaultNewSize)
	{
		this.defaultNewSize = defaultNewSize;
		setLayout(null);
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {

				EntryNode dragging = dragEntryNode;
				int offset = dragBarOffset;
				int cX = dragBarX;
				
				dragEntryNode = null;
				dragBarOffset = -1;
				dragBarX = -1;
				
				if (dragging != null && offset >= 0 && cX >= 0)
				{
					setPositionOfChild(dragging, e.getX() - offset);
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				dragEntryNode = getEntry(e.getX());
				if (dragEntryNode == null) return;
				dragBarOffset = e.getX() - (dragEntryNode.currentStart + dragEntryNode.width);
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {}
			@Override
			public void mouseDragged(MouseEvent e) {
				if (dragEntryNode == null) return;
				dragBarX = e.getX();
				repaint();
			}
		});
		adjustBounds();
	}

	public void prependChild(JComponent component)
	{
		synchronized (components)
		{
			for (EntryNode entry : components)
			{
				entry.currentStart += defaultNewSize + DIVIDER_WIDTH;
				entry.index++;
			}
			
			components.add(0, 
					new EntryNode(0,
					0,
					defaultNewSize,
					component));
			add(component);
			component.setVisible(true);
			currentWidth = currentWidth + defaultNewSize + DIVIDER_WIDTH;

		}
		adjustBounds();
		repaint();
	}
	public void postpendChild(JComponent component)
	{
		synchronized (components)
		{
			components.add(components.size(), 
					new EntryNode(components.size(),
					currentWidth,
					defaultNewSize,
					component));
			add(component);
			component.setVisible(true);
			currentWidth = currentWidth + defaultNewSize + DIVIDER_WIDTH;
		}
		adjustBounds();
		repaint();
	}

	public void removeChildrenAfter(int index) {
		for (int i = index + 1; i < components.size(); i++) {
			remove(components.get(i).component);
		}

		components.removeIf(x -> x.index > index);

		if (components.isEmpty()) {
			currentWidth = 0;
		} else {
			EntryNode last = components.get(components.size() - 1);
			currentWidth = last.currentStart + last.width + DIVIDER_WIDTH;
		}
		adjustBounds();
		repaint();
	}
	
	public void removeAllChildren()
	{
		synchronized (components) {
			components.clear();
			super.removeAll();
			currentWidth = 0;
		}
		adjustBounds();
	}

    int getNumberOfChildren() {
        return components.size();
    }
    JComponent getChild(int index)
    {
    	return components.get(index).component;
    }

	public int getChildEnd(int index) {
		return components.get(index).currentStart + components.get(index).width + DIVIDER_WIDTH;
	}

	public int getChildBegin(int index) {
		return components.get(index).currentStart;
	}
	
	
	
	
	
	

	
	@Override
	public void paint(Graphics graphics)
	{
		super.paint(graphics);

		
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Application.getApplication().getColorSelector().getColumnDividerColor());
		
		synchronized (components)
		{
			for (EntryNode node : components)
			{
				g.fillRect(node.currentStart + node.width, 0, DIVIDER_WIDTH, getHeight());
			}
		}

		EntryNode dragging = dragEntryNode;
		int offset = dragBarOffset;
		int cX = dragBarX;
		if (dragging != null && offset >= 0 && cX >= 0)
		{
			g.setColor(Application.getApplication().getColorSelector().getColumnHeaderDragColor());
			g.fillRect(cX - offset, 0, DIVIDER_WIDTH, getHeight());
		}
	}
	
	
	
	
	
	
	
	
	
	private EntryNode getEntry(int x)
	{
		synchronized (components)
		{
			for (EntryNode node : components)
			{
				int dividerStart = node.currentStart + node.width;
				int dividerEnd   = dividerStart + DIVIDER_WIDTH;
				if (dividerStart <= x && x < dividerEnd)
				{
					return node;
				}
			}
			return null;
		}
	}
	
	private void setPositionOfChild(EntryNode dragging, int newDividerStart) 
	{
		synchronized (components) {
			if (newDividerStart < dragging.currentStart + dragging.width)
				contract(dragging, newDividerStart);
			else
				expand(dragging, newDividerStart);
		}
		adjustBounds();
		repaint();
	}

	private void contract(EntryNode dragging, int newDividerStart) {
		int requiredStartingPosition = 0;
		for (int i=0;i<=dragging.index;i++)
		{
			EntryNode entryNode = components.get(i);

			int lastPossibleDividerStart   = (newDividerStart - (dragging.index - i) * DIVIDER_WIDTH);
			entryNode.currentStart = requiredStartingPosition;
			entryNode.width        = Math.min(
								entryNode.width,
									Math.max(
											0,
											lastPossibleDividerStart - entryNode.currentStart));
			
			requiredStartingPosition += entryNode.width + DIVIDER_WIDTH;
		}
			
		for (int i=dragging.index + 1; i < components.size();i++)
		{
			EntryNode entryNode = components.get(i);
			entryNode.currentStart = requiredStartingPosition;
			requiredStartingPosition += entryNode.width + DIVIDER_WIDTH;
		}
		
		currentWidth = requiredStartingPosition;
	}

	private void expand(EntryNode dragging, int newDividerStart)
	{
		int difference = newDividerStart - (dragging.currentStart + dragging.width);
		dragging.width = newDividerStart - dragging.currentStart;
		for (int i = dragging.index + 1; i < components.size(); i++)
			components.get(i).currentStart += difference;
		
		EntryNode last = components.get(components.size()-1);
		currentWidth = last.currentStart + last.width + DIVIDER_WIDTH;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

    public static final class EntryNode
    {
    	private int index;
    	
    	private int currentStart;
        private int width;
        
        private JComponent component;

		private EntryNode(int index, int currentStart, int width, JComponent component) {
			this.index = index;
			this.currentStart = currentStart;
			this.width = width;
			this.component = component;
		}
    }



















//
//    @Override
//	public int getWidth()
//    {
//    	return currentWidth;
//    }
//    
//    @Override
//	public Dimension getPreferredSize()
//    {
//    	Dimension preferredSize = super.getPreferredSize();
//    	return new Dimension(currentWidth, preferredSize.height);
//    }


	private int getHeightFromEvent(ComponentEvent e) {
		JComponent jComponent = (JComponent)e.getSource();
		int h = jComponent.getHeight();
//		if (jComponent instanceof JScrollPane)
			h = ((JScrollPane) jComponent).getViewport().getHeight();
		return h;
	}
    
	@Override
	public void componentResized(ComponentEvent e) {
		adjustBounds(getHeightFromEvent(e));
	}


	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {
		adjustBounds(getHeightFromEvent(e));
	}

	private void adjustBounds() {
		adjustBounds(getHeight());
	}

	private void adjustBounds(int h) {
		System.out.println("h=" + h);
		for (EntryNode node : components)
			node.component.setBounds(node.currentStart, 0, node.width, h);
		Dimension newSize = new Dimension(currentWidth, getHeight());
		setSize(newSize);
		setPreferredSize(newSize);
	}

	@Override
	public void componentHidden(ComponentEvent e) {}
}
