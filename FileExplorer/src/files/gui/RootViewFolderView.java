/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.gui;

import files.app.Application;
import files.gui.FileInteractionIF.FileOpenInteraction;
import files.model.FileEntry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thallock
 */
public class RootViewFolderView extends javax.swing.JPanel {

    private FileHistory history = new FileHistory();
    private FolderView folderView;
    
    FileOpenInteraction fileOpenInteraction = new FileOpenInteraction()
    {
        @Override
        public void doubleClickDirectory(FileEntry entry) {
            if (folderView == null) return;
            
            history.push(entry);
            folderView.showExistingDirectory(entry);
            updateButtons();
        }
    };
    
    RootViewFolderView()
    {
        initComponents();
    }
    
    void setFolderView(FolderView view)
    {
        folderView = view;
        child.removeAll();
        view.setInteraction(fileOpenInteraction);
        IdkWhereThisGoesAgain.setPanel(child, view.getView());
        
        updateButtons();
    }

    private void updateButtons() {
        FileEntry currentPath;
        if (folderView == null || (currentPath = folderView.getCurrentDirectoy()) == null) {
            jTextField1.setText("");
            jButton2.setEnabled(false);
            jButton1.setEnabled(false);
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            return;
        }

        jButton2.setEnabled(!history.isEmpty());
        Path parent = currentPath.getPath().getParent();
        jButton1.setEnabled(parent != null && !parent.equals(currentPath.getPath()));

        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
        
        jTextField1.setText(currentPath.getPath().toString());
    }
    
    public void show(FileEntry entry)
    {
        history.push(entry);
        if (folderView != null)
            folderView.showRootDirectory(entry);
        updateButtons();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        child = new javax.swing.JPanel();

        jButton1.setText("Up");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Back");

        jButton3.setText("New File");

        jButton4.setText("Terminal");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(1404, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        child.setBackground(new java.awt.Color(255, 204, 102));

        javax.swing.GroupLayout childLayout = new javax.swing.GroupLayout(child);
        child.setLayout(childLayout);
        childLayout.setHorizontalGroup(
            childLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        childLayout.setVerticalGroup(
            childLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 877, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(child, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(child, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        if (folderView == null) return;
        Path p = Paths.get(jTextField1.getText());
        if (!Files.exists(p))
        {
            jTextField1.setText(folderView.getCurrentDirectoy().getPath().toString());
            return;
        }
        try {
            show(FileEntry.load(p));
        } catch (IOException ex) {
            Application.getApplication().getLogger().log(files.app.Logger.LogLevel.None, "Unable to open path " + p, ex);
            jTextField1.setText(folderView.getCurrentDirectoy().getPath().toString());
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        up();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (folderView == null) return;
        Application.getApplication().getSystem().launchTerminal(folderView.getCurrentDirectoy());
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel child;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private void up() {
        if (folderView == null) {
            return;
        }
        
        FileEntry currentPath = folderView.getCurrentDirectoy();
        Path parent = currentPath.getPath().getParent();

        if (parent == null || parent.equals(currentPath.getPath())) {
            return;
        }

        try {
            FileEntry parentEntry;
            parentEntry = FileEntry.load(parent);

            folderView.goUpToDirectory(parentEntry);
        } catch (IOException e) {
            Application.getApplication().getLogger().log(files.app.Logger.LogLevel.Normal, "Unable to load parent.", e);
            return;
        }
        
        updateButtons();
    }
}
