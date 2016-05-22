package dockable.app;

import java.util.HashMap;

import javax.swing.JComponent;

import dockable.intf.PanelFactory;
import dockable.intf.PanelProperties;

public class PanelCache
{
	private HashMap<PanelProperties, JComponent> components = new HashMap<>();
	private PanelFactory driver;
	
	public PanelCache(PanelFactory driver)
	{
		this.driver = driver;
	}

	public JComponent getComponent(PanelProperties properties) {
		JComponent jComponent = components.get(properties);
		if (jComponent != null)
		{
			return jComponent;
		}
		return driver.create(properties);
	}

	public void register(PanelProperties props, JComponent component) {
		components.put(props, component);
	}
}
