package dockable.app;

import java.awt.Point;
import java.util.LinkedList;

import dockable.dom.DockableFrame;
import dockable.dom.DockablePanel;
import dockable.dom.DockableRoot;
import dockable.gui.PanelManagerDisplay;
import dockable.intf.ApplicationListener;
import dockable.intf.PanelFactory;
import dockable.intf.PanelProperties;
import dockable.tree.LeafNode;
import dockable.tree.RootNode;
import dockable.tree.SplitNode;
import dockable.tree.TabNode;


public class DockableApplication
{
	private ApplicationListener listener;
	private PanelFactory factory;
	private DockableRoot components;
	private boolean locked;
	private PanelCache registry;
	
	public DockableApplication(PanelFactory factory)
	{
		setFactory(factory);
	}
	
	public RootNode newTreeRootNode()
	{
		return new RootNode();
	}

	public SplitNode createSplit()
	{
		return new SplitNode(null);
	}

	public TabNode createTab() {
		return new TabNode(null);
	}

	public LeafNode createPanel(PanelProperties props) {
		return new LeafNode(props, null);
	}

	public void setTree(RootNode root) {
		PanelCache registry = new PanelCache(factory);
		if (components != null)
		{
			components.cache(registry);
			components.release();
		}
		
		System.out.println("Setting tree:");
		System.out.println(Writer.writer.toString(root));
		
		components = new DockableRoot(registry, root, this);
		show();
	}
	
	public void show()
	{
		components.setVisible(true);
	}

	public void setFactory(PanelFactory panelDriver) {
		this.factory = panelDriver;
		registry  = new PanelCache(factory);
	}
	
	public RootNode getRoot()
	{
		return components.toTree(null);
	}

	public void showManagerDisplay() {
		PanelManagerDisplay panelManagerDisplay = new PanelManagerDisplay(this);
		panelManagerDisplay.display(getRoot());
		panelManagerDisplay.setVisible(true);
	}
	
	public void findComponents(Point location, LinkedList<DockablePanel> panels)
	{
		if (components == null)
			return;
		components.findComponentAt(location, panels);
	}
	
	public void make(DockablePanel toRemove, Point newLocation)
	{
		if (locked)
			return;

		toRemove.getParent().remove(toRemove);
		DockableFrame frame = new DockableFrame(registry, components, newLocation, toRemove);
		frame.setVisible(true);
		components.addWindow(frame);
	}

    private void move(DockablePanel toRemove, DockablePanel first)
    {
		if (locked)
			return;
		
		
		// if empty
		//		replace it
		// if tab
		// 		if in high part
		// else
		//		split lowest non-split, non-frame 
		
		
		throw new RuntimeException("Implement me");
	}

	public void move(DockablePanel toRemove, Point newLocation) {
		if (locked)
			return;
		LinkedList<DockablePanel> list = new LinkedList<>();
		findComponents(newLocation, list);
		if (list.isEmpty())
			make(toRemove, newLocation);
		else
			move(toRemove, list.getFirst());
	}

	public void setLocked(boolean selected)
    {
        locked = selected;
    }

}
