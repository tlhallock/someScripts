package files.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;

public class FileViewTree extends FileViewHorizontal
{
	private static final int SHOULD_BE_DYNAMIC_DEPTH = 50;
	private static final int WIDTH = 2;

	private int depth;
	private boolean last;
	
	public FileViewTree(Columns columns, FileEntry entry, FileInteractionIF intera)
	{
		super(columns, entry, intera);
	}
	
	void setLast(boolean last)
	{
		this.last = last;
	}
	
	void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	protected int getStart()
	{
		return depth * SHOULD_BE_DYNAMIC_DEPTH;
	}
	
	public void paint(Graphics graphics)
	{
		super.paint(graphics);
		
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.black);
		
		for (int i=0;i<depth;i++)
			g.fillRect(i * SHOULD_BE_DYNAMIC_DEPTH + SHOULD_BE_DYNAMIC_DEPTH / 2 - WIDTH / 2, 0, WIDTH, (last && i == depth-1) ? getHeight() / 2 + WIDTH / 2 : getHeight());

		g.fillRect(depth * SHOULD_BE_DYNAMIC_DEPTH - SHOULD_BE_DYNAMIC_DEPTH / 2, getHeight() / 2 - WIDTH / 2, SHOULD_BE_DYNAMIC_DEPTH / 2 / 2, WIDTH);
		
	}
}
