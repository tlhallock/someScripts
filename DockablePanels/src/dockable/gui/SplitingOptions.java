/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dockable.gui;

import dockable.tree.DockableNode;

/**
 *
 * @author thallock
 */
public class SplitingOptions extends javax.swing.JPanel {

    DockableNode node;
    TreeChangedListener notifier;
    
    private static final int DEFAULT_NEW_DIVIDER_LOCATION = 50;
    
    /**
     * Creates new form SplitingOptions
     */
    public SplitingOptions(
            DockableNode node,
            TreeChangedListener notifier) {
        initComponents();
        this.node = node;
        this.notifier = notifier;
        
        setBorder(javax.swing.BorderFactory.createTitledBorder("Splitting options"));
        
        sL.setEnabled(node.splitable());
        sR.setEnabled(node.splitable());
        sT.setEnabled(node.splitable());
        sB.setEnabled(node.splitable());
        aT.setEnabled(node.tabable());
        c.setEnabled(node.collapseable());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sR = new javax.swing.JButton();
        sL = new javax.swing.JButton();
        sT = new javax.swing.JButton();
        sB = new javax.swing.JButton();
        aT = new javax.swing.JButton();
        c = new javax.swing.JButton();

        sR.setText("Split Right");
        sR.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                sRActionPerformed(evt);
            }
        });

        sL.setText("Split Left");
        sL.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                sLActionPerformed(evt);
            }
        });

        sT.setText("Split Top");
        sT.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                sTActionPerformed(evt);
            }
        });

        sB.setText("Split Bottom");
        sB.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                sBActionPerformed(evt);
            }
        });

        aT.setText("Add tabs");
        aT.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                aTActionPerformed(evt);
            }
        });

        c.setText("Collapse");
        c.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                cActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sR, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aT))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sT, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(c)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sL)
                    .addComponent(sR)
                    .addComponent(aT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sT)
                    .addComponent(sB)
                    .addComponent(c))
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sLActionPerformed
        notifier.parentInserted(node.splitLeft());
    }//GEN-LAST:event_sLActionPerformed

    private void sRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sRActionPerformed
    	notifier.parentInserted(node.splitRight());
    }//GEN-LAST:event_sRActionPerformed

    private void sTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sTActionPerformed
    	notifier.parentInserted(node.splitTop());
    }//GEN-LAST:event_sTActionPerformed

    private void sBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sBActionPerformed
    	notifier.parentInserted(node.splitBottom());
    }//GEN-LAST:event_sBActionPerformed

    private void aTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aTActionPerformed
    	notifier.parentInserted(node.addTabs());
    }//GEN-LAST:event_aTActionPerformed

    private void cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cActionPerformed
    	notifier.parentRemoved(node.collapse());
    }//GEN-LAST:event_cActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aT;
    private javax.swing.JButton c;
    private javax.swing.JButton sB;
    private javax.swing.JButton sL;
    private javax.swing.JButton sR;
    private javax.swing.JButton sT;
    // End of variables declaration//GEN-END:variables
}
