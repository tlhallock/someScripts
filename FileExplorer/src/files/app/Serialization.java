package files.app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class Serialization {

	public static Serialization writer = new Serialization();

	ObjectMapper objectMapper = new ObjectMapper();
	
	private Serialization()
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
	
	public <T> T read(File file, Class<T> type) throws JsonParseException, JsonMappingException, IOException
	{
		return objectMapper.readValue(file, type);
	}
}
