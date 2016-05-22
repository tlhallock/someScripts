package dockable.tree;
import java.awt.Color;

import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.PanelCache;
import dockable.dom.DockablePanel;
import dockable.dom.DockableTab;
import dockable.gui.TreeChangedListener;


public class TabNode extends SeveralChildrenNode
{
	private String type = NodeTypes.TABS;
	
	public TabNode(DockableNode parent)
	{
		super(parent);
	}
	
	public TabNode(JsonNode node, DockableNode parent)
	{
		super(parent);
		readArray(node);
	}

	@Override
	public TabNode duplicate(DockableNode parent) {
		TabNode returnValue = new TabNode(parent);
		returnValue.duplcateChildren(this);
		return returnValue;
	}

	@Override
	public DockableTreeNode createJTree() {
		return addChildren(new DockableTreeNode("Tabs", this)
		{
			@Override
			public JPanel getCustomization(TreeChangedListener notifier) {
				JPanel panel = new JPanel();
				panel.setBackground(Color.green);
				return panel;
			}
		});
	}


	@Override
	public DockableTab createPanel(PanelCache cache, DockablePanel parent) {
		return new DockableTab(cache, parent, this);
	}

	public void addTab(DockableNode node)
	{
		addChild(node);
	}
}
