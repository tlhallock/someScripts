/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.gui;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.stream.Stream;

import javax.swing.JPanel;

import files.app.Application;
import files.app.Logger.LogLevel;
import files.gui.DetailsHeaderView.Column;
import files.gui.DetailsHeaderView.ColumnManager;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;
import files.model.FileEntryAttributes.FileEntryAttributeValue;

/**
 *
 * @author thallock
 */
public class DetailsListView extends javax.swing.JPanel {

    int height = 50;
    
    DetailsHeaderView header;
    LinkedList<DetailsFileView> details;
    FileInteraction interaction;
    
    TreeSet<FileEntry> marked = new TreeSet<>();

    public DetailsListView() {
    	this(new FileEntryAttributeKey[] {
    			FileEntryAttributeKey.All_Name,
    			FileEntryAttributeKey.File_LastModified,
    			FileEntryAttributeKey.File_LastAccessed,
    			FileEntryAttributeKey.File_Created,
    			FileEntryAttributeKey.File_Length,
    	});
    	
    	canvas.setBackground(Application.getApplication().getColorSelector().getListBackground());
        
    	// DOESN"T WORK:
//        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				adjustSizes();
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				adjustSizes();
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				adjustSizes();
//			}
//		});
    }
    /**
     * Creates new form FolderView
     */
    public DetailsListView(FileEntryAttributeKey[] attributes) {
    	details = new LinkedList<>();
        header = new DetailsHeaderView(new ColumnManager() {
			@Override
			public void repaint() {
				header.repaint();
				canvas.repaint();
			}

			@Override
			public void sort(int column, boolean reverse) {
				synchronized(details)
				{
					Collections.sort(details, new Comparator<DetailsFileView>(){
						@Override
						public int compare(DetailsFileView o1, DetailsFileView o2) {
							FileEntryAttributeValue display1 = o1.get(column);
							FileEntryAttributeValue display2 = o2.get(column);
							int cmp = 0;
							if (display1 == null)
								cmp = display2 == null ? 0 : 1;
							else if (display2 == null)
								cmp = -1;
							else
								cmp = display1.compareTo(display2);
							return (reverse ? -1 : 1) * cmp;
						}});
				}
				adjustSizes();
			}});
        initComponents();
        canvas.setLayout(null);
        
        int i=0;
        for (FileEntryAttributeKey key : attributes)
        	header.addColumn(new Column(i++, key));
        
        ComponentListener l = new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				adjustSizes();
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				adjustSizes();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		};
		jScrollPane1.addComponentListener(l);
//		canvas.addComponentListener(l);
    }
    
    public void setFolderHighlighted(boolean val)
    {
    	header.setFolderHighlighted(val);
    }
    
    public void setInteraction(FileInteraction interaction)
    {
    	this.interaction = interaction;
    	synchronized (details) {
    		for (DetailsFileView view : details)
    			view.setInteraction(interaction);
		}
    }

	private static void safelyLoad(LinkedList<FileEntry> list, Path p) {
		try {
			list.add(FileEntry.load(p));
		} catch (IOException e) {
			Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to read file: " + p, e);
		}
	}
    
    public void show(Path p)
    {
    	Application.getApplication().getLogger().log(LogLevel.Normal, "Showing " + p);
    	LinkedList<FileEntry> entries = new LinkedList<>();
    	
    	// wtf mate
        try (Stream<Path> list = Files.list(p);)
        {
        	list.forEach(x -> safelyLoad(entries, x));
        } catch (IOException e) {
        	Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to list " + p, e);
		}
        
        synchronized(details)
        {
        	canvas.removeAll();
        	details.clear();
        
        	for (FileEntry entry : entries)
        	{
        		DetailsFileView detailView = new DetailsFileView(header.getColumns(), entry, interaction);
        		details.add(detailView);
        		detailView.setVisible(true);
        	}
        }
        
        adjustSizes();
    }
    
    private void adjustSizes()
    {
		String filterText = jTextField1.getText();
		if (!jCheckBox1.isSelected())
			filterText = filterText.toLowerCase();

		int w = jScrollPane1.getViewport().getWidth();
		int y = 0;
		// header.setBounds(0, y, canvas.getWidth(), headerHeight);
		// y += headerHeight;

		boolean even = false;
		int count = 0;
		synchronized (details) {
			details.stream().forEach(x -> canvas.remove(x));

			for (DetailsFileView detail : details) {
				String name = detail.getEntry().getName();
				if (!jCheckBox1.isSelected()) name = name.toLowerCase();
				if (filterText.length() != 0 && !name.contains(filterText))
                        continue;
				if (detail.getEntry().isHidden() && !jCheckBox2.isSelected())
					continue;
				
				canvas.add(detail);
				detail.setBounds(0, y, w, height);
				detail.setEven(even = !even);
				y += height;
				count++;
			}
		}

		Dimension d = new Dimension(w, y);
		canvas.setPreferredSize(d);
		jLabel2.setText(String.valueOf(count));
		repaint();
	}
    
    private JPanel getHeader()
    {
    	return header;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    void setHighlight(FileEntry entry)
    {
    	synchronized (details)
    	{
    		for (DetailsFileView detail : details)
        	{
    			detail.setHighlighted(detail.getEntry().equals(entry));
        	}
    	}
    	repaint();
    }
    
    void setMarks()
    {
    	synchronized (details)
    	{
    		for (DetailsFileView detail : details)
        	{
    			detail.setHighlighted(marked.contains(detail));
        	}
    	}
    	repaint();
    }

    void clearMarked()
    {
    	marked.clear();
    	setMarks();
    }
    
    void addMarked(FileEntry entry)
    {
    	marked.add(entry);
    	setMarks();
    }
    
    void removeMarked(FileEntry entry)
    {
    	marked.remove(entry);
    	setMarks();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = getHeader();
        jScrollPane1 = new javax.swing.JScrollPane();
        canvas = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();

        jLabel1.setText("Number of items:");

        jLabel2.setText("0");

        jSplitPane1.setDividerLocation(50);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel2.setBackground(new java.awt.Color(153, 255, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1606, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane1.setTopComponent(jPanel2);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
        canvas.setLayout(canvasLayout);
        canvasLayout.setHorizontalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1588, Short.MAX_VALUE)
        );
        canvasLayout.setVerticalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1007, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(canvas);

        jSplitPane1.setRightComponent(jScrollPane1);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Case");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Show Hidden");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jCheckBox2))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        adjustSizes();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        adjustSizes();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        adjustSizes();
    }//GEN-LAST:event_jCheckBox2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel canvas;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
