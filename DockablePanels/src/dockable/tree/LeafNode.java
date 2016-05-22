package dockable.tree;

import java.awt.Color;

import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.PanelCache;
import dockable.dom.DockableComponent;
import dockable.dom.DockablePanel;
import dockable.gui.TreeChangedListener;
import dockable.intf.PanelProperties;

public class LeafNode extends DockableNode
{
	private PanelProperties options;

	private String type = NodeTypes.COMPONENT;
	
	public LeafNode(PanelProperties props, DockableNode parent) {
		super(parent);
		this.options = props;
	}
	
	public LeafNode(JsonNode node, DockableNode parent) {
		super(parent);
		options = new PanelProperties(node.get("options"));
	}

	@Override
	public LeafNode duplicate(DockableNode parent) {
		return new LeafNode(options.duplicate(), parent);
	}

	@Override
	public DockableTreeNode createJTree() {
		DockableTreeNode defaultMutableTreeNode = new DockableTreeNode(options.getName(), this)
		{
			@Override
			public JPanel getCustomization(TreeChangedListener listener) {
				JPanel panel = new JPanel();
				panel.setBackground(Color.orange);
				return panel;
			}
		};
		return defaultMutableTreeNode;
	}

	@Override
	public DockableComponent createPanel(PanelCache cache, DockablePanel parent) {
		return new DockableComponent(parent, options,  
				cache.getComponent(options));
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public LeafNode reduce()
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