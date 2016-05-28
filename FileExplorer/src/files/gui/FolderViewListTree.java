package files.gui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import files.app.Serialization;
import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import files.model.FileEntryAttributes.FileEntryAttributeValue;

public class FolderViewListTree extends FolderViewListHorizontal<FileViewTree>
{
	private TreeStructure root = new TreeStructure(null);
	private HashMap<String, TreeStructure> structureByPath = new HashMap<>();
	private FileEntry rootDirectory;

	private Comparator<TreeStructure> comparator;

	FolderViewListTree(FileEntryAttributeKey[] attributes)
	{
		super(attributes);
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
		comparator = new Comparator<TreeStructure>(){
		@Override
		public int compare(TreeStructure o1, TreeStructure o2) {
			FileEntryAttributeValue display1 = o1.view.get(column);
			FileEntryAttributeValue display2 = o2.view.get(column);
			int cmp = 0;
			if (display1 == null)
				cmp = display2 == null ? 0 : 1;
			else if (display2 == null)
				cmp = -1;
			else
				cmp = display1.compareTo(display2);
			return (reverse ? -1 : 1) * cmp;
		}};
		root.setOrder(0, new ArrayList<Boolean>());
		synchronized (details)
		{
			orderDetails();
		}
	}

	protected FileViewTree createFileView(Columns columns, FileEntry entry, FileInteractionIF interaction, FileFilterer filterer)
	{
		FileViewTree fileViewHorizontal = new FileViewTree(columns, entry, interaction, filterer);
		TreeStructure structure = new TreeStructure(fileViewHorizontal);
		
		// Bad stuff happens at "/"
		Path path = fileViewHorizontal.getEntry().getPath();
		structureByPath.put(path.toString(), structure);
		TreeStructure parentStructure = structureByPath.get(FileEntry.getParentPath(path));
		if (parentStructure == null)
			if (rootDirectory.isParentOf(entry))
				root.children.add(structure);
			else
				return null;
		else
		{
			parentStructure.children.add(structure);
			if (parentStructure.view != null && parentStructure.view.getEntry().equals(entry))
			{
				throw new RuntimeException("This better not be happening...");
			}
		}
		
		return fileViewHorizontal;
	}
        
    
        
        
        
        
        
	public void showExistingDirectory(FileEntry p)
	{
		setHighlight(p);
		TreeStructure treeStructure = structureByPath.get(p.getPath().toString());

		if (treeStructure.isExpanded)
		{
			synchronized (details)
			{
				treeStructure.children.stream().forEach(x -> x.remove());
				treeStructure.children.clear();
			}
			treeStructure.isExpanded = false;
		}
		else
		{
			show(p, false);
			root.setOrder(0, new ArrayList<Boolean>());
			orderDetails();
			treeStructure.isExpanded = true;
		}
		adjustSizes();
	}

	@Override
	public void showRootDirectory(FileEntry entry)
	{
		rootDirectory = entry;
		structureByPath.clear();
		root = new TreeStructure(null);
		structureByPath.put(entry.getPath().toString(), root);
		super.showRootDirectory(entry);
	}

	@Override
	public void goUpToDirectory(FileEntry entry)
	{
		// TODO: We could do better than this...
		showRootDirectory(entry);
	}
    
    
    
    
	

	private void orderDetails()
	{
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
	}
    
    

	private class TreeStructure
	{
		@JsonIgnore
		FileViewTree view;
		int index;
		String forJson;
		boolean isExpanded;
		ArrayList<TreeStructure> children = new ArrayList<>();
		
		TreeStructure(FileViewTree view)
		{
			this.view = view;
			forJson = view == null ? "root" : view.getEntry().getName();
		}
		
		public void remove()
		{
			details.remove(view);
			structureByPath.remove(view.getEntry().getPath().toString());
			children.stream().forEach(x -> x.remove());
			children.clear();
		}

		int setOrder(int currentIndex, ArrayList<Boolean> markers)
		{
			if (comparator != null)
				Collections.sort(children, comparator);
			
			if (view != null /* && !view.isFiltered() */)
			{
				view.setDepth(markers);
				index = currentIndex++;
			}
			
			int index = 0;
			for (TreeStructure child : children)
			{
				ArrayList<Boolean> clone = (ArrayList<Boolean>) markers.clone();
				if (view != null) clone.add(!isLast(index++));
				currentIndex = child.setOrder(currentIndex, clone);
			}
			
			return currentIndex;
		}
		
		private boolean isLast(int index)
		{
			for (int i = index+1;i<children.size();i++)
			{
				if (!children.get(i).view.isFiltered())
					return false;
			}
			return true;
		}

		public String toString()
		{
			return Serialization.writer.toString(this);
		}
	}
}
