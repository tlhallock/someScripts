package dockable.dom;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JComponent;

import dockable.app.DockableApplication;
import dockable.app.PanelCache;
import dockable.tree.DockableNode;


public abstract class DockablePanel {

	protected DockablePanel parent;
	
	public DockablePanel(DockablePanel parent)
	{
		this.parent = parent;
	}

	public DockablePanel getParent() {
		return parent;
	}
	
	public abstract Dimension getSize();

	public abstract void cache(PanelCache registry);

	public abstract JComponent getComponent();

	public abstract String getName();
	
	public abstract void findComponentAt(Point location, LinkedList<DockablePanel> panels);
	
	public abstract void release();
	
	public abstract DockableNode toTree(DockableNode parent);
	
	protected DockableApplication getApplication()
	{
		return parent.getApplication();
	}

	public abstract void remove(DockablePanel toRemove);

	public void setParent(DockableRoot dockableRoot) {
		this.parent=dockableRoot;
	}
	
//	abstract DockablePanelNode serialize();
//	abstract void register(PanelCache registry);
}
