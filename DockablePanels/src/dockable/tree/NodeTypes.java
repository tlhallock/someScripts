package dockable.tree;

import com.fasterxml.jackson.databind.JsonNode;

class NodeTypes {
	static final String ROOT = "root";
	static final String COMPONENT = "component";
	static final String EMPTY = "empty";
	static final String SPLIT = "split";
	static final String TABS = "tabs";
	static final String WINDOW = "frame";
	
	static DockableNode read(JsonNode node, DockableNode parent)
	{
		JsonNode jsonNode = node.get("type");
		if (jsonNode == null)
		{
			return null;
		}
		switch (jsonNode.asText())
		{
		case ROOT:
			return new RootNode(node);
		case COMPONENT:
			return new LeafNode(node, parent);
		case EMPTY:
			return new EmptyNode(node, parent);
		case SPLIT:
			return new SplitNode(node, parent);
		case TABS:
			return new TabNode(node, parent);
		case WINDOW:
			return new FrameNode(node, parent);
			default:
				throw new RuntimeException("Unkown type: " + jsonNode);
		}
	}
}
