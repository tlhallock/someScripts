/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.model;

import java.util.LinkedList;

/**
 *
 * @author thallock
 */
public class TagFilter implements CardFilter
{
	LinkedList<String> filters = new LinkedList<>();

	@Override
	public boolean accept(Card card)
	{
		for (String tag : card.tags)
			if (filters.contains(tag))
				return true;
		return false;
	}
}
