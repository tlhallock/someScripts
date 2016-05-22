package dockable.app;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JComponent;

public class Utils {

	public static boolean hashMapEquals(HashMap<String, String> map1, HashMap<String, String> map2)
	{
		if (map1.size() != map2.size())
		{
			return false;
		}
		
		for (Entry<String, String> entry : map1.entrySet())
		{
			String string = map2.get(entry.getKey());
			if (string == null)
			{
				if (entry.getValue() != null)
				{
					return false;
				}
				else
				{
					continue;
				}
			}
			else if (!entry.getValue().equals(string))
			{
				return false;
			}
		}
		
		return true;
	}

	public static boolean moved(Point p1, Point p2) {
		if (Math.abs(p1.getX() - p2.getX()) > 3)
		{
			return true;
		}
		if (Math.abs(p1.getY() - p2.getY()) > 3)
		{
			return true;
		}
		return false;
	}

	public static Point subtract(Point location, Point locationOnScreen) {
		return new Point(location.x - locationOnScreen.x, location.y - locationOnScreen.y);
	}

	public static boolean wtf(Point location, JComponent c) {
		Point locationOnScreen = c.getLocationOnScreen();
		Rectangle rec = new Rectangle(locationOnScreen.x, locationOnScreen.y, c.getWidth(), c.getHeight());
		return rec.contains(location);
	}
}
