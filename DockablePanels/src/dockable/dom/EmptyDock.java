package dockable.dom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import dockable.app.DELETE_ME;
import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.tree.DockableNode;
import dockable.tree.EmptyNode;

public class EmptyDock extends DockablePanel {

	private JPanel panel;
	
	public EmptyDock(DockablePanel parent) {
		super(parent);
		
		panel = new JPanel();
		panel.setBackground(Color.pink);
		
		
		createPopups();
	}

	@Override
	void setTree(PanelCache cache, DockableNode tree) {}

	@Override
	public void dump(PanelCache registry)
	{
		registry.register(this);
	}

	@Override
	public JComponent getComponent() {
		return panel;
	}

	@Override
	public String getName() {
		return "empty";
	}

	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels)
	{
		if (Utils.wtf(location, panel))
		{
			panels.add(this);
		}
	}

	@Override
	public EmptyNode toTree()
	{
		return DELETE_ME.createEmpty(panelId);
	}

	@Override
	public Dimension getSize()
	{
		return panel.getSize();
	}

	@Override
	public void remove(DockablePanel toRemove) {
		throw new RuntimeException("Is not a parent!");
	}
	
	


	@Override
	public void replace(DockablePanel child, DockablePanel newChild)
	{
		throw new RuntimeException("No children!");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	private void createPopups()
	{
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item;
		EmptyDock s = this;
		
		
		
		

		item = new JMenuItem("Collapse");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("collapse me!");
			}});
		menu.add(item);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		item = new JMenuItem("Split bottom");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.replace(s, wrap(parent, s, false, true, getComponent().getHeight() / 2));
			}});
		menu.add(item);
		
		item = new JMenuItem("Split top");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.replace(s, wrap(parent, s, false, false, getComponent().getHeight() / 2));
			}});
		menu.add(item);

		item = new JMenuItem("Split right");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.replace(s, wrap(parent, s, true, true, getComponent().getWidth() / 2));
			}});
		menu.add(item);
		

		item = new JMenuItem("Split left");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.replace(s, wrap(parent, s, true, false, getComponent().getWidth() / 2));
			}});
		menu.add(item);
		
		

		item = new JMenuItem("Tabulate");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.replace(s, wrap(parent, s));
			}
		});
		menu.add(item);
		
		getComponent().setComponentPopupMenu(menu);
	}
}
