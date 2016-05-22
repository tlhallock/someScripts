/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import files.app.Application;
import files.app.ExtensionManager.ExtensionsListener;
import files.app.ExtensionManager.FileType;
import files.app.Logger.LogLevel;
import files.util.ComponentShowingListener;

/**
 *
 * @author thallock
 */
public class ExtensionViewer extends javax.swing.JPanel implements ExtensionsListener {

	DefaultListModel<FileType> list = new DefaultListModel<FileType>();
	FileType currentlyViewing;
    
    /**
     * Creates new form ExtensionViewer
     */
    public ExtensionViewer() {
        initComponents();
        
        createPopupMenu();
        
        ExtensionViewer t = this;
        new ComponentShowingListener(this) {
			@Override
			protected void isShowing() {
					t.extensionsChanged();
				Application.getApplication().getKnownFileTypes().add(t);
			}
			
			@Override
			protected void isNotShowing() {
				Application.getApplication().getKnownFileTypes().remove(t);
			}
		};

		jList1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				JList jList = (JList)evt.getSource();
				if (evt.getClickCount() != 2)
					return;
				int index = jList.locationToIndex(evt.getPoint());
				if (index < 0)
					return;
				FileType fileType = list.get(index);
				if (fileType == null)
					return;
				select(fileType);
			}
		});

		extensionsChanged();
	}

	private void createPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Add new type");
        item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileType newFileType = Application.getApplication().getKnownFileTypes().createNewFileType();
				console.setText(Application.getApplication().getKnownFileTypes().addIfCompatible(newFileType));
			}
		});
        menu.add(item);
        
        item = new JMenuItem("Delete Selected");
        item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LinkedList<FileType> toDelete = new LinkedList<>();
				StringBuilder builder = new StringBuilder("Deleting ");
				int[] selectedIndices = jList1.getSelectedIndices();
				for (int i : selectedIndices)
				{
					FileType elementAt = list.getElementAt(i);
					builder.append("\"").append(elementAt).append("\" ");
					toDelete.add(elementAt);
				}
				Application.getApplication().getKnownFileTypes().removeAll(toDelete);
			}
		});
        menu.add(item);
        jList1.setComponentPopupMenu(menu);
	}

	void select(FileType newFileType) {
		if (newFileType == null || !Application.getApplication().getKnownFileTypes().hasFileType(newFileType)) {
			currentlyViewing = null;
			jTextField1.setText("");
			jTextArea1.setText("");
			nameField.setText("");
			jButton1.setEnabled(false);
			jButton2.setEnabled(false);
			console.setText("");
			nameField.setEditable(false);
			jTextArea1.setEditable(false);
			jTextField1.setEditable(false);
			return;
		}

		currentlyViewing = newFileType;

		jTextField1.setText(PaintUtils.format(newFileType.getCommands(), ' '));
		jTextArea1.setText(PaintUtils.format(newFileType.getExtensions(), '\n'));
		nameField.setText(newFileType.getName());
		jButton1.setEnabled(true);
		jButton2.setEnabled(true);
		console.setText("Viewing " + newFileType.getName());
		nameField.setEditable(true);
		jTextArea1.setEditable(true);
		jTextField1.setEditable(true);
		return;
	}

	@Override
	public void extensionsChanged() {
		list.removeAllElements();
		
		LinkedList<FileType> fileTypes = new LinkedList<>();
		for (FileType fileType : Application.getApplication().getKnownFileTypes().getFileTypes())
			fileTypes.add(fileType);
		Collections.sort(fileTypes);
		for (FileType fileType : fileTypes)
			list.addElement(fileType);
		select(currentlyViewing);
	}

	private DefaultListModel getListModel() {
		return list;
	}
        
    private void setCommand() {
        if (currentlyViewing == null)
        	return;
        currentlyViewing.setCommands(jTextField1.getText());
    }
    private void setName() {

        if (currentlyViewing == null)
        	return;
        console.setText(Application.getApplication().getKnownFileTypes().updateNameIfPossible(currentlyViewing, nameField.getText()));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        nameField = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        console = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        jSplitPane1.setDividerLocation(301);

        jList1.setModel(getListModel());
        jScrollPane1.setViewportView(jList1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel1.setText("Run");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Change...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("<file>");

        jLabel3.setText("Icon:");

        jLabel5.setText("Name:");

        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        nameField.setText("jTextField2");
        nameField.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        jButton4.setText("Update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addGap(76, 76, 76)
                .addComponent(jLabel3)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jSplitPane2.setTopComponent(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Known extenions"));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        console.setText("Initialized");

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(console, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(console)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        try {
            jTextField1.setText(chooser.getSelectedFile().getCanonicalPath());
        } catch (IOException ex) {
            Application.getApplication().getLogger().log(LogLevel.Normal, "Unable to get path of file " + chooser.getSelectedFile(), ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        TreeSet<String> exts = PaintUtils.format(jTextArea1.getText());
        String status = Application.getApplication().getKnownFileTypes().setIfCompatible(currentlyViewing, exts);
        console.setText(status);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        setName();
    }//GEN-LAST:event_nameFieldActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        setName();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        setCommand();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        setCommand();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel console;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField nameField;
    // End of variables declaration//GEN-END:variables


}
