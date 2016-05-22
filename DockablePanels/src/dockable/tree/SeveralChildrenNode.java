package dockable.tree;

import java.util.Collection;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public abstract class SeveralChildrenNode extends DockableNode {

	private LinkedList<DockableNode> children = new LinkedList<>();
		
	public SeveralChildrenNode(DockableNode parent)
	{
		super(parent);
	}

	public void duplcateChildren(SeveralChildrenNode returnValue) {
		for (DockableNode child : returnValue.children)
			children.add(child.duplicate(this));
	}

	public Collection<DockableNode> getChildren()
	{
		return children;
	}

	public DockableTreeNode addChildren(DockableTreeNode node) {
		
		for (DockableNode child : children)
			node.add(child.createJTree());
		return node;
	}


	public void addChild(DockableNode node) {
		children.add(node);
		node.setParent(this);
	}

	@Override
	public DockableNode reduce() {
		
		LinkedList<DockableNode> clone = (LinkedList<DockableNode>) children.clone();
		children.clear();
		
		boolean allEmpty = true;
		
		for (DockableNode node : clone)
		{
			if (!(node instanceof EmptyNode))
			{
				children.add(node);
				node.setParent(this);
				allEmpty = false;
			}
		}
		
		if (allEmpty)
		{
			return new EmptyNode(null);
		}
		
		return this;
	}

	
	
	protected void readArray(JsonNode node)
	{
		// isArray
		ArrayNode c = (ArrayNode) node.get("children");
		for (int i = 0; i < c.size(); i++)
			children.add(NodeTypes.read(c.get(i), this));
	}
	
	

	@Override
	public boolean isEmpty() {
		for (DockableNode n : children)
		{
			if (!n.isEmpty())
				return false;
		}
		return true;
	}

	@Override
	public void replace(DockableNode child, DockableNode newChild)
	{
		children.replaceAll(x-> { return x == child ? newChild : x; });
	}


	@Override
	protected void addChildrenTo(TabNode combined)
	{
		for (DockableNode child : children)
			combined.addChild(child);
		children.clear();
	}

	public void remove(DockableNode dockableNode) {
		children.remove(dockableNode);
	}
}
