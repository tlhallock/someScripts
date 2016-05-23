package dockable.dom;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import dockable.app.DELETE_ME;
import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.tree.DockableNode;
import dockable.tree.SplitNode;


public class DockableSplit extends DockablePanel
{
	private JSplitPane pane;
	
	private DockablePanel left;
	private DockablePanel right;
	
	public DockableSplit(DockablePanel parent)
	{
		super(parent);

		pane = new JSplitPane();
		
		// left and right are null?
		
		createPopups();
	}

	@Override
	void setTree(PanelCache cache, DockableNode tree)
	{
		if (!(tree instanceof SplitNode))
			throw new RuntimeException("Better not happen.");
		SplitNode s = (SplitNode) tree;

		pane.setOrientation(s.getLeftRight() ? JSplitPane.HORIZONTAL_SPLIT : JSplitPane.VERTICAL_SPLIT);
		pane.setDividerLocation(s.getDividerLocation());
		
		left  = s.getLeft().getOrCreatePanel(cache, this);
		right = s.getRight().getOrCreatePanel(cache, this);
		
		pane.setLeftComponent(left.getComponent());
		pane.setRightComponent(right.getComponent());
		
		left.setTree(cache, s.getLeft());
		right.setTree(cache, s.getRight());
	}
	
	@Override
	public void dump(PanelCache registry)
	{
		registry.register(this);
		left.dump(registry);
		right.dump(registry);
	}

	@Override
	public JComponent getComponent() {
		return pane;
	}

	@Override
	public String getName() {
		return "split";
	}

	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels) {
		// Could change this to be more efficient...
		Component findComponentAt = pane.findComponentAt(Utils.subtract(location, pane.getLocationOnScreen()));
		if (findComponentAt != null)
		{
			left.findComponentAt(location, panels);
			right.findComponentAt(location, panels);
		}
	}

	@Override
	public SplitNode toTree()
	{
		return DELETE_ME.createSplit(
				panelId,
				left.toTree(),
				right.toTree(),
				pane.getDividerLocation(),
				pane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT);
	}

	@Override
	public Dimension getSize() {
		return pane.getSize();
	}

	@Override
	public void remove(DockablePanel toRemove)
	{
		if (left == toRemove)
		{
			pane.remove(left.getComponent());
			left = new EmptyDock(this);
			pane.setLeftComponent(left.getComponent());
		}
		else if (right == toRemove)
		{
			pane.remove(right.getComponent());
			right = new EmptyDock(this);
			pane.setLeftComponent(right.getComponent());
		}
	}
	

	private void setLeft(DockablePanel left)
	{
		this.left = left;
		pane.setLeftComponent(left.getComponent());
		createPopups();
	}
	private void setRight(DockablePanel left)
	{
		this.right = left;
		pane.setRightComponent(left.getComponent());
		createPopups();
	}
	
	
	
	
	

	private void createPopups()
	{
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item;
		DockableSplit s = this;
		
		

		item = new JMenuItem("Left: Split bottom");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setLeft(wrap(s, left, false, true, left.getComponent().getHeight() / 2));
			}});
		menu.add(item);
		
		item = new JMenuItem("Left: Split top");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setLeft(wrap(s, left, false, false, left.getComponent().getHeight() / 2));
			}});
		menu.add(item);

		item = new JMenuItem("Left: Split right");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setLeft(wrap(s, left, true, true, left.getComponent().getWidth() / 2));
			}});
		menu.add(item);
		

		item = new JMenuItem("Left: Split left");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setLeft(wrap(s, left, true, false, left.getComponent().getWidth() / 2));
			}});
		menu.add(item);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		item = new JMenuItem("Right: Split bottom");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setRight(wrap(s, right, false, true, right.getComponent().getHeight() / 2));
			}});
		menu.add(item);
		
		item = new JMenuItem("Right: Split top");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setRight(wrap(s, right, false, false, right.getComponent().getHeight() / 2));
			}});
		menu.add(item);

		item = new JMenuItem("Right: Split right");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setRight(wrap(s, right, true, true, right.getComponent().getWidth() / 2));
			}});
		menu.add(item);
		

		item = new JMenuItem("Right: Split left");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setRight(wrap(s, right, true, false, right.getComponent().getWidth() / 2));
			}});
		menu.add(item);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		if (!(right instanceof DockableTab))
		{
		item = new JMenuItem("Tabulate left");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setLeft(wrap(s, left));
			}
		});
		menu.add(item);
		}

		if (!(right instanceof DockableTab))
		{
		item = new JMenuItem("Tabulate right");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setRight(wrap(s, right));
			}
		});
		menu.add(item);
		}
		
		pane.setComponentPopupMenu(menu);
	}

	@Override
	public void replace(DockablePanel child, DockablePanel newChild)
	{
		if (child == left)
			setLeft(newChild);
		else if (child == right)
			setRight(newChild);
		else
			throw new RuntimeException("Child not found");
	}
}
