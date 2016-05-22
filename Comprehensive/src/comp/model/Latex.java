package comp.model;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Latex extends Content
{
	String text = "";
	LinkedList<String> dependencies = new LinkedList<>();
	LinkedList<String> references = new LinkedList<>();
	LinkedList<String> exports = new LinkedList<>();
	
	BufferedImage imageCache;
	
	public Latex(String name, String text)
	{
		super(name);
		this.text = text;
	}

	
	void parseAtts()
	{
		Matcher matcher = REFERENCE_PATTERN.matcher(text);
		while (matcher.matches())
		{
			dependencies.add(matcher.group(0));
		}

		matcher = EXPORTS_PATTERN.matcher(text);
		while (matcher.matches())
		{
			exports.add(matcher.group(0));
		}
	}
	

	
	void write(PrintStream out)
	{
		out.print("\\documentclass{article}\n");
		for (String dependency : dependencies)
		{
			out.print("\\usepackage{");
			out.print(dependency);
			out.print("}\n");
		}
		out.print("\\begin{document}\n");
		
		out.print(text);
		
		out.print("\\end{document}\n");
	}
	
	private static Pattern REFERENCE_PATTERN = Pattern.compile("\\ref\\{([^}]*)\\}");
	private static Pattern EXPORTS_PATTERN = Pattern.compile("\\tag\\{[^}]*\\}");

	@Override
	public void writeTo(Canvas panel) {
		
		if (imageCache == null)
		{
			imageCache = Compiler.compile(this);
		}
		panel.setImage(imageCache);
	}
}
