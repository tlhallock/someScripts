package dockable.dom;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.tree.DockableNode;
import dockable.tree.SplitNode;


public class DockableSplit extends DockablePanel
{
	private DockableNode node; // Don't need this...
	private JSplitPane pane;
	
	private DockablePanel left;
	private DockablePanel right;
	
	public DockableSplit(PanelCache cache, DockablePanel parent, SplitNode node)
	{
		super(parent);
		this.node = node;
		
		pane = new JSplitPane(node.getLeftRight() ? JSplitPane.HORIZONTAL_SPLIT : JSplitPane.VERTICAL_SPLIT);
		pane.setDividerLocation(node.getDividerLocation());
		
		left  = node.getLeft().createPanel(cache, this);
		right = node.getRight().createPanel(cache, this);
		
		pane.setLeftComponent(left.getComponent());
		pane.setRightComponent(right.getComponent());
	}

	@Override
	public void cache(PanelCache registry)
	{
		left.cache(registry);
		right.cache(registry);
	}

	@Override
	public JComponent getComponent() {
		return pane;
	}

	@Override
	public String getName() {
		return "split";
	}

	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels) {
		// Could change this to be more efficient...
		Component findComponentAt = pane.findComponentAt(Utils.subtract(location, pane.getLocationOnScreen()));
		if (findComponentAt != null)
		{
			left.findComponentAt(location, panels);
			right.findComponentAt(location, panels);
		}
	}

	@Override
	public void release() {
		left.release();
		right.release();
		pane.removeAll();
	}

	@Override
	public SplitNode toTree(DockableNode parent)
	{
		SplitNode returnValue = new SplitNode(parent);
		returnValue.setDivider(pane.getDividerLocation());
		returnValue.setLeftRight(pane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT);
		returnValue.setLeft(left.toTree(returnValue));
		returnValue.setRight(right.toTree(returnValue));
		return returnValue;
	}

	@Override
	public Dimension getSize() {
		return pane.getSize();
	}

	@Override
	public void remove(DockablePanel toRemove)
	{
		if (left == toRemove)
		{
			pane.remove(left.getComponent());
			left = new EmptyDock(this);
			pane.setLeftComponent(left.getComponent());
		}
		else if (right == toRemove)
		{
			pane.remove(right.getComponent());
			right = new EmptyDock(this);
			pane.setLeftComponent(right.getComponent());
		}
	}
}
