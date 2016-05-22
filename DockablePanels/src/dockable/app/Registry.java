package dockable.app;

public final class Registry
{
	private NameGenerator nameGenerator;
	
	
	private Registry()
	{
		nameGenerator = new NameGenerator();
	}
	
	NameGenerator getNameGenerator()
	{
		return nameGenerator;
	}
	

	
	
	private static Registry registry = new Registry();
	public static Registry getRegistry()
	{
//		if (registry == null)
//		{
//			registry = new Registry();
//		}
		return registry;
	}

}
