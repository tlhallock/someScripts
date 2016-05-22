/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.model;

import java.util.Collections;

/**
 *
 * @author thallock
 */
public abstract class CardDistribution
{
    Deck deck;
    
    CardDistribution(Deck deck)
    {
        this.deck = deck;
    }
    
    abstract Card sample();
}
