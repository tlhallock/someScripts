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
import files.model.FileEntryAttributes.FileEntryAttributeKey;

/**
 *
 * @author thallock
 */
public class ColumnView extends javax.swing.JPanel {


	private static final int NEW_FOLDER_SIZE = 500;
	
    MultipleSplitPane splitPane;
    ArrayList<FileEntry> entries = new ArrayList<>();
    
    private static final FileEntryAttributeKey[] DEFAULT_ATTRIBUTES = new FileEntryAttributeKey[] {
		FileEntryAttributeKey.All_Name,
		FileEntryAttributeKey.File_Length,
    };
    
    /**
     * Creates new form ColumnView
     */
    public ColumnView() {
        initComponents();
        
        splitPane = (MultipleSplitPane) jPanel1;
        jScrollPane1.addComponentListener(splitPane);
    }
    
    
    
    public void show(FileEntry entry)
    {
    	synchronized (entries)
    	{
    		splitPane.removeAllChildren();
    		entries.clear();
    	
    		addView(entry, 1);
    		scrollToView(0);
			jTextField1.setText(entry.getPath().toString());
    	}
    }

	private void addView(FileEntry entry, int index) {
		DetailsListView view = new DetailsListView(DEFAULT_ATTRIBUTES);
		view.show(entry.getPath());
		view.setInteraction(new DepthInteraction(index));
		entries.add(entries.size(), entry);
		splitPane.postpendChild(view);
	}
	
    
    private class DepthInteraction implements FileInteraction
    {
    	private int index;
    	
    	public DepthInteraction(int idx)
    	{
    		this.index = idx;
    	}
    	
		@Override
		public void doubleClick(FileEntry entry) {
			Path p = entry.getPath();
			if (Files.isRegularFile(p))
			{
				System.out.println("Open " + p);
				scrollToView(index-1);
				return;
			}
			
			if (!Files.isDirectory(p))
			{
				return;
			}
			synchronized (entries)
			{
				((DetailsListView) splitPane.getChild(index-1)).setHighlight(entry);
				
				if (index == entries.size())
				{
					addView(entry, index+1);
					scrollToView(index);
					return;
				}

				if (entries.get(index).equals(entry))
				{
					scrollToView(index);
					setFolderHighlight(index);
					return;
				}
				splitPane.removeChildrenAfter(index);
				entries.set(index, entry);
				while (entries.size() > index + 1)
					entries.remove(entries.size() - 1);
				((DetailsListView) splitPane.getChild(index)).show(entry.getPath());
				scrollToView(index);
			}
		}
    }


	private void setFolderHighlight(int index) {

		for (int i=0;i<splitPane.getNumberOfChildren();i++)
		{
			((DetailsListView) splitPane.getChild(i)).setFolderHighlighted(i == index);
		}
	}
    private void scrollToView(int index)
    {
		setFolderHighlight(index);
		int childEnd = splitPane.getChildEnd(index);
		int childBegin = splitPane.getChildBegin(index);
		Rectangle viewRect = jScrollPane1.getViewport().getViewRect();
		if (viewRect.x + viewRect.width < childEnd)
			jScrollPane1.getViewport().setViewPosition(new Point(childEnd, 0));
		viewRect = jScrollPane1.getViewport().getViewRect();
		if (viewRect.x > childBegin) 
    		jScrollPane1.getViewport().setViewPosition(new Point(childBegin, 0));
    }
    
    private OptionsPanel createPanel()
    {
    	return new OptionsPanel(new OptionsListener() {
                @Override
                public void up()
                {
                	FileEntry fileEntry = entries.get(0);
                	Path parent = fileEntry.getPath().getParent();
                	
                	if (parent == null || parent.equals(fileEntry.getPath()))
                		return;
                	try {
                		entries.add(0, FileEntry.load(parent));
                	} catch (IOException e) {
                		Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to load parent.", e);
                		return;
                	}
                	
                	jTextField1.setText(parent.toString());
                	
                	DetailsListView view = new DetailsListView(DEFAULT_ATTRIBUTES);
                	view.show(parent);
                	view.setHighlight(fileEntry);
                	splitPane.prependChild(view);
                	
                	for (int i=0;i<entries.size();i++)
                		((DetailsListView) splitPane.getChild(i)).setInteraction(new DepthInteraction(i+1));

					scrollToView(0);
                }

                @Override
                public void showHidden(boolean val) {
                   
                }
	});
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
        jPanel2 = createPanel();
        jTextField1 = new javax.swing.JTextField();

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1160, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 663, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jPanel2.setBackground(new java.awt.Color(153, 255, 102));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        try {
			show(FileEntry.load(Paths.get(jTextField1.getText())));
		} catch (IOException e) {
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to show " + jTextField1.getText());
			e.printStackTrace();
		}
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
