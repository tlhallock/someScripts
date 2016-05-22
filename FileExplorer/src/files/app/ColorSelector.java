package files.app;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import files.app.Logger.LogLevel;

public final class ColorSelector
{
	private HashMap<String, SerializableColor> colors = new HashMap<>();
	
	private ColorSelector() {}
	
	
	public Color getFileBackgroundColor(boolean even, boolean marked, boolean highlighted)
	{
		
		if (highlighted)
		{
			if (marked)
			{
				return colors.get(DETAILS_FILE_BACKGROUND_HIGHLIGHTED_MARKED).getColor();
			}
			else
			{
				return colors.get(DETAILS_FILE_BACKGROUND_HIGHLIGHTED).getColor();
			}
		}
		if (even)
		{
			if (marked)
			{
				return colors.get(DETAILS_FILE_BACKGROUND_EVEN_MARKED).getColor();
			}
			else
			{
				return colors.get(DETAILS_FILE_BACKGROUND_EVEN).getColor();
			}
		}
		else
		{
			if (marked)
			{
				return colors.get(DETAILS_FILE_BACKGROUND_MARKED).getColor();
			}
			else
			{
				return colors.get(DETAILS_FILE_BACKGROUND).getColor();
			}
		}
	}
	public Color getFileForegroundColor(boolean even, boolean marked, boolean highlighted)
	{
		if (highlighted)
		{
			if (marked)
			{
				return colors.get(DETAILS_FILE_FOREGROUND_HIGHLIGHTED_MARKED).getColor();
			}
			else
			{
				return colors.get(DETAILS_FILE_FOREGROUND_HIGHLIGHTED).getColor();
			}
		}
		if (even)
		{
			if (marked)
			{
				return colors.get(DETAILS_FILE_FOREGROUND_EVEN_MARKED).getColor();
			}
			else
			{
				return colors.get(DETAILS_FILE_FOREGROUND_EVEN).getColor();
			}
		}
		else
		{
			if (marked)
			{
				return colors.get(DETAILS_FILE_FOREGROUND_MARKED).getColor();
			}
			else
			{
				return colors.get(DETAILS_FILE_FOREGROUND).getColor();
			}
		}
	}
	public Color getColumnHeaderBackgroundColor(boolean folderHighlighted)
	{
		return colors.get(folderHighlighted ? DETAILS_LIST_HEADER_BACKGROUND_HIGHLIGHTED : DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED).getColor();
	}
	public Color getColumnHeaderForegroundColor(boolean highlighted)
 {
		return colors.get(highlighted ? DETAILS_LIST_HEADER_FOREGROUND_HIGHLIGHTED : DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED).getColor();
	}
	
	public Color getColumnDividerColor()
	{
		return colors.get(DETAILS_COLUMN_DIVIDER).getColor();
	}
	public Color getListBackground() {
		return colors.get(DETAILS_LIST_BACKGROUND).getColor();
	}
	public Color getFileDividerColor(boolean even, boolean marked, boolean highlighted) {
		return colors.get(DETAILS_FILE_DIVIDER).getColor();
	}
	public Color getColumnDragColor() {
		return colors.get(DETAILS_COLUMN_DRAG).getColor();
	}
	public Color getColumnHeaderDragColor() {
		return colors.get(DETAILS_LIST_HEADER_DRAG).getColor();
	}
	public Color getNotShadowBorderColor(Color original)
	{
		return new Color((255+original.getRed()) / 2, (255+original.getGreen()) / 2, (255+original.getBlue()) / 2);
//		return Color.white;
	}
	public Color getShadowBorderColor(Color original)
	{
		return colors.get(SHADOW).getColor();
	}
	public Color getSimpleViewBackground() {
		return colors.get(SIMPLE_FILE_BACKGROUND).getColor();
	}
	

	
	
	
	

	public static ColorSelector loadHumanReadable(Path path) throws IOException
	{
		ColorSelector read = Serialization.writer.read(path.toFile(), ColorSelector.class);
		read.fillRestHashMap();
		return read;
	}

	public void writeHumanReadable(Path path) throws IOException
	{
		Serialization.writer.write(path.toFile(), this);
	}
	public void save()
	{
		try {
			writeHumanReadable(Paths.get(Application.getApplication().getSettings().getColorsPath()));
		} catch (IOException e) {
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to write colors file", e);
		}
	}
	
