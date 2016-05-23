package dockable.app;

import java.awt.Point;
import java.util.LinkedList;

import dockable.dom.DockableFrame;
import dockable.dom.DockablePanel;
import dockable.dom.DockableRoot;
import dockable.gui.PanelManagerDisplay;
import dockable.intf.ApplicationListener;
import dockable.intf.PanelFactory;
import dockable.tree.RootNode;


public class DockableApplication
{
	private ApplicationListener listener;
	private PanelFactory factory;
	private DockableRoot components;
	private boolean locked;
	
	public DockableApplication(PanelFactory factory)
	{
		setFactory(factory);
		components = new DockableRoot(this);
	}

	public void setFactory(PanelFactory panelDriver) {
		this.factory = panelDriver;
	}
	
	public RootNode getRoot()
	{
		return components.toTree();
	}

	public void setTree(RootNode root) {
		PanelCache registry = new PanelCache(factory);
		if (components != null)
			components.dump(registry);
		
		System.out.println("Setting tree:");
		System.out.println(root);
		
		IdManager.reset(root);
		
		components = new DockableRoot(this);
		components.setTree(registry, root);
		setVisible(true);
		
		registry.dispose();
	}
	
	public void setVisible(boolean val)
	{
		components.setVisible(val);
	}
	
	public void setLocked(boolean selected)
	{
		locked = selected;
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
		DockableFrame frame = new DockableFrame(components, newLocation, toRemove);
		
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
