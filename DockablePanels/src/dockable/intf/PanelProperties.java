package dockable.intf;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;

import dockable.app.Utils;



public class PanelProperties
{
	private String name;
	private String category;
	private HashMap<String, String> arguments = new HashMap<>();
	
	public PanelProperties() {}
	
	public PanelProperties(String name)
	{
		this.name = name;
	}

	public PanelProperties(JsonNode jsonNode) {
		name = jsonNode.get("name").asText();
		category = jsonNode.get("category").asText();
//		arguments = jsonNode.get("arguments").as
	}

	public String getCategory() {
		return category;
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof PanelProperties))
		{
			return false;
		}
		
		PanelProperties props = (PanelProperties) other;
		
		if (!props.name.equals(name))
		{
			return false;
		}
		if (!props.category.equals(category))
		{
			return false;
		}
		
		if (!Utils.hashMapEquals(arguments, props.arguments))
		{
			return false;
		}
		
		return true;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String string) {
		this.name = string;
	}

	public void setCategory(String string) {
		this.category = string;
	}

	public void setArgument(String string, String string2) {
		this.arguments.put(string, string2);
	}

	public PanelProperties duplicate()
	{
		PanelProperties returnValue = new PanelProperties();
		
		returnValue.name = name;
		returnValue.category = category;
		returnValue.arguments.putAll(arguments);
		
		return returnValue;
	}
}