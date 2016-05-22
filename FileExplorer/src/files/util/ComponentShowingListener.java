package files.util;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JComponent;

public abstract class ComponentShowingListener implements HierarchyListener
{
	private JComponent component;

	public ComponentShowingListener(JComponent component)
	{
		this.component = component;
		component.addHierarchyListener(this); // should not be here...
	}

	@Override
	public void hierarchyChanged(HierarchyEvent e) {

		switch ((int) e.getChangeFlags())
		{
		case HierarchyEvent.ANCESTOR_MOVED:
			break;
		case HierarchyEvent.ANCESTOR_RESIZED:
			break;
		case HierarchyEvent.DISPLAYABILITY_CHANGED:
			break;
		case HierarchyEvent.PARENT_CHANGED:
			break;
		case HierarchyEvent.SHOWING_CHANGED:
			if (component.isShowing())
				isShowing();
			else
				isNotShowing();
			break;
		}
	}

	protected abstract void isShowing();
	protected abstract void isNotShowing();
}