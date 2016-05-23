package dockable.app;

import java.awt.Rectangle;
import java.util.Collection;

import dockable.intf.PanelProperties;
import dockable.tree.DockableNode;
import dockable.tree.EmptyNode;
import dockable.tree.FrameNode;
import dockable.tree.LeafNode;
import dockable.tree.RootNode;
import dockable.tree.SplitNode;
import dockable.tree.TabNode;

// I need to delete this class
public class DELETE_ME
{
	public static EmptyNode createEmpty(int panelId)
	{
		return new EmptyNode(null, panelId);
	}

	public static SplitNode createSplit(int panelId, DockableNode tree, DockableNode tree2, int dividerLocation, boolean leftRight)
	{
		SplitNode splitNode = new SplitNode(null, panelId);
		splitNode.setLeft(tree);
		splitNode.setRight(tree2);
		splitNode.setDivider(dividerLocation);
		splitNode.setLeftRight(leftRight);
		return splitNode;
	}
	
	public static TabNode createTab(int id, Collection<DockableNode> children) {
		TabNode tabNode = new TabNode(null, id);
		children.stream().forEach(x -> tabNode.addChild(x));
		return tabNode;
	}
	
	public static LeafNode createPanel(int id, PanelProperties props) {
		LeafNode leafNode = new LeafNode(props, null, id);
		return leafNode;
	}

	public static FrameNode createWindow(int id, String string, DockableNode content, Rectangle bounds, int defaultCloseOperation)
	{
		FrameNode frameNode = new FrameNode(string, null, id);
		frameNode.addChild(content);
		frameNode.setBounds(bounds);
		frameNode.setCloseOperation(defaultCloseOperation);
		return frameNode;
	}
	
	public static RootNode createRootNode(Collection<DockableNode> windows)
	{
		RootNode rootNode = new RootNode();
		windows.stream().forEach(x -> rootNode.addChild(x));
		return rootNode;
	}

}