package dockable.tree;

import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.PanelCache;
import dockable.dom.DockablePanel;
import dockable.dom.DockableSplit;
import dockable.gui.SplitOptions;
import dockable.gui.TreeChangedListener;





public class SplitNode extends DockableNode
{
	
	private static final int LEFT_NODE = 0;
	private static final int RIGHT_NODE = 1;
	
	private String type = NodeTypes.SPLIT;
	
	private int dividerLocation = -1;
	private boolean horizontal = true;
	
	public SplitNode(DockableNode parent, int id)
	{
		super(parent, id, NodeTypes.SPLIT);
		
		children.add(LEFT_NODE,  new EmptyNode(this, -1)); // left
		children.add(RIGHT_NODE, new EmptyNode(this, -1));
	}

	SplitNode(JsonNode node, DockableNode parent)
	{
		super(node, parent, NodeTypes.SPLIT);
		dividerLocation = node.get("dividerLocation").asInt();
		horizontal = node.get("horizontal").asBoolean();
	}
	


	public int getDividerLocation() {
		return dividerLocation;
	}

	public void setDivider(int i) {
		this.dividerLocation = i;
	}

	public void setLeftRight(boolean b)
	{
		horizontal = b;
	}

	public boolean getLeftRight()
	{
		return horizontal;
	}
	
	
	
	
	
	
	
	
	

	public void setLeft(DockableNode leftNode) {
		
		if (children.get(LEFT_NODE).equals(leftNode))
			return;

		if (leftNode == null)
			leftNode = new EmptyNode(this, -1);
		
		leftNode.setParent(this);
		children.set(LEFT_NODE, leftNode);
	}

	public void setRight(DockableNode rightNode) {

		if (children.get(RIGHT_NODE).equals(rightNode))
			return;
		
		if (rightNode == null)
			rightNode = new EmptyNode(this, -1);
		
		rightNode.setParent(this);
		children.set(RIGHT_NODE, rightNode);
	}


	public DockableNode getLeft() {
		return children.get(LEFT_NODE);
	}


	public DockableNode getRight() {
		return children.get(RIGHT_NODE);
	}

	public DockableNode addChild(DockableNode node) {
		throw new RuntimeException("Please call setLeft or setRight!");
	}
	
	
	
	
	
	
	
	
	
	

	@Override
	public SplitNode duplicateSpecifics()
	{
		SplitNode returnValue = new SplitNode(parent, id);
		returnValue.dividerLocation = dividerLocation;
		returnValue.horizontal = horizontal;
		return returnValue;
	}

	@Override
	public JPanel getCustomization(TreeChangedListener listener)
	{
		SplitOptions panel = new SplitOptions(this, listener);
		return panel;
	}

	@Override
	protected DockableSplit createPanel(PanelCache cache, DockablePanel parent) {
		return new DockableSplit(parent);
	}

	@Override
	public DockableNode reduce()
	{
		children.set(LEFT_NODE, children.get(LEFT_NODE).reduce());
		children.set(LEFT_NODE, children.get(LEFT_NODE).reduce());
		setParents();

		if (children.get(LEFT_NODE) instanceof EmptyNode)
		{
			return children.get(RIGHT_NODE);
		}
		if (children.get(RIGHT_NODE) instanceof EmptyNode)
		{
			return children.get(LEFT_NODE);
		}

		return this;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public TabNode toTabs()
	{
		TabNode tab = new TabNode(getParent(), -1);
	        tab.addChild(getLeft());
	        tab.addChild(getRight());
	        getParent().replace(this, tab);
	        return tab;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// ARGH, someday I will undo everything I just did
	public SplitNode()
	{
		this(null, -1);
	}
}
