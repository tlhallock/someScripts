/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.test;

import javax.swing.JFrame;
import javax.swing.JPanel;

import files.util.ComponentShowingListener;

/**
 *
 * @author thallock
 */
public class Test {
	
    
    public static void main(String[] args) throws InterruptedException
    {
        JFrame frame = new JFrame();
        frame.setBounds(50,50,50,50);
        
        JPanel panel = new JPanel();
        
        
        new ComponentShowingListener(panel) {
			
			@Override
			protected void isShowing() {
				System.out.println("enter");
			}
			
			@Override
			protected void isNotShowing() {
				System.out.println("exit");
			}
		};
        
        frame.getContentPane().add(panel);
        System.out.println("setting visible");
        frame.setVisible(true);
        
        System.out.println("nothing");
        Thread.sleep(1000);
        
        System.out.println("disposing");
        frame.dispose();

        Thread.sleep(1000);
        System.out.println("Nothing");
    }
}
