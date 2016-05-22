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
	private String type = NodeTypes.SPLIT;
	
	private int dividerLocation = -1;
	private boolean horizontal = true;
	
	private DockableNode left;
	private DockableNode right;
	
	public SplitNode(DockableNode parent)
	{
		super(parent);
		
		left = new EmptyNode(this);
		right = new EmptyNode(this);
	}

	public SplitNode(JsonNode node, DockableNode parent)
	{
		super(parent);
		
		dividerLocation = node.get("dividerLocation").asInt();
		horizontal = node.get("horizontal").asBoolean();
		left = NodeTypes.read(node.get("left"), this);
		right = NodeTypes.read(node.get("right"), this);
	}

	public void setLeft(DockableNode leftNode) {
		
		if (left.equals(leftNode))
		{
			return;
		}
		
		leftNode.setParent(this);
		left = leftNode;
	}

	public void setRight(DockableNode rightNode) {
		
		if (right.equals(rightNode))
		{
			return;
		}

		rightNode.setParent(this);
		right = rightNode;
	}

	@Override
	public SplitNode duplicate(DockableNode parent)
	{
		SplitNode returnValue = new SplitNode(parent);
		returnValue.dividerLocation = dividerLocation;
		returnValue.horizontal = horizontal;
		
		returnValue.left = left.duplicate(this);
		returnValue.right = right.duplicate(this);
		
		return returnValue;
	}


	public DockableNode getLeft() {
		return left;
	}


	public DockableNode getRight() {
		return right;
	}

	public int getDividerLocation() {
		return dividerLocation;
	}

	@Override
	public DockableTreeNode createJTree() {
            SplitNode t = this;
		DockableTreeNode defaultMutableTreeNode = new DockableTreeNode("Divider", this)
		{
			@Override
			public JPanel getCustomization(TreeChangedListener listener) {
				SplitOptions panel = new SplitOptions(t, listener);
				return panel;
			}
		};
                
		defaultMutableTreeNode.add(left.createJTree());
		defaultMutableTreeNode.add(right.createJTree());
		
		return defaultMutableTreeNode;
	}

	@Override
	public DockableSplit createPanel(PanelCache cache, DockablePanel parent) {
		return new DockableSplit(cache, parent, this);
	}

	public void setDivider(int i) {
		this.dividerLocation = i;
	}

	@Override
	public boolean isEmpty() {
		return left.isEmpty() && right.isEmpty();
	}

	@Override
	public DockableNode reduce()
	{
		left = left.reduce();
		left.setParent(this);
		right = right.reduce();
		right.setParent(this);
		
		if (left instanceof EmptyNode)
		{
			return right;
		}
		if (right instanceof EmptyNode)
		{
			return left;
		}
		
		return this;
	}

    public void setLeftRight(boolean b)
    {
        horizontal = b;
    }

    public boolean getLeftRight() {
        return horizontal;
    }

	@Override
	public void replace(DockableNode child, DockableNode newChild)
	{
		if (left == child)
		{
			setLeft(newChild);
		}
		else if (right == child)
		{
			setRight(newChild);
		}
		else
		{
			throw new RuntimeException("Not a child.");
		}
	}

	@Override
	protected void addChildrenTo(TabNode combined)
	{
		combined.addChild(left);
		combined.addChild(right);
	}
}
