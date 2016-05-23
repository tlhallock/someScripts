package dockable.dom;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JComponent;

import dockable.app.DockableApplication;
import dockable.app.IdManager;
import dockable.app.PanelCache;
import dockable.tree.DockableNode;
import dockable.tree.EmptyNode;
import dockable.tree.SplitNode;
import dockable.tree.TabNode;


public abstract class DockablePanel {

	protected DockablePanel parent;
	protected int panelId;
	
	// could have children here...
	
	public DockablePanel(DockablePanel parent)
	{
		this.parent = parent;
		panelId = IdManager.getNewId();
	}

	public DockablePanel getParent() {
		return parent;
	}

	abstract void setTree(PanelCache cache, DockableNode tree);
	
	public abstract Dimension getSize();

	public abstract void dump(PanelCache registry);

	public abstract JComponent getComponent();

	public abstract String getName();
	
	public abstract void findComponentAt(Point location, LinkedList<DockablePanel> panels);
	
	public abstract DockableNode toTree();
	
	public abstract void replace(DockablePanel child, DockablePanel newChild);
	
	public String toString()
	{
		return toTree().toString();
	}
	
	protected DockableApplication getApplication()
	{
		return parent.getApplication();
	}

	public abstract void remove(DockablePanel toRemove);

	public void setParent(DockablePanel dockableRoot) {
		this.parent=dockableRoot;
	}


	public int getId()
	{
		return panelId;
	}
	
	public void disposeIfJFrame() {}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static DockableTab wrap(
			DockablePanel parent,
			DockablePanel node)
	{
		if (node instanceof DockableTab)
		{
			System.out.println("Already a tab!");
			return (DockableTab) node;
		}
		TabNode tab = new TabNode();
		tab.addChild(node.toTree());
		
		PanelCache registry = new PanelCache(null);
		node.dump(registry);
		System.out.println(registry);
		
		DockableTab dockableTab = new DockableTab(parent);
		dockableTab.setTree(registry, tab);
		
		registry.dispose();
		
		return dockableTab;
	}

	public static DockableSplit wrap(
			DockablePanel parent,
			DockablePanel node,
			boolean horizontal,
			boolean left,
			int dividerLocation)
	{
		SplitNode split = new SplitNode();
		split.setDivider(dividerLocation);
		split.setLeftRight(horizontal);
		split.setLeft ( left ? node.toTree() : new EmptyNode());
		split.setRight(!left ? node.toTree() : new EmptyNode());
		
		PanelCache registry = new PanelCache(null);
		node.dump(registry);
		DockableSplit dockableSplit = new DockableSplit(parent);
		dockableSplit.setTree(registry, split);
		registry.dispose();
		return dockableSplit;
	}
}
