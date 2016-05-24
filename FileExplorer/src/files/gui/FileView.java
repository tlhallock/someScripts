package files.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import files.app.Application;
import files.app.ExtensionManager.FileType;
import files.app.Logger.LogLevel;
import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeValue;

public abstract class FileView extends JPanel
{
	protected FileEntry entry;
	protected Columns columns;
	
	protected boolean highlighted;
	protected boolean marked;
	protected boolean filtered;

	private FileInteractionIF interaction;

	public FileView(Columns columns, FileEntry entry, FileInteractionIF intera)
	{
		this.entry = entry;
		this.columns = columns;
		this.interaction = intera;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() != 2)
					return;
				interaction.doubleClick(entry);
			}
		});
		
		JPanel p = this;
		JPopupMenu popup = new JPopupMenu();
		JMenuItem item = new JMenuItem("Set File type options...");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RootViewExtensions launchFileTypeOptions = Application.getApplication().launchFileTypeOptions(p.getLocationOnScreen());
				String extension = entry.getExtension();
				if (extension == null) return;
				FileType type = Application.getApplication().getKnownFileTypes().getFileTypeByExtension(extension);
				if (type == null)
				{
					type = Application.getApplication().getKnownFileTypes().createNewFileType();
					Application.getApplication().getLogger().log(LogLevel.Normal, Application.getApplication().getKnownFileTypes().addIfCompatible(type));
					Application.getApplication().getKnownFileTypes().setIfCompatible(type, Collections.singleton(extension));
				}
				launchFileTypeOptions.select(type);
			}
		});
		popup.add(item);
		setComponentPopupMenu(popup);
	}
	


	public FileEntryAttributeValue get(int column) {
		return entry.get(columns.get(column).key);
	}
	void setInteraction(FileInteractionIF interaction)
	{
		this.interaction = interaction;
	}
	void setMarked(boolean sel)
	{
		this.marked = sel;
	}
	void setHighlighted(boolean sel)
	{
		this.highlighted = sel;
	}

	FileEntry getEntry()
	{
		return entry;
	}

	void setFiltered(boolean b)
	{
		filtered = b;
	}
	boolean isFiltered()
	{
		return filtered;
	}

}
