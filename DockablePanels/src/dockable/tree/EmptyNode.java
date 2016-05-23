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
	
	public EmptyNode(DockableNode parent, int id) {
		super(parent, id, NodeTypes.EMPTY);
	}

	EmptyNode(JsonNode node, DockableNode parent) {
		super(node, parent, NodeTypes.EMPTY);
	}
	
	
	
	
	


	@Override
	public EmptyNode duplicateSpecifics() {
		return new EmptyNode(parent, id);
	}

	@Override
	public JPanel getCustomization(TreeChangedListener listener)
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.red);
		return panel;
	}


	@Override
	protected EmptyDock createPanel(PanelCache cache, DockablePanel parent) {
		return new EmptyDock(parent);
	}
	
	


	
	
	
	@Override
	public DockableNode addChild(DockableNode node) {
		throw new RuntimeException("No children!");
	}
	
	

	// ARGH, someday I will undo everything I just did
	public EmptyNode()
	{
		this(null, -1);
	}
}
