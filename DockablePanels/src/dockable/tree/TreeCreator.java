package dockable.tree;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;

import dockable.app.DockableApplication;
import dockable.app.Writer;
import dockable.intf.PanelProperties;

public class TreeCreator
{
	protected RootNode rootNode = new RootNode();
	private DockableApplication application;
	
	public TreeCreator(DockableApplication application)
	{
		this.application = application;
	}
	
	public SplitNode createSplit(DockableNode left, DockableNode right, int dividerLocation, boolean leftRight)
	{
		return createSplit(-1, left, right, dividerLocation, leftRight);
	}
	
	public TabNode createTab(Collection<DockableNode> children) {
		return createTab(-1, children);
	}
	
	public LeafNode createPanel(PanelProperties props) {
		return createPanel(-1, props);
	}

	public FrameNode createWindow(String string, DockableNode content, Rectangle bounds, int defaultCloseOperation)
	{
		return createWindow(-1, string, content, bounds, defaultCloseOperation);
	}
	
	public void commit()
	{
		application.setTree(rootNode);
	}
	
	
	
	
	
	
	
	
	
	
	

	protected EmptyNode createEmpty(int panelId)
	{
		return new EmptyNode(null, panelId);
	}

	protected SplitNode createSplit(int panelId, DockableNode tree, DockableNode tree2, int dividerLocation, boolean leftRight)
	{
		SplitNode splitNode = new SplitNode(null, panelId);
		splitNode.setLeft(tree);
		splitNode.setRight(tree2);
		splitNode.setDivider(dividerLocation);
		splitNode.setLeftRight(leftRight);
		return splitNode;
	}
	
	protected TabNode createTab(int id, Collection<DockableNode> children) {
		TabNode tabNode = new TabNode(null, id);
		children.stream().forEach(x -> tabNode.addChild(x));
		return tabNode;
	}
	
	protected LeafNode createPanel(int id, PanelProperties props) {
		LeafNode leafNode = new LeafNode(props, null, id);
		return leafNode;
	}

	protected FrameNode createWindow(int id, String string, DockableNode content, Rectangle bounds, int defaultCloseOperation)
	{
		FrameNode frameNode = new FrameNode(string, rootNode, id);
		frameNode.addChild(content);
		frameNode.setBounds(bounds);
		frameNode.setCloseOperation(defaultCloseOperation);
		rootNode.addChild(frameNode);
		return frameNode;
	}
	

	public static RootNode read(File file) throws JsonProcessingException, IOException
	{
            	return new RootNode(Writer.writer.read(file));
	}
}
