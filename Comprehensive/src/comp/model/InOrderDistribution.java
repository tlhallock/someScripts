/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.model;

import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author thallock
 */
public class InOrderDistribution extends CardDistribution
{
    Iterator<Card> it;

    public InOrderDistribution(Deck deck) {
        super(deck);
    }

    @Override
    Card sample() {
        if (it == null || !it.hasNext())
        {
            Collections.shuffle(deck);
            it = deck.iterator();
        }
        
        return it.next();
    }
    
}
