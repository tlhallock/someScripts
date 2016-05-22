package comp.model;


public abstract class Content
{
	String name;
	
	public Content(String name)
	{
		this.name = name;
	}
	
    public abstract void writeTo(Canvas panel);

	public String getName() {
		return name;
	}
}
