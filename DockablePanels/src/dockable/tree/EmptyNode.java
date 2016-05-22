package dockable.tree;

import java.awt.Color;

import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.PanelCache;
import dockable.dom.DockablePanel;
import dockable.dom.EmptyDock;
import dockable.gui.TreeChangedListener;




public class EmptyNode extends DockableNode {
	
	private String type = NodeTypes.EMPTY;
	
	public EmptyNode(DockableNode parent) {
		super(parent);
	}

	public EmptyNode(JsonNode node, DockableNode parent) {
		super(parent);
	}


	@Override
	public EmptyNode duplicate(DockableNode parent) {
		return new EmptyNode(parent);
	}

	@Override
	public DockableTreeNode createJTree() {
		return new DockableTreeNode("Null", this)
		{
			@Override
			public JPanel getCustomization(TreeChangedListener listener) {
				JPanel panel = new JPanel();
				panel.setBackground(Color.red);
				return panel;
			}
		};
	}


	@Override
	public EmptyDock createPanel(PanelCache cache, DockablePanel parent) {
		return new EmptyDock(parent);
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public DockableNode reduce()
	{
		return this;
	}

	@Override
	public void replace(DockableNode child, DockableNode newChild)
	{
		throw new RuntimeException("Not a parent.");
	}

	@Override
	protected void addChildrenTo(TabNode combined) {}
}
