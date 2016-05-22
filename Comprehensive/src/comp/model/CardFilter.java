/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.model;

/**
 *
 * @author thallock
 */
public interface CardFilter {
    
    boolean accept(Card card);
}
