package dockable.app;

import java.util.Random;
import java.util.TreeSet;

import dockable.tree.RootNode;

public class IdManager
{

	private static Random random = new Random();
	private static TreeSet<Integer> currentIds = new TreeSet<>();
	
	
	public static synchronized void reset(RootNode node)
	{
		currentIds.clear();
		node.contributeIds();
	}
	
	public static synchronized int getNewId()
	{
		int returnValue = -1;
		do
		{
			returnValue = Math.abs(random.nextInt());
		} while (returnValue < 0 || currentIds.contains(returnValue));
		return returnValue;
	}

	public static void addId(int id)
	{
		if (id < 0)
			return;
		if (!currentIds.add(id))
			throw new RuntimeException("Id already exists!");
	}
}
