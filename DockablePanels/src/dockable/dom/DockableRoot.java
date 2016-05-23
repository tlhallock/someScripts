package dockable.dom;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JComponent;

import dockable.app.DELETE_ME;
import dockable.app.DockableApplication;
import dockable.app.PanelCache;
import dockable.tree.DockableNode;
import dockable.tree.RootNode;

public class DockableRoot extends DockablePanel {

	private DockableApplication application;
	private LinkedList<DockablePanel> windows = new LinkedList<>();

	public DockableRoot(DockableApplication app) {
		super(null);
		this.application = app;
	}
	
	public void setTree(PanelCache cache, DockableNode node)
	{
		if (!(node instanceof RootNode))
			throw new RuntimeException("Better not happen.");
		
		for (DockableNode child : node.getChildren())
		{
			DockablePanel orCreatePanel = child.getOrCreatePanel(cache, this);
			windows.add(orCreatePanel);
			orCreatePanel.setTree(cache, child);
		}
	}
	
	public void addWindow(DockablePanel window)
	{
		windows.add(window);
		window.setParent(this);
	}

	public void setVisible(boolean visible)
	{
		for (DockablePanel panel : windows)
			if (panel instanceof DockableFrame)
				((DockableFrame) panel).setVisible(visible);
	}

	@Override
	public void dump(PanelCache registry) {
		for (DockablePanel panel : windows)
			panel.dump(registry);
	}

	@Override
	public JComponent getComponent() {
		throw new RuntimeException("The root does not have a component.");
	}

	@Override
	public String getName()
	{
		return "Da Root";
	}
	
	@Override
	protected DockableApplication getApplication()
	{
		return application;
	}

	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels) {
		// Could change this to be more efficient...
		for (DockablePanel panel : windows)
			panel.findComponentAt(location, panels);
	}

	@Override
	public RootNode toTree() {
		LinkedList<DockableNode> frames = new LinkedList<DockableNode>();
		windows.stream().forEach(x -> frames.add(x.toTree()));
		return DELETE_ME.createRootNode(frames);
	}

	@Override
	public Dimension getSize() {
		return new Dimension(0, 0);
	}

	@Override
	public void remove(DockablePanel toRemove)
	{
		throw new RuntimeException("Why are we doing this?");
	}

	@Override
	public void replace(DockablePanel child, DockablePanel newChild)
	{
		LinkedList<DockablePanel> clone = (LinkedList<DockablePanel>)windows.clone();
		windows.clear();
		clone.stream().forEach(x -> windows.add(x == child ? newChild : x));
	}
}
