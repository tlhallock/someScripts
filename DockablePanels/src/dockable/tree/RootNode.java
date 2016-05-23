package dockable.tree;

import java.awt.Color;

import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.PanelCache;
import dockable.dom.DockablePanel;
import dockable.dom.DockableRoot;
import dockable.gui.TreeChangedListener;

public class RootNode extends DockableNode {
	
	private String type = NodeTypes.ROOT;

	public RootNode() {
		super(null, -1, NodeTypes.ROOT);
	}

	RootNode(JsonNode node)
	{
		super(node, (DockableNode) null, NodeTypes.ROOT);
	}

	@Override
	public RootNode duplicateSpecifics() {
		return new RootNode();
	}

	@Override
	public JPanel getCustomization(TreeChangedListener listener)
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.yellow);
		return panel;
	}

	@Override
	public boolean splitable()
	{
		return false;
	}

	@Override
	public boolean tabable()
	{
		return false;
	}

	@Override
	protected DockableRoot createPanel(PanelCache cache, DockablePanel parent)
	{
		throw new RuntimeException("Not implemented.");
	}
}
