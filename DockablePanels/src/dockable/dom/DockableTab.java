package dockable.dom;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import dockable.app.DockableApplication;
import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.tree.DockableNode;
import dockable.tree.TabNode;


public class DockableTab extends DockablePanel
{
	private ArrayList<DockablePanel> children = new ArrayList<>();

	private JTabbedPane pane;
	
	public DockableTab(PanelCache cache, DockablePanel parent, TabNode node)
	{
		super(parent);
		
		pane = new JTabbedPane();
		
		for (DockableNode child : node.getChildren())
		{
			DockablePanel childPanel = child.createPanel(cache, this);
			pane.add(childPanel.getName(), childPanel.getComponent());
			children.add(childPanel);
		}
		
		pane.addMouseListener(new MouseAdapter() {
			
			private Point locationOnScreen;
			private int index = -1;
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (locationOnScreen == null || index < 0)
					return;
				Point releasePoint = e.getLocationOnScreen();
				if (Utils.moved(locationOnScreen, releasePoint))
				{
					move(index, releasePoint);
				}
				locationOnScreen = null;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				index = pane.indexAtLocation(e.getX(), e.getY());
				if (index < 0)
					return;
				locationOnScreen = e.getLocationOnScreen();
			}
		});
	}

	
	private void move(int index, Point newLocation) {
		DockableApplication application = getApplication();
		application.move(children.get(index), newLocation);
	}

	@Override
	public void cache(PanelCache registry) {
		for (DockablePanel child : children)
		{
			child.cache(registry);
		}
	}

	@Override
	public JComponent getComponent() {
		return pane;
	}

	@Override
	public String getName() {
		return "tabs";
	}


	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels) {
		// Could change this to be more efficient...
		Component findComponentAt = pane.findComponentAt(Utils.subtract(location, pane.getLocationOnScreen()));
		if (findComponentAt == null)
			return;
		for (DockablePanel child : children)
			child.findComponentAt(location, panels);
	}

	@Override
	public void release() {
		for (DockablePanel panel : children)
			panel.release();
		pane.removeAll();
	}


	@Override
	public TabNode toTree(DockableNode parent)
	{
		TabNode returnValue = new TabNode(parent);
		for (DockablePanel child : children)
			returnValue.addChild(child.toTree(returnValue));
		return returnValue;
	}


	@Override
	public Dimension getSize() {
		return pane.getSize();
	}


	@Override
	public void remove(DockablePanel toRemove)
	{
		children.remove(toRemove);
		pane.remove(toRemove.getComponent());
	}
}
