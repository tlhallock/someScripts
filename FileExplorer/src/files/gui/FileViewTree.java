package files.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;

public class FileViewTree extends FileViewHorizontal
{
	private static final int SHOULD_BE_DYNAMIC_DEPTH = 50;
	private static final int WIDTH = 2;

	private ArrayList<Boolean> markers;
	
	public FileViewTree(Columns columns, FileEntry entry, FileInteractionIF intera, FileFilterer filterer)
	{
		super(columns, entry, intera, filterer);
	}
	
	void setDepth(ArrayList<Boolean> markers)
	{
		this.markers = markers;
	}
	
	protected int getStart()
	{
		if (markers == null) return 0;
		return markers.size() * SHOULD_BE_DYNAMIC_DEPTH;
	}
	
	public void paint(Graphics graphics)
	{
		super.paint(graphics);
		
		if (markers == null || markers.isEmpty())
			return;
		
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.black);
		
		for (int i=0;i<markers.size()-1;i++)
			if (markers.get(i))
				g.fillRect((i) * SHOULD_BE_DYNAMIC_DEPTH + SHOULD_BE_DYNAMIC_DEPTH / 2 - WIDTH / 2, 0, WIDTH, getHeight());
		

		g.fillRect(markers.size() * SHOULD_BE_DYNAMIC_DEPTH - SHOULD_BE_DYNAMIC_DEPTH / 2 - WIDTH / 2, 0, WIDTH, markers.get(markers.size()-1) ? getHeight() : (getHeight() / 2));
		g.fillRect(markers.size() * SHOULD_BE_DYNAMIC_DEPTH - SHOULD_BE_DYNAMIC_DEPTH / 2, getHeight() / 2 - WIDTH / 2, SHOULD_BE_DYNAMIC_DEPTH / 2 / 2, WIDTH);
	}
}
