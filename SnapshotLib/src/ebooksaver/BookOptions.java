///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ebooksaver;
//
///**
// *
// * @author default
// */
//public class BookOptions extends javax.swing.JPanel {
//
//    /**
//     * Creates new form BookOptions
//     */
//    public BookOptions() {
//        initComponents();
//    }
//
//    /**
//     * This method is called from within the constructor to initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is always
//     * regenerated by the Form Editor.
//     */
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        jLabel1 = new javax.swing.JLabel();
//        jLabel2 = new javax.swing.JLabel();
//        jLabel3 = new javax.swing.JLabel();
//        jLabel4 = new javax.swing.JLabel();
//        jButton1 = new javax.swing.JButton();
//        jButton2 = new javax.swing.JButton();
//        jLabel5 = new javax.swing.JLabel();
//        console = new javax.swing.JLabel();
//        jPanel1 = new javax.swing.JPanel();
//        jTextField1 = new javax.swing.JTextField();
//        jCheckBox1 = new javax.swing.JCheckBox();
//        jLabel7 = new javax.swing.JLabel();
//        jTextField2 = new javax.swing.JTextField();
//        jButton3 = new javax.swing.JButton();
//        jButton4 = new javax.swing.JButton();
//        jCheckBox2 = new javax.swing.JCheckBox();
//
//        jLabel1.setText("Where to click");
//
//        jLabel2.setText("Location to dock mouse:");
//
//        jLabel3.setText("Save the book to:");
//
//        jLabel4.setText("How to turn the page:");
//
//        jButton1.setText("Start");
//
//        jButton2.setText("Stop");
//
//        jLabel5.setText("Status");
//
//        console.setText("jLabel6");
//
//        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Book Options"));
//
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 100, Short.MAX_VALUE)
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 100, Short.MAX_VALUE)
//        );
//
//        jTextField1.setText("jTextField1");
//
//        jCheckBox1.setText("Dock mouse while grabbing screen");
//
//        jLabel7.setText("Name of the book:");
//
//        jTextField2.setText("jTextField2");
//
//        jButton3.setText("Save settings");
//
//        jButton4.setText("Load settings");
//
//        jCheckBox2.setText("Pause on mouse move");
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
//        this.setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addGap(681, 681, 681)
//                .addComponent(jCheckBox2)
//                .addContainerGap(529, Short.MAX_VALUE))
//            .addGroup(layout.createSequentialGroup()
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                        .addGap(0, 0, Short.MAX_VALUE)
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addGroup(layout.createSequentialGroup()
//                                .addComponent(jButton4)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jButton3))
//                            .addGroup(layout.createSequentialGroup()
//                                .addComponent(jButton2)
//                                .addGap(102, 102, 102)
//                                .addComponent(jButton1)))
//                        .addGap(20, 20, 20))
//                    .addGroup(layout.createSequentialGroup()
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addGroup(layout.createSequentialGroup()
//                                .addGap(196, 196, 196)
//                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                .addGap(275, 275, 275)
//                                .addComponent(jLabel4))
//                            .addGroup(layout.createSequentialGroup()
//                                .addGap(371, 371, 371)
//                                .addComponent(jLabel2)))
//                        .addGap(0, 0, Short.MAX_VALUE))
//                    .addGroup(layout.createSequentialGroup()
//                        .addContainerGap()
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addGroup(layout.createSequentialGroup()
//                                .addComponent(jLabel5)
//                                .addGap(18, 18, 18)
//                                .addComponent(console, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                            .addGroup(layout.createSequentialGroup()
//                                .addComponent(jLabel3)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jTextField1))
//                            .addGroup(layout.createSequentialGroup()
//                                .addComponent(jCheckBox1)
//                                .addGap(0, 0, Short.MAX_VALUE))
//                            .addGroup(layout.createSequentialGroup()
//                                .addComponent(jLabel7)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                .addComponent(jTextField2)))))
//                .addContainerGap())
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jLabel1)
//                .addGap(282, 282, 282))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jLabel3)
//                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jLabel7)
//                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addGap(69, 69, 69)
//                .addComponent(jCheckBox1)
//                .addGap(17, 17, 17)
//                .addComponent(jLabel2)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(5, 5, 5)
//                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(17, 17, 17)
//                        .addComponent(jLabel4)))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
//                .addComponent(jLabel1)
//                .addGap(88, 88, 88)
//                .addComponent(jCheckBox2)
//                .addGap(117, 117, 117)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jButton3)
//                    .addComponent(jButton4))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jButton1)
//                    .addComponent(jButton2))
//                .addGap(51, 51, 51)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jLabel5)
//                    .addComponent(console))
//                .addContainerGap())
//        );
//    }// </editor-fold>//GEN-END:initComponents
//
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JLabel console;
//    private javax.swing.JButton jButton1;
//    private javax.swing.JButton jButton2;
//    private javax.swing.JButton jButton3;
//    private javax.swing.JButton jButton4;
//    private javax.swing.JCheckBox jCheckBox1;
//    private javax.swing.JCheckBox jCheckBox2;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
//    private javax.swing.JLabel jLabel4;
//    private javax.swing.JLabel jLabel5;
//    private javax.swing.JLabel jLabel7;
//    private javax.swing.JPanel jPanel1;
//    private javax.swing.JTextField jTextField1;
//    private javax.swing.JTextField jTextField2;
//    // End of variables declaration//GEN-END:variables
//}
