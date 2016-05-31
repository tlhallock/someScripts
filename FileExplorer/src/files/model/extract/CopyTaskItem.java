package files.model.extract;

import java.nio.file.Path;

class CopyTaskItem
{
	Path source;
	Path destination;
	long numBytes;
	
	public CopyTaskItem(Path source, Path destination)
	{
		this.source = source;
		this.destination = destination;
		numBytes = source.toFile().length(); // TODO: fix this...
	}
	
	public String toString()
	{
		return "Copy " + source + " to " + destination + " which is " + numBytes + " long";
	}
}