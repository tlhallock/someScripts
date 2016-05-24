package files.app;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import files.app.Logger.LogLevel;

public final class ColorSelector
{
	private ColorSelector() {}

	// Still leaving these as non static in case I want to test...
	public Color getFileBackgroundColor(boolean even, boolean marked, boolean highlighted)
	{
		if (highlighted)
		{
			if (marked)
			{
				return (DETAILS_FILE_BACKGROUND_HIGHLIGHTED_MARKED).getColor();
			}
			else
			{
				return (DETAILS_FILE_BACKGROUND_HIGHLIGHTED).getColor();
			}
		}
		if (even)
		{
			if (marked)
			{
				return (DETAILS_FILE_BACKGROUND_EVEN_MARKED).getColor();
			}
			else
			{
				return (DETAILS_FILE_BACKGROUND_EVEN).getColor();
			}
		}
		else
		{
			if (marked)
			{
				return (DETAILS_FILE_BACKGROUND_MARKED).getColor();
			}
			else
			{
				return (DETAILS_FILE_BACKGROUND).getColor();
			}
		}
	}
	public Color getFileForegroundColor(boolean even, boolean marked, boolean highlighted)
	{
		if (highlighted)
		{
			if (marked)
			{
				return (DETAILS_FILE_FOREGROUND_HIGHLIGHTED_MARKED).getColor();
			}
			else
			{
				return (DETAILS_FILE_FOREGROUND_HIGHLIGHTED).getColor();
			}
		}
		if (even)
		{
			if (marked)
			{
				return (DETAILS_FILE_FOREGROUND_EVEN_MARKED).getColor();
			}
			else
			{
				return (DETAILS_FILE_FOREGROUND_EVEN).getColor();
			}
		}
		else
		{
			if (marked)
			{
				return (DETAILS_FILE_FOREGROUND_MARKED).getColor();
			}
			else
			{
				return (DETAILS_FILE_FOREGROUND).getColor();
			}
		}
	}
	public Color getColumnHeaderBackgroundColor(boolean folderHighlighted)
	{
		return (folderHighlighted ? DETAILS_LIST_HEADER_BACKGROUND_HIGHLIGHTED : DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED).getColor();
	}
	public Color getColumnHeaderForegroundColor(boolean highlighted)
	{
		return (highlighted ? DETAILS_LIST_HEADER_FOREGROUND_HIGHLIGHTED : DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED).getColor();
	}
	
