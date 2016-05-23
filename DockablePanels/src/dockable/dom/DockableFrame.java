package dockable.dom;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import dockable.app.DELETE_ME;
import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.tree.DockableNode;
import dockable.tree.FrameNode;


public class DockableFrame extends DockablePanel
{
	private DockablePanel content;
	private JFrame frame;
	private String title;
	
	public DockableFrame(
			DockableRoot components,
			Point location, 
			DockablePanel content)
	{
		this(components, "Unnamed frame",
				new Rectangle(location.x, location.y, content.getSize().width, content.getSize().height), 
				JFrame.DISPOSE_ON_CLOSE);
		setContent(content);
	}
	
	public DockableFrame(
			DockablePanel parents,
			String title,
			Rectangle bounds,
			int closeOperation)
	{
		super(parents);
		
		frame = new JFrame();
		frame.setTitle(this.title = title);
		frame.setBounds(bounds);
		frame.setDefaultCloseOperation(closeOperation);

		createPopups();
	}



	@Override
	void setTree(PanelCache cache, DockableNode node)
	{
		if (!(node instanceof FrameNode))
			throw new RuntimeException("Better not happen.");
		FrameNode f = (FrameNode) node;
		
		frame.setTitle(f.getTitle());
		frame.setBounds(f.getBounds());
		frame.setDefaultCloseOperation(f.getCloseOperation());
		
		DockableNode child = node.getChildren().iterator().next();
		setContent(child.getOrCreatePanel(cache, this));
		content.setTree(cache, child);
	}
	
	private void setContent(DockablePanel node)
	{
		content = node;
		frame.getContentPane().removeAll();
//		frame.getContentPane().add(content.getComponent());
		IdkWhereThisGoes.afix(frame.getContentPane(), content.getComponent());
		node.setParent(this);

		createPopups();
	}

	JFrame getFrame()
	{
		return frame;
	}

	@Override
	public void dump(PanelCache registry) {
		registry.register(this);
		frame.getContentPane().removeAll();
		content.dump(registry);
	}

	@Override
	public JComponent getComponent() {
		throw new RuntimeException("Frames don't have components...");
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public void findComponentAt(Point location, LinkedList<DockablePanel> panels)
	{
		// Could change this to be more efficient...
		Component findComponentAt = frame.findComponentAt(Utils.subtract(location, frame.getLocationOnScreen()));
		if (findComponentAt != null)
			content.findComponentAt(location, panels);
	}

	@Override
	public FrameNode toTree()
	{
		return DELETE_ME.createWindow(panelId, frame.getTitle(), content.toTree(), frame.getBounds(), frame.getDefaultCloseOperation());
	}

	@Override
	public Dimension getSize() {
		return frame.getSize();
	}

	@Override
	public void remove(DockablePanel toRemove)
	{
		if (toRemove != content)
			return;
		frame.getContentPane().removeAll();
		content = new EmptyDock(this);
		IdkWhereThisGoes.afix(frame.getContentPane(), content.getComponent());
	}

	public void setVisible(boolean b) {
		frame.setVisible(true);
	}
	

	public void disposeIfJFrame()
	{
		frame.dispose();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private void createPopups()
	{
		DockableFrame t = this;
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item = new JMenuItem("Split bottom");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setContent(wrap(t, content, false, true, frame.getContentPane().getHeight() / 2));
			}});
		menu.add(item);
		
		item = new JMenuItem("Split top");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setContent(wrap(t, content, false, false, frame.getContentPane().getHeight() / 2));
			}});
		menu.add(item);

		item = new JMenuItem("Split right");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setContent(wrap(t, content, true, true, frame.getContentPane().getWidth() / 2));
			}});
		menu.add(item);
		

		item = new JMenuItem("Split left");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setContent(wrap(t, content, true, false, frame.getContentPane().getWidth() / 2));
			}});
		menu.add(item);

		if (!(content instanceof DockableTab))
		{
		item = new JMenuItem("Tabulate");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Before");
				System.out.println(content);
				setContent(wrap(t, content));
				System.out.println("After");
				System.out.println(content);
			}
		});
		menu.add(item);
		}
		
		frame.getRootPane().setComponentPopupMenu(menu);
	}

	@Override
	public void replace(DockablePanel child, DockablePanel newChild)
	{
		if (child != content)
			throw new RuntimeException("Child does not match!");
		setContent(newChild);
	}
}
