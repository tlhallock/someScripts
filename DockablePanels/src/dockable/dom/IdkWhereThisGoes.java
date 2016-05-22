package dockable.dom;

import java.awt.Container;

import javax.swing.JComponent;

public class IdkWhereThisGoes {
	
	public static void afix(Container container, JComponent child)
	{
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(child);
//        child.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 1575, Short.MAX_VALUE)
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 983, Short.MAX_VALUE)
//        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(container);
        container.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(child,
                		javax.swing.GroupLayout.DEFAULT_SIZE,
                		javax.swing.GroupLayout.DEFAULT_SIZE,
                		Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(child,
                		javax.swing.GroupLayout.DEFAULT_SIZE,
                		javax.swing.GroupLayout.DEFAULT_SIZE,
                		Short.MAX_VALUE)
                .addContainerGap())
        );
	}
}
