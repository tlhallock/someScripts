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
public class WithReplacementDistribution extends CardDistribution
{

    public WithReplacementDistribution(Deck deck)
    {
        super(deck);
    }

    @Override
    public Card sample()
    {
        Collections.shuffle(deck);
        return deck.getFirst();
    }
}
