package files.gui;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import files.app.Serialization;
import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;

public class ListViewTree extends ListViewHorizontal<FileViewTree>
{
	private TreeStructure root = new TreeStructure(null);
	private HashMap<String, TreeStructure> structureByPath = new HashMap<>();

	public ListViewTree()
	{
            this(new FileEntryAttributeKey[] {FileEntryAttributeKey.All_Name, FileEntryAttributeKey.File_Length});
	}
        
	public ListViewTree(FileEntryAttributeKey[] attributes)
	{
		super(attributes);
		setInteraction(new ListViewInteraction());
	}

	@Override
	protected boolean getShowNameFirst()
	{
		return true;
	}

	@Override
	protected boolean getFixedColumns()
	{
		return false;
	}

	@Override
	protected void sortColumns(int column, boolean reverse)
	{

	}

	protected FileViewTree createFileView(Columns columns, FileEntry entry, FileInteractionIF interaction)
	{
		FileViewTree fileViewHorizontal = new FileViewTree(columns, entry, interaction);
		TreeStructure structure = new TreeStructure(fileViewHorizontal);
		
		// Bad stuff happens at /
		Path path = fileViewHorizontal.getEntry().getPath();
		structureByPath.put(path.toString(), structure);
		structureByPath.get(path.getParent().toString()).children.add(structure);
		
		return fileViewHorizontal;
	}

	public void show(Path p)
	{
		structureByPath.clear();
		root = new TreeStructure(null);
		structureByPath.put(p.toString(), root);
		
		super.show(p);
	}

	class TreeStructure
	{
		@JsonIgnore
		FileViewTree view;
		int index;
		String forJson;
		boolean isExpanded;
		LinkedList<TreeStructure> children = new LinkedList<>();
		
		TreeStructure(FileViewTree entry)
		{
			this.view = entry;
			forJson = view == null ? "root" : view.getEntry().getName();
		}
		
		int setOrder(int currentIndex, int depth)
		{
			if (view != null /* && !view.isFiltered() */)
			{
				view.setDepth(depth);
				index = currentIndex++;
			}
			for (TreeStructure child : children)
			{
				currentIndex = child.setOrder(currentIndex, depth+1);
				child.view.setLast(false);
			}
			
			if (!children.isEmpty())
				children.getLast().view.setLast(true);
			
			return currentIndex;
		}
		
		public String toString()
		{
			return Serialization.writer.toString(this);
		}
	}
	
	class ListViewInteraction extends FileInteractionIF.FileOpenInteraction
	{
		@Override
		public void doubleClickDirectory(FileEntry clicked)
		{
			System.out.println(clicked.getName());
			TreeStructure treeStructure = structureByPath.get(clicked.getPath().toString());
			if (treeStructure.isExpanded)
			{
				synchronized (details)
				{
					for (TreeStructure structure : treeStructure.children)
					{
						details.remove(structure.view);
						structureByPath.remove(structure.view.getEntry().getPath().toString());
					}
					treeStructure.children.clear();
				}
				treeStructure.isExpanded = false;
			}
			else
			{
				show(clicked.getPath(), false);
				root.setOrder(0, -1);
				details.sort(new Comparator<FileViewHorizontal>()
				{
					@Override
					public int compare(FileViewHorizontal o1, FileViewHorizontal o2)
					{
						return Integer.compare(
								structureByPath.get(o1.getEntry().getPath().toString()).index,
								structureByPath.get(o2.getEntry().getPath().toString()).index);
					}
				});
				treeStructure.isExpanded = true;
			}
			adjustSizes();
		}
	}
}
