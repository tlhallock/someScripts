/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.gui;

import files.app.Application;
import files.model.FileEntry;
import files.model.FileEntryAttributes;
import files.model.FileEntryAttributes.FileEntryAttributeKey;

/**
 *
 * @author thallock
 */
public class FolderExplorers
{
    
    
    public static RootViewFolderView createTreeView(FileEntry entry)
    {
        return createTreeView(entry, Application.getApplication().getSettings().getDefaultTreeColumns());
    }
    
    public static RootViewFolderView createColumnsView(FileEntry entry)
    {
        return createColumnsView(entry, Application.getApplication().getSettings().getDefaultColumnColumns());
    }
    
    public static RootViewFolderView createGridView(FileEntry entry)
    {
        return createGridView(entry, Application.getApplication().getSettings().getDefaultGridColumns());
    }
    
    public static RootViewFolderView createDetailsView(FileEntry entry)
    {
        return createDetailsView(entry, Application.getApplication().getSettings().getDefaultDetailsColumns());
    }
    
    
    
    
    
    public static RootViewFolderView createTreeView(FileEntry entry, FileEntryAttributeKey[] columns)
    {
        RootViewFolderView root = new RootViewFolderView();
        FolderViewListTree view = new FolderViewListTree(columns);
        root.setFolderView(view);
        root.show(entry);
        return root;
    }
    
    public static RootViewFolderView createColumnsView(FileEntry entry, FileEntryAttributeKey[] columns)
    {
        RootViewFolderView root = new RootViewFolderView();
        FolderViewColumns view = new FolderViewColumns(columns);
        root.setFolderView(view);
        root.show(entry);
        return root;
    }
    
    public static RootViewFolderView createGridView(FileEntry entry, FileEntryAttributeKey[] columns)
    {
        RootViewFolderView root = new RootViewFolderView();
        FolderViewListGrid view = new FolderViewListGrid(columns);
        root.setFolderView(view);
        root.show(entry);
        return root;
    }
    
    public static RootViewFolderView createDetailsView(FileEntry entry, FileEntryAttributeKey[] columns)
    {
        RootViewFolderView root = new RootViewFolderView();
        FolderViewListDetails view = new FolderViewListDetails(columns);
        root.setFolderView(view);
        root.show(entry);
        return root;
    }
}
