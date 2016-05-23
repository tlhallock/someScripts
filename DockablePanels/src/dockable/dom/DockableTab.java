package dockable.dom;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import dockable.app.DELETE_ME;
import dockable.app.DockableApplication;
import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.tree.DockableNode;
import dockable.tree.TabNode;


public class DockableTab extends DockablePanel
{
	private ArrayList<DockablePanel> children = new ArrayList<>();

	private JTabbedPane pane;
	
	private int lastRightClick;
	
	public DockableTab(DockablePanel parent)
	{
		super(parent);

		pane = new JTabbedPane();
		
		pane.addMouseListener(new MouseAdapter() {
			
			private Point locationOnScreen;
			private int dragIndex = -1;
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (locationOnScreen == null || dragIndex < 0)
					return;
				Point releasePoint = e.getLocationOnScreen();
				if (Utils.moved(locationOnScreen, releasePoint))
				{
					move(dragIndex, releasePoint);
				}
				locationOnScreen = null;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				dragIndex = pane.indexAtLocation(e.getX(), e.getY());
				if (dragIndex < 0)
					return;
				
				if (SwingUtilities.isRightMouseButton(e))
					lastRightClick = dragIndex;
				else if (SwingUtilities.isLeftMouseButton(e))
					locationOnScreen = e.getLocationOnScreen();
			}
		});
		
		createPopups();
	}

	@Override
	void setTree(PanelCache cache, DockableNode node)
	{
		for (DockableNode child : node.getChildren())
		{
			DockablePanel childPanel = child.getOrCreatePanel(cache, this);
			pane.add(childPanel.getName(), childPanel.getComponent());
			children.add(childPanel);
			childPanel.setTree(cache, child);
		}
	}
	
	private void move(int index, Point newLocation) {
		DockableApplication application = getApplication();
		application.move(children.get(index), newLocation);
	}

	@Override
	public void dump(PanelCache registry) {
		registry.register(this);
		for (DockablePanel child : children)
			child.dump(registry);
		pane.removeAll();
	}

	@Override
	public JComponent getComponent() {
		return pane;
	}

	@Override
	public String getName() {
		return "tabs";
	}


	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels) {
		// Could change this to be more efficient...
		Component findComponentAt = pane.findComponentAt(Utils.subtract(location, pane.getLocationOnScreen()));
		if (findComponentAt == null)
			return;
		for (DockablePanel child : children)
			child.findComponentAt(location, panels);
	}

	@Override
	public TabNode toTree()
	{
		LinkedList<DockableNode> tmp = new LinkedList<>();
		children.stream().forEach(x -> tmp.add(x.toTree()));
		return DELETE_ME.createTab(panelId, tmp);
	}


	@Override
	public Dimension getSize() {
		return pane.getSize();
	}


	@Override
	public void remove(DockablePanel toRemove)
	{
		children.remove(toRemove);
		pane.remove(toRemove.getComponent());
	}

	@Override
	public void replace(DockablePanel child, DockablePanel newChild)
	{
		int index = -1;
		for (DockablePanel panel : children)
		{
			if (panel == child)
			{
				break;
			}
			index++;
		}
		if (index >= children.size())
		{
			throw new RuntimeException("Component not found!");
		}
		// not the right spot!!!
		pane.addTab(newChild.getName(), newChild.getComponent());
		children.remove(child);
		children.add(newChild);
		
		// should now remove the old tab if it is still here!!!!!!!!!!!!!!!!!!!!!!!!!!
		
//		pane.setTabComponentAt(index, newChild.getComponent());
//		DockablePanel set = children.set(index, newChild);
//		if (set != child)
//		{
//			throw new RuntimeException("Child not where expected!");
//		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private void createPopups()
	{
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item;
		DockableTab s = this;

		item = new JMenuItem("Split bottom");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (lastRightClick < 0)
				{
					System.out.println("None selected!");
					return;
				}
				DockablePanel child = children.get(lastRightClick);
				replace(child, wrap(s, child, false, true, child.getComponent().getHeight() / 2));
			}});
		menu.add(item);
		
		item = new JMenuItem("Split top");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (lastRightClick < 0)
				{
					System.out.println("None selected!");
					return;
				}
				DockablePanel child = children.get(lastRightClick);
				replace(child, wrap(s, child, false, false, child.getComponent().getHeight() / 2));
			}});
		menu.add(item);

		item = new JMenuItem("Split right");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (lastRightClick < 0)
				{
					System.out.println("None selected!");
					return;
				}
				DockablePanel child = children.get(lastRightClick);
				replace(child, wrap(s, child, true, true, child.getComponent().getWidth() / 2));
			}});
		menu.add(item);
		

		item = new JMenuItem("Split left");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (lastRightClick < 0)
				{
					System.out.println("None selected!");
					return;
				}
				DockablePanel child = children.get(lastRightClick);
				replace(child, wrap(s, child, true, false, child.getComponent().getWidth() / 2));
			}});
		menu.add(item);
		
		

		item = new JMenuItem("Tabulate");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Timer().scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run()
					{
						System.out.println(pane.getTabCount());
						
					}}, 1000,1000);;
				if (lastRightClick < 0)
				{
					System.out.println("None selected!");
					return;
				}
				DockablePanel child = children.get(lastRightClick);
				replace(child, wrap(s, child));
			}
		});
		menu.add(item);
		
		getComponent().setComponentPopupMenu(menu);
	}
}
