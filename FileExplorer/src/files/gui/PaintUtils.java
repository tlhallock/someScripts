package files.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.util.Collection;
import java.util.TreeSet;

import files.app.Application;

public class PaintUtils
{
	private static final int LOWER_BUFFER = 2;
	
	static int paintString(
			Graphics2D g, 
			String text, 
			int xB, 
			int xE, 
			int h, 
			boolean cut,
			boolean failOnOverFlow,
			boolean center)
	{
		
		int fontSize = Application.getApplication().getSettings().getFontSize();
		
		// Probably a better way...
		while (text.length() > 0)
		{
			AttributedString string = new AttributedString(text);
			string.addAttribute(TextAttribute.SIZE, fontSize);
			TextLayout textLayout = new TextLayout(string.getIterator(), g.getFontRenderContext());
			Rectangle2D bounds = textLayout.getBounds();
			if (cut && xB + bounds.getWidth() > xE)
			{
				text = text.substring(0, text.length() - 1);
				continue;
			}

			float y = (float) (h - textLayout.getBaselineOffsets()[0] - textLayout.getDescent() - LOWER_BUFFER);
			if (center)
			{
				int midpoint = (xB + xE ) / 2;
				textLayout.draw(g, (float)(midpoint - bounds.getWidth() / 2), y);
			}
			else
			{
				textLayout.draw(g, xB, y);
			}
			return (int) Math.ceil(bounds.getWidth());
		}
		return 0;
	}

	static int getWidth(Graphics2D g, String text) {
		int fontSize = Application.getApplication().getSettings().getFontSize();
		AttributedString string = new AttributedString(text);
		string.addAttribute(TextAttribute.SIZE, fontSize);
		TextLayout textLayout = new TextLayout(string.getIterator(), g.getFontRenderContext());
		Rectangle2D bounds = textLayout.getBounds();
		return (int) Math.ceil(bounds.getWidth());
	}
	
	private static final int BORDER_WIDTH = 2;
	static void paintBorder(Graphics2D g, Dimension size, Color original)
	{
		g.setColor(Application.getApplication().getColorSelector().getNotShadowBorderColor(original));
		g.fillRect(0, 0, size.width, BORDER_WIDTH);
		g.fillRect(0, 0, BORDER_WIDTH, size.height);

		g.setColor(Application.getApplication().getColorSelector().getShadowBorderColor(original));
		g.fillRect(BORDER_WIDTH, size.height - BORDER_WIDTH, size.width - BORDER_WIDTH, BORDER_WIDTH);
		g.fillRect(size.width - BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, size.height - BORDER_WIDTH);
	}

	public static TreeSet<String> format(String text)
	{
	    TreeSet<String> returnValue = new TreeSet<>();
	    for (String t : text.split("\n"))
	    {
	    	String ext = t.toLowerCase().trim();
	    	if (ext.length()==0)
	    		continue;
	        returnValue.add(ext);
	    }
	    return returnValue;
	}

	public static String format(Collection<String> exts, char sep)
	{
		StringBuilder builder = new StringBuilder();
	    for (String s : exts)
	    {
	    	builder.append(s).append(sep);
	    }
	    return builder.toString();
	}
}
