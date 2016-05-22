package dockable.tree;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.PanelCache;
import dockable.dom.DockableFrame;
import dockable.dom.DockablePanel;
import dockable.gui.FrameViewer;
import dockable.gui.TreeChangedListener;


public class FrameNode extends DockableNode
{
	private String type = NodeTypes.WINDOW;
	private Rectangle bounds = new Rectangle(500,500,500,500);
	private int closeOperation = JFrame.EXIT_ON_CLOSE;
	private String title;
	
	private DockableNode content;
	
	public FrameNode(String title, DockableNode parent)
	{
		super(parent);
		this.title = title;
		content = new EmptyNode(this);
	}
	
	
	public FrameNode(JsonNode node, DockableNode parent)
	{
		super(parent);
		
		JsonNode loc = node.get("bounds");
		bounds = new Rectangle(
				loc.get("x").asInt(),
				loc.get("y").asInt(),
				loc.get("width").asInt(),
				loc.get("height").asInt());
		closeOperation = node.get("closeOperation").asInt();
		title = node.get("title").asText();
		
		content = NodeTypes.read(node.get("content"), this);
	}


	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}
	
	public void setContent(DockableNode node)
	{
		content = node;
		node.setParent(this);
	}

	@Override
	public FrameNode duplicate(DockableNode parent) {
		FrameNode returnValue = new FrameNode(title, parent);
		returnValue.content = content.duplicate(this);
		return returnValue;
	}


	public DockableNode getContent() {
		return content;
	}


	public String getTitle() {
		return title;
	}


	public Rectangle getBounds() {
		return bounds;
	}


	public int getCloseOperation() {
		return closeOperation;
	}


	@Override
	public DockableTreeNode createJTree() {
        FrameNode t = this;
        DockableTreeNode node = new DockableTreeNode(title, this)
        {
			@Override
			public JPanel getCustomization(TreeChangedListener listener) {
				FrameViewer viewer = new FrameViewer(t);
				return viewer;
			}
		};
		node.add(content.createJTree());
		return node;
	}


	@Override
	public DockableFrame createPanel(PanelCache cache, DockablePanel parent) {
		return new DockableFrame(cache, parent, this);
	}


	@Override
	public boolean isEmpty() {
		return content.isEmpty();
	}


	@Override
	public DockableNode reduce() {
		content = content.reduce();
		content.setParent(this);
		if (content instanceof EmptyNode)
			return content;
		return this;
	}


	public void setTitle(String text) {
		title = text;
	}


	@Override
	public void replace(DockableNode child, DockableNode newChild)
	{
		if (content == child) {
			setContent(newChild);
		} else {
			throw new RuntimeException("Not a child.");
		}
	}
	
        @Override
		public boolean splitable() { return false; }
        @Override
		public boolean tabable() { return false; }


		@Override
		protected void addChildrenTo(TabNode combined) {
			combined.addChild(content);
		}
}
