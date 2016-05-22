package snap;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SinglePanelFrame extends JFrame
{
	public SinglePanelFrame(JPanel panel)
	{
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 785, Short.MAX_VALUE)
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 482, Short.MAX_VALUE)
//        );

		
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
	}
	
	
	public static SinglePanelFrame showPanel(JPanel panel, Rectangle location, String title)
	{
		SinglePanelFrame frame = new SinglePanelFrame(panel);
		frame.setTitle(title);
		frame.setBounds(location);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        return frame;
	}
}
