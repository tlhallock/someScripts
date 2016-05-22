package dockable.tree;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import dockable.gui.TreeChangedListener;

public abstract class DockableTreeNode extends DefaultMutableTreeNode {
	
	private DockableNode origin;

	public DockableTreeNode(String name, DockableNode origin)
	{
		super(name);
		this.origin = origin;
	}

    public abstract JPanel getCustomization(TreeChangedListener listener);

    public DockableNode getPanel() {
        return origin;
    }
}
