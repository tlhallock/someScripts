package dockable.tree;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import dockable.gui.TreeChangedListener;

public final class DockableTreeNode extends DefaultMutableTreeNode
{

	private DockableNode origin;

	DockableTreeNode(String name, DockableNode origin)
	{
		super(name);
		this.origin = origin;
	}

	public JPanel getCustomization(TreeChangedListener listener)
	{
		return origin.getCustomization(listener);
	}

	public DockableNode getPanel()
	{
		return origin;
	}
}
