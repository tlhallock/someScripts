package dockable.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JPanel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import dockable.app.IdManager;
import dockable.app.PanelCache;
import dockable.app.Writer;
import dockable.dom.DockablePanel;
import dockable.gui.TreeChangedListener;




public abstract class DockableNode
{
	
	@JsonIgnore
	protected DockableNode parent;
	
	@JsonIgnore
	protected int id;
	
	protected String name;

	protected ArrayList<DockableNode> children = new ArrayList<>();
	
	protected DockableNode(DockableNode parent, int existingId, String defaultName)
	{
		this.parent = parent;
		this.id = existingId;
		this.name = defaultName;
	}
	
	
	protected DockableNode(JsonNode node, DockableNode parent, String defaultName)
	{
		this(parent, -1, defaultName);
		readArray(node);
		JsonNode asText = node.get("name");
		if (asText != null)
			name = asText.asText();
	}
	
	
	
	
	
	public DockableNode getParent() {
		return parent;
	}

	public void setParent(DockableNode panelFrameNode) {
		this.parent = panelFrameNode;
	}

	protected void setParents()
	{
		for (DockableNode child : children)
		{
			child.setParent(this);
		}
	}
	

	
	
	
	
	
	

	public DockableNode addChild(DockableNode node) {
		children.add(node);
		node.setParent(this);
		return node;
	}

	public Collection<DockableNode> getChildren()
	{
		return children;
	}

	public void remove(DockableNode dockableNode) {
		children.remove(dockableNode);
	}
	
	protected final void addChildrenTo(TabNode combined)
	{
		for (DockableNode child : children)
			combined.addChild(child);
		children.clear();
	}

	public final void replace(DockableNode child, DockableNode newChild)
	{
		children.replaceAll(x-> { return x == child ? newChild : x; });
		newChild.setParent(this);
	}
	
	

	protected String getDisplayName()
	{
		return name;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String toString()
	{
		return Writer.writer.toString(this);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////
	// Nodes
	
	public final DockableNode duplicate(DockableNode parent)
	{
		DockableNode returnValue = duplicateSpecifics();
		for (DockableNode node : children)
			returnValue.children.add(node.duplicate(this));
		return returnValue;
	}

	protected abstract DockableNode duplicateSpecifics();
	
	
	public boolean isEmpty()
	{
		for (DockableNode child : children)
			if (child.isEmpty())
				return false;
		return true;
	}
	
	public final void contributeIds()
	{
		IdManager.addId(id);
		for (DockableNode child : children)
			child.contributeIds();
	}
	
	public final DockableTreeNode createJTree()
	{
	        DockableTreeNode node = new DockableTreeNode(getDisplayName(), this);
	        for (DockableNode child : children)
	        	node.add(child.createJTree());
	        return node;
	}
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

//	public void duplcateChildren(SeveralChildrenNode returnValue) {
//		for (DockableNode child : returnValue.children)
//			children.add(child.duplicate(this));
//	}

	public DockableTreeNode addChildren(DockableTreeNode node) {
		
		for (DockableNode child : children)
			node.add(child.createJTree());
		return node;
	}



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
			return new EmptyNode(null, -1);
		}
		
		return this;
	}

	
	
	protected void readArray(JsonNode node)
	{
		// isArray
		ArrayNode c = (ArrayNode) node.get("children");
		if (c == null) return;
		for (int i = 0; i < c.size(); i++)
			children.add(NodeTypes.read(c.get(i), this));
	}
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//////////////////////////////////////////////////////////////////////////////////////
	// STRUCTURAL MANIPULATION
	
	
        public boolean collapseable() { return false; }
        public boolean splitable() { return true; }
        public boolean tabable() { return true; }
	
	
	private static final int DEFAULT_NEW_DIVIDER_LOCATION = 50;

	public TabNode addTabs()
	{
		DockableNode op = parent;
		TabNode newNode = new TabNode(op, -1);
		newNode.addChild(this);
		op.replace(this, newNode);
		return newNode;
	}
	private SplitNode split(boolean horizontal, boolean left)
	{
		DockableNode op = parent;
		SplitNode newNode = new SplitNode(parent, -1);
		newNode.setDivider(DEFAULT_NEW_DIVIDER_LOCATION);
		newNode.setLeftRight(horizontal);
		newNode.setLeft ( left ? this : new EmptyNode(newNode, -1));
		newNode.setRight(!left ? this : new EmptyNode(newNode, -1));
		op.replace(this, newNode);
		return newNode;
	}

	public SplitNode splitLeft()
	{
		return split(true, false);
	}

	public SplitNode splitRight()
	{
		return split(true, true);
	}

	public SplitNode splitTop()
	{
		return split(false, false);
	}

	public SplitNode splitBottom()
	{
		return split(false, true);
	}
	

	public TabNode collapse()
	{
		TabNode combined = (TabNode) parent;
		combined.remove(this);
		addChildrenTo(combined);
		return combined;
	}
	

	public final DockablePanel getOrCreatePanel(PanelCache cache, DockablePanel parent)
	{
		DockablePanel node = cache.removeNode(id);
		if (node != null)
		{
			node.setParent(parent);
			return node;
		}
		return createPanel(cache, parent);
	}
	
	
	
	
	//////////////////////////////////////////////////////////
	// TO IMPLEMENT
	
	protected abstract JPanel getCustomization(TreeChangedListener listener);
	protected abstract DockablePanel createPanel(PanelCache cache, DockablePanel parent);
}