	public Color getColumnDividerColor()
	{
		return (DETAILS_COLUMN_DIVIDER).getColor();
	}
	public Color getListBackground() {
		return (DETAILS_LIST_BACKGROUND).getColor();
	}
	public Color getFileDividerColor(boolean even, boolean marked, boolean highlighted) {
		return (DETAILS_FILE_DIVIDER).getColor();
	}
	public Color getColumnDragColor() {
		return (DETAILS_COLUMN_DRAG).getColor();
	}
	public Color getColumnHeaderDragColor() {
		return (DETAILS_LIST_HEADER_DRAG).getColor();
	}
	public Color getNotShadowBorderColor(Color original)
	{
		return new Color((255+original.getRed()) / 2, (255+original.getGreen()) / 2, (255+original.getBlue()) / 2);
//		return Color.white;
	}
	public Color getShadowBorderColor(Color original)
	{
		return (SHADOW).getColor();
	}
	public Color getSimpleViewBackground() {
		return (SIMPLE_FILE_BACKGROUND).getColor();
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static void loadHumanReadable(Path path) throws IOException
	{
		ColorPropertyList list = Serialization.writer.read(path.toFile(), ColorPropertyList.class);
		HashMap<String,SerializableColor> map = list.toMap();
		for (SerializableColorProperty p : ALL_COLORS)
			p.set(map.get(p.name));
	}

	private static void writeHumanReadable(Path path) throws IOException
	{
		for (SerializableColorProperty p : ALL_COLORS)
			p.setIfNotWritten();
		ColorPropertyList list = new ColorPropertyList();
		for (SerializableColorProperty p : ALL_COLORS)
			list.list.add(p);
		// Maybe sort them?
		Serialization.writer.write(path.toFile(), list);
	}
	public static void save()
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
			loadHumanReadable(path);
		} catch (IOException e) {
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to read colors file");
			e.printStackTrace();
			try {
				save();
				loadHumanReadable(path);
			} catch (IOException e2) {
				Application.getApplication().getLogger().log(LogLevel.Minimal, "Unable to read colors file", e2);
				return null;
			}
		}
		return new ColorSelector();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	private static class SerializableColor
	{
		private int red;
		private int green;
		private int blue;
		private int alpha;
		
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
			return new Color(red, green, blue, alpha);
		}
	}
	private static final class SerializableColorProperty implements Comparable<SerializableColorProperty>
	{
		private String name;
		private SerializableColor color;
		
		@JsonIgnore
		private SerializableColor defaultColor;
		@JsonIgnore
		private Color cachedColor;
		
		// Used by json
		public SerializableColorProperty() {}
		
		public SerializableColorProperty(String name, SerializableColor defaultColor)
		{
			this.name = name;
			this.defaultColor = defaultColor;
		}
		
		private void set(SerializableColor color)
		{
			if (color == null) return;
			this.color = color;
		}
		
		private void setIfNotWritten()
		{
			if (color == null)
				color = defaultColor;
		}
		
		public Color getColor()
		{
			if (cachedColor != null)
				return cachedColor;
			
			setIfNotWritten();
			return cachedColor = color.getColor();
		}

		@Override
		public int compareTo(SerializableColorProperty o)
		{
			return name.compareTo(o.name);
		}
		public boolean equals(Object other)
		{
			if (!(other instanceof SerializableColorProperty))
				return false;
			return ((SerializableColorProperty) other).name.equals(name);
		}
	}
	private static class ColorPropertyList
	{
		TreeSet<SerializableColorProperty> list = new TreeSet<>();
		
		HashMap<String, SerializableColor> toMap()
		{
			HashMap<String, SerializableColor> returnValue = new HashMap<>();
			for (SerializableColorProperty p : list)
				returnValue.put(p.name, p.color);
			return returnValue;
		}
	}
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static final Color LIGHT_GREEN	= new Color(153, 255, 153);
	private static final Color DARK_GREEN	= new Color( 51, 204,  51);
	private static final Color COOL_ORANGE	= new Color(255, 153,  51);
	private static final Color OTHER_ORANGE	= new Color(255, 204,   0);

	private static final SerializableColorProperty SIMPLE_FILE_BACKGROUND 				= new SerializableColorProperty("simple view background"	                  , new SerializableColor(Color.green )    );
	private static final SerializableColorProperty SHADOW 						= new SerializableColorProperty("shadow"                                          , new SerializableColor(Color.black )    );
	private static final SerializableColorProperty DETAILS_COLUMN_DRAG 				= new SerializableColorProperty("column drag"                                     , new SerializableColor(Color.cyan  )    );
	private static final SerializableColorProperty DETAILS_LIST_BACKGROUND				= new SerializableColorProperty("list background (is it used?)"                   , new SerializableColor(Color.cyan  )    );
	private static final SerializableColorProperty DETAILS_COLUMN_DIVIDER 				= new SerializableColorProperty("column divider"                                  , new SerializableColor(Color.black )    );
	private static final SerializableColorProperty DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED 	= new SerializableColorProperty("DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED"     , new SerializableColor(Color.black )    );
	private static final SerializableColorProperty DETAILS_LIST_HEADER_FOREGROUND_HIGHLIGHTED 	= new SerializableColorProperty("DETAILS_LIST_HEADER_FOREGROUND__HIGHLIGHTED"     , new SerializableColor(Color.black )    );
	private static final SerializableColorProperty DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED 	= new SerializableColorProperty("DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED"     , new SerializableColor(Color.WHITE )    );
	private static final SerializableColorProperty DETAILS_LIST_HEADER_BACKGROUND_HIGHLIGHTED 	= new SerializableColorProperty("DETAILS_LIST_HEADER_BACKGROUND__HIGHLIGHTED"     , new SerializableColor(Color.white )    );
	private static final SerializableColorProperty DETAILS_LIST_HEADER_DRAG 			= new SerializableColorProperty("header drag"                                     , new SerializableColor(Color.blue  )    );
	private static final SerializableColorProperty DETAILS_FILE_DIVIDER 				= new SerializableColorProperty("file divider"                                    , new SerializableColor(Color.white )    );
	private static final SerializableColorProperty DETAILS_FILE_BACKGROUND_HIGHLIGHTED_MARKED 	= new SerializableColorProperty("details file background, highlighted, marked"    , new SerializableColor(COOL_ORANGE )    );
	private static final SerializableColorProperty DETAILS_FILE_BACKGROUND_HIGHLIGHTED 		= new SerializableColorProperty("details file background, highlighted, not marked", new SerializableColor(OTHER_ORANGE)    );
	private static final SerializableColorProperty DETAILS_FILE_BACKGROUND_EVEN_MARKED 		= new SerializableColorProperty("details file background, even, marked"           , new SerializableColor(LIGHT_GREEN )    );
	private static final SerializableColorProperty DETAILS_FILE_BACKGROUND_EVEN 			= new SerializableColorProperty("details file background, even, not marked"       , new SerializableColor(Color.yellow)    );
	private static final SerializableColorProperty DETAILS_FILE_BACKGROUND_MARKED 			= new SerializableColorProperty("details file background, odd, marked"            , new SerializableColor(DARK_GREEN  )    );
	private static final SerializableColorProperty DETAILS_FILE_BACKGROUND 				= new SerializableColorProperty("details file background, odd, not marked"        , new SerializableColor(Color.WHITE )    );
	private static final SerializableColorProperty DETAILS_FILE_FOREGROUND_HIGHLIGHTED_MARKED  	= new SerializableColorProperty("details file background, highlighted, marked"    , new SerializableColor(Color.WHITE )    );
	private static final SerializableColorProperty DETAILS_FILE_FOREGROUND_HIGHLIGHTED		= new SerializableColorProperty("details file foreground, highlighted, not marked", new SerializableColor(Color.WHITE )    );
	private static final SerializableColorProperty DETAILS_FILE_FOREGROUND_EVEN_MARKED 		= new SerializableColorProperty("details file foreground, even, marked"           , new SerializableColor(Color.BLACK )    );
	private static final SerializableColorProperty DETAILS_FILE_FOREGROUND_EVEN 			= new SerializableColorProperty("details file foreground, even, not marked"       , new SerializableColor(Color.BLACK )    );
	private static final SerializableColorProperty DETAILS_FILE_FOREGROUND_MARKED 			= new SerializableColorProperty("details file foreground, odd, marked"            , new SerializableColor(Color.BLACK )    );
	private static final SerializableColorProperty DETAILS_FILE_FOREGROUND 				= new SerializableColorProperty("details file foreground, odd, not marked"        , new SerializableColor(Color.BLACK )    );
	
	
	private static final SerializableColorProperty[] ALL_COLORS = new SerializableColorProperty[]
	{
		 SIMPLE_FILE_BACKGROUND 			 ,
		 SHADOW 					 ,
		 DETAILS_COLUMN_DRAG 				 ,
		 DETAILS_LIST_BACKGROUND			 ,
		 DETAILS_COLUMN_DIVIDER 			 ,
		 DETAILS_LIST_HEADER_FOREGROUND_NHIGHLIGHTED 	 ,
		 DETAILS_LIST_HEADER_FOREGROUND_HIGHLIGHTED 	 ,
		 DETAILS_LIST_HEADER_BACKGROUND_NHIGHLIGHTED 	 ,
		 DETAILS_LIST_HEADER_BACKGROUND_HIGHLIGHTED 	 ,
		 DETAILS_LIST_HEADER_DRAG 			 ,
		 DETAILS_FILE_DIVIDER 			         ,
		 DETAILS_FILE_BACKGROUND_HIGHLIGHTED_MARKED 	 ,
		 DETAILS_FILE_BACKGROUND_HIGHLIGHTED 		 ,
		 DETAILS_FILE_BACKGROUND_EVEN_MARKED 		 ,
		 DETAILS_FILE_BACKGROUND_EVEN 		         ,
		 DETAILS_FILE_BACKGROUND_MARKED 		 ,
		 DETAILS_FILE_BACKGROUND 			 ,
		 DETAILS_FILE_FOREGROUND_HIGHLIGHTED_MARKED  	 ,
		 DETAILS_FILE_FOREGROUND_HIGHLIGHTED		 ,
		 DETAILS_FILE_FOREGROUND_EVEN_MARKED 		 ,
		 DETAILS_FILE_FOREGROUND_EVEN 		         ,
		 DETAILS_FILE_FOREGROUND_MARKED 		 ,
		 DETAILS_FILE_FOREGROUND 			 ,
	};
	
}
