package dockable.app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class Writer {

	public static Writer writer = new Writer();

	ObjectMapper objectMapper = new ObjectMapper();
	
	private Writer()
	{
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
	
	public String toString(Object object)
	{
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "Failed to generate JSON";
		}
	}
	

	public void write(File f, Object object) throws IOException
	{
		try {
			objectMapper.writeValue(f, object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public JsonNode read(File file) throws JsonProcessingException, IOException
	{
		return objectMapper.readTree(file);
	}
}
