package comp.model;

import java.util.Collection;
import java.util.LinkedList;

public class Card {
	Content statement;

	double rating;

	long attempts;
	long success;

	String exactAnswer;

	LinkedList<Content> answers = new LinkedList<>();
	LinkedList<String> tags = new LinkedList<>();
	
	
	public Collection<Content> getAnswers()
	{
		return answers;
	}
}
