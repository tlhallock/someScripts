package dockable.intf;

import javax.swing.JComponent;

public interface PanelFactory
{
	public JComponent create(PanelProperties options);
}
