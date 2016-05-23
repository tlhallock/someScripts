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
	
	public LeafNode(PanelProperties props, DockableNode parent, int id) {
		super(parent, id, props.getName());
		this.options = props;
	}
	
	LeafNode(JsonNode node, DockableNode parent) {
		super(node, parent, node.get("options").get("name").textValue());
		options = new PanelProperties(node.get("options"));
	}

	
	
	
	
	
	

	public DockableNode addChild(DockableNode node) {
		throw new RuntimeException("No children!");
	}
	
	
	
	
	
	
	
	
	@Override
	public LeafNode duplicateSpecifics() {
		return new LeafNode(options.duplicate(), parent, id);
	}

	@Override
	public JPanel getCustomization(TreeChangedListener listener)
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.orange);
		return panel;
	}

	@Override
	public DockableComponent createPanel(PanelCache cache, DockablePanel parent) {
		return new DockableComponent(parent, options, cache.getOrCreateComponent(options));
	}
}