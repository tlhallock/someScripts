/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.model;

import java.io.File;
import java.util.LinkedList;

/**
 *
 * @author thallock
 */
public class Deck extends LinkedList<Card> {
    
	private String name;
	
	public Deck(File file)
	{
		
	}
	
	public Deck(String name)
	{
		this.name = name;
	}
	
	
	void writeTo(String path)
	{
		
	}
	
    Deck filter(CardFilter filter)
    {
        Deck returnValue = new Deck("filtered " + name + " " + System.currentTimeMillis());
        
        for (Card c : this)
            if (filter.accept(c))
                returnValue.add(c);
        
        return returnValue;
    }
}
