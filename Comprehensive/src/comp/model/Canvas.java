/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 *
 * @author thallock
 */
public class Canvas extends JPanel {
    BufferedImage image;
    
    public void setImage(BufferedImage image)
    {
    	this.image = image;
    }
    
    @Override
	public void paint(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        
        if (image == null)
        {
        	g.setColor(Color.black);
        	g.fillRect(0, 0, getWidth(), getHeight());
        }
        else
        {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), this);
        }
    }
}
