package dockable.tree;

import java.awt.Color;
import java.util.Collection;

import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.PanelCache;
import dockable.dom.DockablePanel;
import dockable.dom.DockableRoot;
import dockable.gui.TreeChangedListener;

public class RootNode extends SeveralChildrenNode {
	
	private String type = NodeTypes.ROOT;

	public RootNode() {
		super(null);
	}

	public RootNode(JsonNode node)
	{
		super(null);
		readArray(node);		
	}

	public FrameNode createWindow(String name) {
		
		FrameNode node = new FrameNode(name, this);
		addChild(node);
		return node;
	}

	public Collection<DockableNode> getWindows() {
		return getChildren();
	}

	@Override
	public RootNode duplicate(DockableNode parent) {
		RootNode returnValue = new RootNode();
		returnValue.duplcateChildren(this);
		return returnValue;
	}

	@Override
	public DockableTreeNode createJTree() {
		return addChildren(new DockableTreeNode(NodeTypes.ROOT, this)
		{
			@Override
			public JPanel getCustomization(TreeChangedListener listener) {
				JPanel panel = new JPanel();
				panel.setBackground(Color.yellow);
				return panel;
			}
		});
	}
        
        @Override
		public boolean splitable() { return false; }
        @Override
		public boolean tabable() { return false; }

	@Override
	public DockableRoot createPanel(PanelCache cache, DockablePanel parent) {
		throw new RuntimeException("Not implemented.");
	}
}
