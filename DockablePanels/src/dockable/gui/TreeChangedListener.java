/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dockable.gui;

import dockable.tree.DockableNode;

/**
 *
 * @author thallock
 */
public interface TreeChangedListener {

    void parentInserted(DockableNode newParent);

	void parentRemoved(DockableNode newParend);
}
