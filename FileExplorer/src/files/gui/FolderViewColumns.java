/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import files.app.Application;
import files.app.Logger.LogLevel;
import files.model.FileEntry;
import files.model.FileEntryAttributes;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import javax.swing.JComponent;

/**
 *
 * @author thallock
 */
public class FolderViewColumns extends javax.swing.JPanel implements FolderView {

    private static final int NEW_FOLDER_SIZE = 500;
	
    private FileEntry current;
    private MultipleSplitPane<FolderViewList> splitPane;
    private ArrayList<FileEntry> entries = new ArrayList<>();
    private boolean useHorizontal = true;
    private FileInteractionIF interaction;
    private FileEntryAttributes.FileEntryAttributeKey[] defaultAttributes;
    
    /**
     * Creates new form ColumnView
     */
    FolderViewColumns(FileEntryAttributes.FileEntryAttributeKey[] defaultAttributes) {
        this.defaultAttributes = defaultAttributes;
        initComponents();

        splitPane = (MultipleSplitPane) jPanel1;
        jScrollPane1.addComponentListener(splitPane);
    }

    @Override
    public JComponent getView()
    {
        return this;
    }

    @Override
    public FileEntry getCurrentDirectoy()
    {
        return current;
    }
    
    @Override
    public void showRootDirectory(FileEntry entry) {
        // Try to show existing anyway (idk about this, might be bad or need refactoring...)
        showExistingDirectory(entry);
    }
    
    private void setRoot(FileEntry entry) {
        synchronized (entries) {
            splitPane.removeAllChildren();
            entries.clear();

            addView(entry);
            scrollToView(0);
        }
    }

    public void showExistingDirectory(FileEntry entry) {
        synchronized (entries)
        {
            if (!entries.isEmpty() && entries.get(0).equals(entry))
            {
                scrollToView(0);
                return;
            }
            for (int i = 0; i < entries.size(); i++)
            {
                if (!entries.get(i).isParentOf(entry))
                {
                    continue;
                }
                
                splitPane.getChild(i).setHighlight(entry);
                if (i == entries.size() - 1) {
                    addView(entry);
                    scrollToView(i + 1);
                    return;
                }
                
                if (entries.get(i+1).equals(entry))
                {
                    scrollToView(i+1);
                    return;
                }

                splitPane.removeChildrenAfter(i+1);
                while (entries.size() > i+1) {
                    entries.remove(entries.size() - 1);
                }
                entries.add(i+1, entry);
                splitPane.getChild(i+1).showRootDirectory(entry);
                scrollToView(i+1);
                return;
            }

            // Not found...
            setRoot(entry);
        }
    }
    
    public void goUpToDirectory(FileEntry entry)
    {
        FileEntry fileEntry = entries.get(0);
        while (!fileEntry.equals(entry)) {
            Path parent = fileEntry.getPath().getParent();

            if (parent == null || parent.equals(fileEntry.getPath())) {
                return;
            }
            FileEntry parentEntry;
            try {
                entries.add(0, parentEntry = FileEntry.load(parent));
            } catch (IOException e) {
                Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to load parent.", e);
                return;
            }

            FolderViewList view = createListView();
            view.showRootDirectory(parentEntry);
            view.setHighlight(fileEntry);
            splitPane.prependChild(view);

            for (int i = 0; i < entries.size(); i++)
            {
                splitPane.getChild(i).setInteraction(interaction);
            }
            fileEntry = entries.get(0);
        }

        scrollToView(0);
    }
    
    public void setInteraction(FileInteractionIF interaction)
    {
        this.interaction = interaction;
        for (int i=0;i<splitPane.getNumberOfChildren();i++)
        {
            splitPane.getChild(i).setInteraction(interaction);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    

    private FolderViewList createListView() {
        return useHorizontal ? new FolderViewListDetails(defaultAttributes) : new FolderViewListGrid(defaultAttributes);
    }

    private void addView(FileEntry entry) {
        FolderViewList view = createListView();
        view.showRootDirectory(entry);
        view.setInteraction(interaction);
        entries.add(entries.size(), entry);
        splitPane.postpendChild(view);
    }

    private void setFolderHighlight(int index) {

        for (int i = 0; i < splitPane.getNumberOfChildren(); i++) {
            ((FolderViewList) splitPane.getChild(i)).setFolderHighlighted(i == index);
        }
        current = ((FolderViewList) splitPane.getChild(index)).getCurrentDirectoy();
    }

    private void scrollToView(int index) {
        setFolderHighlight(index);
        int childEnd = splitPane.getChildEnd(index);
        int childBegin = splitPane.getChildBegin(index);
        Rectangle viewRect = jScrollPane1.getViewport().getViewRect();
        if (viewRect.x + viewRect.width < childEnd) {
            jScrollPane1.getViewport().setViewPosition(new Point(childEnd, 0));
        }
        viewRect = jScrollPane1.getViewport().getViewRect();
        if (viewRect.x > childBegin) {
            jScrollPane1.getViewport().setViewPosition(new Point(childBegin, 0));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new MultipleSplitPane(NEW_FOLDER_SIZE);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1184, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 747, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
