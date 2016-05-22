package dockable.intf;

import dockable.tree.LeafNode;

public interface ApplicationListener {

	void panelCreated(LeafNode leaf) throws Exception;
}
