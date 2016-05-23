package dockable.tree;
import java.awt.Color;

import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.PanelCache;
import dockable.dom.DockablePanel;
import dockable.dom.DockableTab;
import dockable.gui.TreeChangedListener;


public class TabNode extends DockableNode
{
	private String type = NodeTypes.TABS;
	
	public TabNode()
	{
		this(null, -1);
	}
	
	public TabNode(DockableNode parent, int id)
	{
		super(parent, id, NodeTypes.TABS);
	}
	
	TabNode(JsonNode node, DockableNode parent)
	{
		super(node, parent, NodeTypes.TABS);
	}

	@Override
	public TabNode duplicateSpecifics() {
		return new TabNode(parent, id);
	}

	@Override
	public JPanel getCustomization(TreeChangedListener notifier)
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.green);
		return panel;
	}


	@Override
	protected DockableTab createPanel(PanelCache cache, DockablePanel parent) {
		return new DockableTab(parent);
	}
	

        public boolean collapseable()
        {
        	return false;
        }

	@Override
	protected String getDisplayName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void addTab(LeafNode nothingNode)
	{
		addChild(nothingNode);
	}
}
