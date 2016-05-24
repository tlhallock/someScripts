/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.gui;

import java.awt.Container;

import javax.swing.JComponent;

/**
 *
 * @author thallock
 */
public class IdkWhereThisGoesAgain
{
    public static void setPanel(Container listPanel, JComponent temp)
    {
        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(temp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(temp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
    

    public static int max(int a, int b)
    {
	    return a > b ? a : b;
    }
    public static int max(int... a)
    {
	    int returnValue = Integer.MIN_VALUE;
	    for (int i : a)
	    {
		    returnValue = i > returnValue ? i : returnValue; 
	    }
	    return returnValue;
    }
}
