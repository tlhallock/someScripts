package dockable.dom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.tree.DockableNode;
import dockable.tree.EmptyNode;

public class EmptyDock extends DockablePanel {

	private JPanel panel;
	
	public EmptyDock(DockablePanel parent) {
		super(parent);
		
		panel = new JPanel();
		panel.setBackground(Color.pink);
	}

	@Override
	public void cache(PanelCache registry) {}

	@Override
	public JComponent getComponent() {
		return panel;
	}

	@Override
	public String getName() {
		return "empty";
	}

	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels)
	{
		if (Utils.wtf(location, panel))
		{
			panels.add(this);
		}
	}

	@Override
	public void release() {}

	@Override
	public EmptyNode toTree(DockableNode parent)
	{
		return new EmptyNode(parent);
	}

	@Override
	public Dimension getSize()
	{
		return panel.getSize();
	}

	@Override
	public void remove(DockablePanel toRemove) {
		throw new RuntimeException("Is not a parent!");
	}
}
