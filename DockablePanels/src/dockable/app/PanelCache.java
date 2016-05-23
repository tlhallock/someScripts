package dockable.app;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JComponent;

import dockable.dom.DockablePanel;
import dockable.intf.PanelFactory;
import dockable.intf.PanelProperties;

public class PanelCache
{
	private HashMap<PanelProperties, JComponent> components = new HashMap<>();
	private HashMap<Integer, DockablePanel> panels = new HashMap<>();
	
	private PanelFactory driver;
	
	public PanelCache(PanelFactory driver)
	{
		this.driver = driver;
	}
	
	
	public void dispose()
	{
		panels.values().stream().forEach(x -> x.disposeIfJFrame());
	}
	
	
	public DockablePanel removeNode(int id)
	{
		if (id < 0)
			return null;
		return panels.remove(id);
	}
	
	public JComponent getOrCreateComponent(PanelProperties properties) {
		JComponent jComponent = components.get(properties);
		if (jComponent != null)
		{
			return jComponent;
		}
		if (driver == null)
		{
			throw new RuntimeException("This should not happen!");
		}
		return driver.create(properties);
	}

	public void register(DockablePanel node) {
		panels.put(node.getId(), node);
	}
	public void register(PanelProperties props, JComponent component) {
		components.put(props, component);
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (Entry<Integer, DockablePanel> entry : panels.entrySet())
			builder.append(entry.getKey()).append(':').append(entry.getValue().getClass().getName()).append('\n');
		return builder.toString();
	}
}