	public static ColorSelector load()
	{
		Path path = Paths.get(Application.getApplication().getSettings().getColorsPath());
		try {
			return loadHumanReadable(path);
		} catch (IOException e) {
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to read colors file");
			e.printStackTrace();
			try {
				new ColorSelector().fillRestHashMap().save();
				return loadHumanReadable(path);
			} catch (IOException e2) {
				Application.getApplication().getLogger().log(LogLevel.Minimal, "Unable to read colors file", e2);
				return null;
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static final Color LIGHT_GREEN	= new Color(153, 255, 153);
	private static final Color DARK_GREEN	= new Color( 51, 204,  51);
	private static final Color COOL_ORANGE	= new Color(255, 153,  51);
	private static final Color OTHER_ORANGE	= new Color(255, 204,   0);
	
	private static final String SHADOW = "shadow";
	private static final String DETAILS_COLUMN_DRAG = "column drag";
	private static final String DETAILS_LIST_BACKGROUND = "list background (is it used?)";
	private static final String DETAILS_COLUMN_DIVIDER = "column divider";
	private static final String DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED = "DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED";
	private static final String DETAILS_LIST_HEADER_FOREGROUND_HIGHLIGHTED = "DETAILS_LIST_HEADER_FOREGROUND__HIGHLIGHTED";
	private static final String DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED = "DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED";
	private static final String DETAILS_LIST_HEADER_BACKGROUND_HIGHLIGHTED = "DETAILS_LIST_HEADER_BACKGROUND__HIGHLIGHTED";
	private static final String DETAILS_LIST_HEADER_DRAG = "header drag";
	private static final String DETAILS_FILE_DIVIDER = "file divider";
	

	private static final String SIMPLE_FILE_BACKGROUND = "simple view background";	
	
	
	private static final String DETAILS_FILE_BACKGROUND_HIGHLIGHTED_MARKED 	= "detail file background, highlighted, marked";
	private static final String DETAILS_FILE_BACKGROUND_HIGHLIGHTED 		= "detail file background, highlighted, not marked";
	private static final String DETAILS_FILE_BACKGROUND_EVEN_MARKED 		= "detail file background, even, marked";
	private static final String DETAILS_FILE_BACKGROUND_EVEN 				= "detail file background, even, not marked";
	private static final String DETAILS_FILE_BACKGROUND_MARKED 				= "detail file background, odd, marked";
	private static final String DETAILS_FILE_BACKGROUND 					= "detail file background, odd, not marked";
	private static final String DETAILS_FILE_FOREGROUND_HIGHLIGHTED_MARKED  = "detail file background, highlighted, marked";    
	private static final String DETAILS_FILE_FOREGROUND_HIGHLIGHTED			= "detail file foreground, highlighted, not marked";
	private static final String DETAILS_FILE_FOREGROUND_EVEN_MARKED 		= "detail file foreground, even, marked";           
	private static final String DETAILS_FILE_FOREGROUND_EVEN 				= "detail file foreground, even, not marked";       
	private static final String DETAILS_FILE_FOREGROUND_MARKED 				= "detail file foreground, odd, marked";            
	private static final String DETAILS_FILE_FOREGROUND 					= "detail file foreground, odd, not marked";        
	
	
	
	
	
	private ColorSelector fillRestHashMap()
	{
		if (!colors.containsKey(SIMPLE_FILE_BACKGROUND						)) colors.put(SIMPLE_FILE_BACKGROUND								, new SerializableColor(Color.green  ));
		if (!colors.containsKey(SHADOW										)) colors.put(SHADOW												, new SerializableColor(Color.black  ));
		if (!colors.containsKey(DETAILS_COLUMN_DRAG 						)) colors.put(DETAILS_COLUMN_DRAG 									, new SerializableColor(Color.cyan   ));
		if (!colors.containsKey(DETAILS_LIST_HEADER_DRAG 					)) colors.put(DETAILS_LIST_HEADER_DRAG 								, new SerializableColor(Color.cyan   ));
		if (!colors.containsKey(DETAILS_LIST_BACKGROUND 					)) colors.put(DETAILS_LIST_BACKGROUND 								, new SerializableColor(Color.black  ));
		if (!colors.containsKey(DETAILS_COLUMN_DIVIDER 						)) colors.put(DETAILS_COLUMN_DIVIDER 								, new SerializableColor(Color.black  ));
		if (!colors.containsKey(DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED	)) colors.put(DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED			, new SerializableColor(Color.black  ));
		if (!colors.containsKey(DETAILS_LIST_HEADER_FOREGROUND_HIGHLIGHTED 	)) colors.put(DETAILS_LIST_HEADER_FOREGROUND_HIGHLIGHTED 			, new SerializableColor(Color.WHITE  ));
		if (!colors.containsKey(DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED )) colors.put(DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED 			, new SerializableColor(Color.white  ));
		if (!colors.containsKey(DETAILS_LIST_HEADER_BACKGROUND_HIGHLIGHTED	)) colors.put(DETAILS_LIST_HEADER_BACKGROUND_HIGHLIGHTED			, new SerializableColor(Color.blue   ));
		if (!colors.containsKey(DETAILS_FILE_DIVIDER 						)) colors.put(DETAILS_FILE_DIVIDER 									, new SerializableColor(Color.white  ));

		if (!colors.containsKey(DETAILS_FILE_BACKGROUND_HIGHLIGHTED_MARKED  ))  colors.put(DETAILS_FILE_BACKGROUND_HIGHLIGHTED_MARKED           , new SerializableColor(COOL_ORANGE  ));
		if (!colors.containsKey(DETAILS_FILE_BACKGROUND_HIGHLIGHTED         ))  colors.put(DETAILS_FILE_BACKGROUND_HIGHLIGHTED                  , new SerializableColor(OTHER_ORANGE ));
		if (!colors.containsKey(DETAILS_FILE_BACKGROUND_EVEN_MARKED         ))  colors.put(DETAILS_FILE_BACKGROUND_EVEN_MARKED                  , new SerializableColor(LIGHT_GREEN  ));
		if (!colors.containsKey(DETAILS_FILE_BACKGROUND_EVEN                ))  colors.put(DETAILS_FILE_BACKGROUND_EVEN                         , new SerializableColor(Color.yellow ));
		if (!colors.containsKey(DETAILS_FILE_BACKGROUND_MARKED              ))  colors.put(DETAILS_FILE_BACKGROUND_MARKED                       , new SerializableColor(DARK_GREEN   ));
		if (!colors.containsKey(DETAILS_FILE_BACKGROUND                     ))  colors.put(DETAILS_FILE_BACKGROUND                              , new SerializableColor(Color.WHITE  ));
		if (!colors.containsKey(DETAILS_FILE_FOREGROUND_HIGHLIGHTED_MARKED  ))  colors.put(DETAILS_FILE_FOREGROUND_HIGHLIGHTED_MARKED           , new SerializableColor(Color.WHITE  ));
		if (!colors.containsKey(DETAILS_FILE_FOREGROUND_HIGHLIGHTED         ))  colors.put(DETAILS_FILE_FOREGROUND_HIGHLIGHTED                  , new SerializableColor(Color.WHITE  ));
		if (!colors.containsKey(DETAILS_FILE_FOREGROUND_EVEN_MARKED         ))  colors.put(DETAILS_FILE_FOREGROUND_EVEN_MARKED                  , new SerializableColor(Color.BLACK  ));
		if (!colors.containsKey(DETAILS_FILE_FOREGROUND_EVEN                ))  colors.put(DETAILS_FILE_FOREGROUND_EVEN                         , new SerializableColor(Color.BLACK  ));
		if (!colors.containsKey(DETAILS_FILE_FOREGROUND_MARKED              ))  colors.put(DETAILS_FILE_FOREGROUND_MARKED                       , new SerializableColor(Color.BLACK  ));
		if (!colors.containsKey(DETAILS_FILE_FOREGROUND                     ))  colors.put(DETAILS_FILE_FOREGROUND                              , new SerializableColor(Color.BLACK  ));
		
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	static class SerializableColor
	{
		private int red;
		private int green;
		private int blue;
		private int alpha;
		
		@JsonIgnore
		private Color cache;
		
		private SerializableColor() {}
		
		private SerializableColor(Color color)
		{
			this.red = color.getRed();
			this.green = color.getGreen();
			this.blue = color.getBlue();
			this.alpha = color.getAlpha();
		}
		
		public Color getColor()
		{
			if (cache != null) return cache;
			return cache = new Color(red, green, blue, alpha);
		}
	}
	
}
