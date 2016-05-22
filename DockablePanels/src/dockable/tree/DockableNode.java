package dockable.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dockable.app.PanelCache;
import dockable.dom.DockablePanel;




public abstract class DockableNode {
	
	@JsonIgnore
	protected DockableNode parent;
	
	DockableNode(DockableNode parent)
	{
		this.parent = parent;
	}
	
	public DockableNode getParent() {
		return parent;
	}

	public void setParent(DockableNode panelFrameNode) {
		this.parent = panelFrameNode;
	}

	public abstract DockableNode duplicate(DockableNode parent);
	
	public abstract DockableTreeNode createJTree();
	
	public abstract DockablePanel createPanel(PanelCache cache, DockablePanel parent);

	public abstract boolean isEmpty();
	
	public abstract DockableNode reduce();
        
        public boolean collapseable()
        {
            return parent instanceof TabNode; // could be overriden...
        }
        
        public boolean splitable() { return true; }
        public boolean tabable() { return true; }
	
	
	private static final int DEFAULT_NEW_DIVIDER_LOCATION = 50;
	public abstract void replace(DockableNode child, DockableNode newChild);
	protected abstract void addChildrenTo(TabNode combined);
	
	public TabNode addTabs()
	{
		DockableNode op = parent;
        TabNode newNode = new TabNode(op);
        newNode.addChild(this);
        op.replace(this, newNode);
        return newNode;
    }

    public SplitNode splitLeft()
    {
		DockableNode op = parent;
        SplitNode newNode = new SplitNode(parent);
        newNode.setDivider(DEFAULT_NEW_DIVIDER_LOCATION);
        newNode.setLeftRight(true);
        newNode.setRight(this);
        newNode.setLeft(new EmptyNode(newNode));
        op.replace(this, newNode);
        return newNode;
    }

    public SplitNode splitRight()
    {
		DockableNode op = parent;
        SplitNode newNode = new SplitNode(parent);
        newNode.setDivider(DEFAULT_NEW_DIVIDER_LOCATION);
        newNode.setLeftRight(true);
        newNode.setLeft(this);
        newNode.setRight(new EmptyNode(newNode));
        op.replace(this, newNode);
        return newNode;
    }

    public SplitNode splitTop()
    {
		DockableNode op = parent;
        SplitNode newNode = new SplitNode(parent);
        newNode.setDivider(DEFAULT_NEW_DIVIDER_LOCATION);
        newNode.setLeftRight(false);
        newNode.setRight(this);
        newNode.setLeft(new EmptyNode(newNode));
        op.replace(this, newNode);
        return newNode;
    }

    public SplitNode splitBottom()
    {
		DockableNode op = parent;
        SplitNode newNode = new SplitNode(parent);
        newNode.setDivider(DEFAULT_NEW_DIVIDER_LOCATION);
        newNode.setLeftRight(false);
        newNode.setLeft(this);
        newNode.setRight(new EmptyNode(newNode));
        op.replace(this, newNode);
        return newNode;
    }

    public TabNode collapse()
    {
    	TabNode combined = (TabNode) parent;
    	combined.remove(this);
    	addChildrenTo(combined);
    	return combined;
    }
}
