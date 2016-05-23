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
	
	public FrameNode(String title, DockableNode parent, int id)
	{
		super(parent, id, NodeTypes.WINDOW);
		this.title = title;
	}
	
	
	FrameNode(JsonNode node, DockableNode parent)
	{
		super(node, parent, NodeTypes.WINDOW);
		
		JsonNode loc = node.get("bounds");
		bounds = new Rectangle(
				loc.get("x").asInt(),
				loc.get("y").asInt(),
				loc.get("width").asInt(),
				loc.get("height").asInt());
		closeOperation = node.get("closeOperation").asInt();
		title = node.get("title").asText();
	}


	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}
	
	@Override
	public FrameNode duplicateSpecifics() {
		FrameNode frameNode = new FrameNode(title, parent, id);
		frameNode.setTitle(title);
		frameNode.bounds = bounds;
		frameNode.closeOperation = closeOperation;
		return frameNode;
	}



	public void setCloseOperation(int defaultCloseOperation)
	{
		this.closeOperation = defaultCloseOperation;
	}

	public void setTitle(String text) {
		title = text;
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
	public JPanel getCustomization(TreeChangedListener listener)
	{
		FrameViewer viewer = new FrameViewer(this);
		return viewer;
	}

	@Override
	protected DockableFrame createPanel(PanelCache cache, DockablePanel parent) {
		return new DockableFrame(parent, title, bounds, closeOperation);
	}

	
	
	
	@Override
	public boolean splitable()
	{
		return false;
	}

	@Override
	public boolean tabable()
	{
		return false;
	}
	
	@Override
	public DockableNode addChild(DockableNode node) {
		if (children.isEmpty())
			children.add(node);
		else
			children.set(0, node);
		node.setParent(this);
		return node;
	}
}
