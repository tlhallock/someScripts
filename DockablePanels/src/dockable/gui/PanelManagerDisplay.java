package dockable.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.DockableApplication;
import dockable.app.Writer;
import dockable.tree.DockableNode;
import dockable.tree.DockableTreeNode;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dockable.tree.RootNode;


/**
 *
 * @author thallock
 */
public class PanelManagerDisplay extends javax.swing.JFrame {

    DefaultMutableTreeNode root;
    DockableApplication app;
    RootNode currentRoot;
    
    String lastDirectory = "./";
    
    
    final JPanel nullPanel1 = new JPanel();
    final JPanel nullPanel2 = new JPanel();
    
    /**
     * Creates new form PanlManager
     */
    public PanelManagerDisplay(DockableApplication app) {
    	this.app = app;
        root = new DefaultMutableTreeNode("Root");
        initComponents();
        
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.addTreeSelectionListener(new SelectionListener());
        removeOptions();
    }
    
    class SelectionListener implements TreeSelectionListener {
        @Override
	public void valueChanged(TreeSelectionEvent se) {
            JTree tree = (JTree) se.getSource();
            Object s =  tree.getLastSelectedPathComponent();
            if (!(s instanceof DockableTreeNode))
            	return;
            
            DockableTreeNode selectedNode = (DockableTreeNode) s;
            TreeChangedListener listener = new TreeChangedListener()
            {
				@Override
				public void parentInserted(DockableNode newChild) {
                    removeOptions();
                    DockableTreeNode parent = (DockableTreeNode) selectedNode.getParent();
                    int index = parent.getIndex(selectedNode);
                    selectedNode.removeFromParent();
                    DockableTreeNode newNode = newChild.createJTree();
                    parent.insert(newNode, index);
                    ((DefaultTreeModel) jTree1.getModel()).nodeStructureChanged(parent);
                    expandAll(); // shouldn't do all of this...
				}

				@Override
				public void parentRemoved(DockableNode newParent) {
                    removeOptions();
                    DockableTreeNode oldParent = (DockableTreeNode) selectedNode.getParent();
                    DockableTreeNode grandParent = (DockableTreeNode) oldParent.getParent();
                    int index = grandParent.getIndex(oldParent);
                    oldParent.removeFromParent();
                    DockableTreeNode newNode = newParent.createJTree();
                    grandParent.insert(newNode, index);
                    ((DefaultTreeModel) jTree1.getModel()).nodeStructureChanged(grandParent);
                    expandAll(); // shouldn't do all of this...
				}
            };
            JPanel panel = selectedNode.getCustomization(listener);
            jSplitPane2.setLeftComponent(panel);
            jSplitPane2.setRightComponent(new SplitingOptions(selectedNode.getPanel(), listener));
        	jSplitPane2.setDividerLocation(jSplitPane2.getWidth() / 2);
        }
    }

    DefaultMutableTreeNode getRoot()
    {
        return root;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree(getRoot());
        jSplitPane2 = new javax.swing.JSplitPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Remove Empty Panels");

        jButton3.setText("Refresh to Application");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTree1.setEditable(true);
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jSplitPane2.setDividerLocation(300);
        jSplitPane1.setRightComponent(jSplitPane2);

        jMenu1.setText("File");

        jMenuItem3.setText("Open");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem1.setText("Save");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jCheckBoxMenuItem1.setText("Lock");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        app.setTree(currentRoot);
        removeOptions();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        display(app.getRoot());
        removeOptions();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(lastDirectory));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
            	JsonNode node = Writer.writer.read(chooser.getSelectedFile());
            	lastDirectory = chooser.getSelectedFile().getParent();
            	display(new RootNode(node));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(lastDirectory));
    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        try {
        	Writer.writer.write(chooser.getSelectedFile(), currentRoot);
        	lastDirectory = chooser.getSelectedFile().getParent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        app.setLocked(jCheckBoxMenuItem1.isSelected());
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(PanelManagerDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(PanelManagerDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(PanelManagerDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(PanelManagerDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//			public void run() {
//                new PanelManagerDisplay().setVisible(true);
//            }
//        });
//    }
//    
    
    private void expandAll()
    {
        for (int i = 0; i < jTree1.getRowCount(); i++) {
            jTree1.expandRow(i);
        }
    }
    
    private void removeOptions()
    {
    	jSplitPane2.setLeftComponent(nullPanel1);
    	jSplitPane2.setRightComponent(nullPanel2);
    	jSplitPane2.setDividerLocation(jSplitPane2.getWidth() / 2);
    }
    
	public void display(RootNode node) {
		currentRoot = node;
        root.removeAllChildren();
        root.add(currentRoot.createJTree());
        ((DefaultTreeModel) jTree1.getModel()).nodeStructureChanged(getRoot());
        removeOptions();
        expandAll();
	}
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}