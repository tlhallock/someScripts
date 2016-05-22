package dockable.dom;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JComponent;

import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.intf.PanelProperties;
import dockable.tree.DockableNode;
import dockable.tree.LeafNode;

public class DockableComponent extends DockablePanel {

	private JComponent component;
	private PanelProperties props;
	
	public DockableComponent(DockablePanel parent, PanelProperties props, JComponent component) {
		super(parent);
		this.props = props;
		this.component = component;
	}

	@Override
	public void cache(PanelCache registry) {
		registry.register(props, component);
	}

	@Override
	public JComponent getComponent() {
		return component;
	}

	@Override
	public String getName() {
		return props.getName();
	}

	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels)
	{
		if (Utils.wtf(location, component))
		{
			panels.add(this);
		}
	}

	@Override
	public void release() {}

	@Override
	public LeafNode toTree(DockableNode parent)
	{
		LeafNode returnValue = new LeafNode(props/*.duplicate()*/, parent);
		return returnValue;
	}

	@Override
	public Dimension getSize() {
		return component.getSize();
	}

	@Override
	public void remove(DockablePanel toRemove)
	{
		throw new RuntimeException("Has no children!");
	}
}
