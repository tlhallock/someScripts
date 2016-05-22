package dockable.dom;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JComponent;

import dockable.app.DockableApplication;
import dockable.app.PanelCache;
import dockable.tree.DockableNode;
import dockable.tree.RootNode;

public class DockableRoot extends DockablePanel {

	private DockableApplication application;
	private LinkedList<DockablePanel> windows = new LinkedList<>();
	
	public DockableRoot(PanelCache cache, RootNode node, DockableApplication app) {
		super(null);
		this.application = app;

		for (DockableNode child : node.getWindows())
			windows.add(child.createPanel(cache, this));
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
	public void cache(PanelCache registry) {
		for (DockablePanel panel : windows)
			panel.cache(registry);
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
	public void release() {
		for (DockablePanel panel : windows)
			panel.release();
	}

	@Override
	public RootNode toTree(DockableNode parent) {
		RootNode returnValue = new RootNode();
		for (DockablePanel panel : windows)
			returnValue.addChild(panel.toTree(returnValue));
		return returnValue;
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
}
