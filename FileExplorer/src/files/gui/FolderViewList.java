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
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.stream.Stream;

import javax.swing.JComponent;
import javax.swing.JPanel;

import files.app.Application;
import files.app.Logger.LogLevel;
import files.gui.FileViewHeader.Column;
import files.gui.FileViewHeader.ColumnManager;
import files.gui.FileViewHeader.Columns;
import files.model.FileEntry;
import files.model.FileEntryAttributes.FileEntryAttributeKey;

/**
 *
 * @author thallock
 */
public abstract class FolderViewList<T extends FileView> extends javax.swing.JPanel implements ComponentListener, FolderView, FileFilterer {

    protected int height = 50;
    
    protected FileViewHeader header;
    protected LinkedList<T> details;
    protected FileInteractionIF interaction;
    private FileEntry current;
    
    private TreeSet<FileEntry> marked = new TreeSet<>();
        
    	// DOESN"T WORK:
//    	canvas.setBackground(Application.getApplication().getColorSelector().getListBackground());
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
    
    
    /**
     * Creates new form FolderView
     */
    public FolderViewList(FileEntryAttributeKey[] attributes) {
        details = new LinkedList<>();
        header = new FileViewHeader(new ColumnManager() {
            @Override
            public void repaint() {
                header.repaint();
                canvas.repaint();
            }

            @Override
            public void sort(int column, boolean reverse) {
                sortColumns(column, reverse);
                adjustSizes();
            }

            @Override
            public int getDesiredWidth(FileEntryAttributeKey key) {
                return getMinimalDesiredWidth(key);
            }
        }, getShowNameFirst(), getFixedColumns());
        initComponents();
        canvas.setLayout(null);

        int i = 0;
        for (FileEntryAttributeKey key : attributes) {
            header.addColumn(new Column(i++, key));
        }

        jScrollPane1.addComponentListener(this);
//		canvas.addComponentListener(l);

        adjustSizes();
    }


	protected int getMinimalDesiredWidth(FileEntryAttributeKey key)
	{
		throw new RuntimeException("Don't call me!");
	}

	public void setFolderHighlighted(boolean val)
	{
		header.setFolderHighlighted(val);
	}

    protected static void safelyLoad(LinkedList<FileEntry> list, Path p) {
        try {
            list.add(FileEntry.load(p));
        } catch (IOException e) {
            Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to read file: " + p, e);
        }
    }

    protected void show(FileEntry p, boolean remove) {
        this.current = p;
        Application.getApplication().getLogger().log(LogLevel.Normal, "Showing " + p);
        LinkedList<FileEntry> entries = new LinkedList<>();

        // wtf mate
        try (Stream<Path> list = Files.list(p.getPath());) {
            list.forEach(x -> safelyLoad(entries, x));
        } catch (IOException e) {
            Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to list " + p, e);
        }

        synchronized (details) {
            if (remove) {
                canvas.removeAll();
                details.clear();
            }

            for (FileEntry entry : entries) {
                T detailView = createFileView(header.getColumns(), entry, interaction, this);
                if (detailView == null)
                	continue;
                details.add(detailView);
                detailView.setVisible(true);
            }
        }

        if (remove) {
            adjustSizes();
        }
    }

	private JPanel getHeader()
	{
		return header;
	}

	void setHighlight(FileEntry entry)
	{
		synchronized (details)
		{
			for (FileView detail : details)
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
			for (FileView detail : details)
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
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Case");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Show Hidden");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

	public void setInteraction(FileInteractionIF interaction)
	{
		this.interaction = interaction;
		synchronized (details)
		{
			for (FileView view : details)
				view.setInteraction(interaction);
		}
	}
        
        @Override
        public FileEntry getCurrentDirectoy() {
            return current;
        }
    
        @Override
        public JComponent getView() {
            return this;
        }

        @Override
        public void goUpToDirectory(FileEntry entry)
        {
            showRootDirectory(entry);
        }

        @Override
	public void showExistingDirectory(FileEntry p)
	{
            showRootDirectory(p);
	}

        @Override
	public void showRootDirectory(FileEntry p)
	{
		show(p, true);
	}
    
	protected void adjustSizes()
	{
		int w = jScrollPane1.getViewport().getWidth();
		header.setEntryWidth(w);

		int count = 0;
		
		LinkedList<T> toShow = new LinkedList<>();
		synchronized (details)
		{
			canvas.removeAll();

			for (T detail : details)
			{
				if (detail.isFiltered())
					continue;

				canvas.add(detail);
				toShow.add(detail);
				count++;
			}
		}
		
		Dimension d = setLocations(toShow, w);

		canvas.setPreferredSize(d);
		jLabel2.setText(String.valueOf(count));
		repaint();
	}
	
	public boolean filter(FileEntry entry)
	{

		String filterText = jTextField1.getText();
		if (!jCheckBox1.isSelected())
			filterText = filterText.toLowerCase();
		
		String name2 = entry.getName();
		

		if (filterText.length() != 0 && !name2.contains(filterText))
		{
			return true;
		}
		if (entry.isHidden() && !jCheckBox2.isSelected())
		{
			return true;
		}

		return false;
	}



	protected abstract T createFileView(Columns columns, FileEntry entry, FileInteractionIF interaction, FileFilterer filterer);
	protected abstract boolean getShowNameFirst();
	protected abstract boolean getFixedColumns();
	protected abstract Dimension setLocations(LinkedList<T> toShow, int w);
	protected abstract void sortColumns(int column, boolean reverse);
}
