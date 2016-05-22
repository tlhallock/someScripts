package dockable.dom;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import dockable.app.PanelCache;
import dockable.app.Utils;
import dockable.tree.DockableNode;
import dockable.tree.FrameNode;


public class DockableFrame extends DockablePanel
{
	private DockablePanel content;
	private JFrame frame;
	private String title;
	
	public DockableFrame(PanelCache cache, DockablePanel parent, FrameNode node)
	{
		this(cache, parent, node.getTitle(), node.getBounds(), node.getCloseOperation());
		content = node.getContent().createPanel(cache, this);
		IdkWhereThisGoes.afix(frame.getContentPane(), content.getComponent());
	}
	
	public DockableFrame(PanelCache registry, DockableRoot components, Point location, DockablePanel content) {
		this(registry, components, "Unnamed frame",
				new Rectangle(location.x, location.y, content.getSize().width, content.getSize().height), 
				JFrame.DISPOSE_ON_CLOSE);
		this.content = content;
		IdkWhereThisGoes.afix(frame.getContentPane(), content.getComponent());
	}
	
	private DockableFrame(
			PanelCache registry,
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
	}

	JFrame getFrame()
	{
		return frame;
	}

	@Override
	public void cache(PanelCache registry) {
		content.cache(registry);
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
	public void release()
	{
		content.release();
		frame.getContentPane().removeAll();
		frame.dispose();
	}

	@Override
	public FrameNode toTree(DockableNode parent)
	{
		FrameNode node = new FrameNode(frame.getTitle(), parent);
		node.setBounds(frame.getBounds());
		node.setContent(content.toTree(node));
		return node;
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
}
