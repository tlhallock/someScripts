package files.test;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Random;
import java.util.TreeSet;

public class CreateDirectoryStructure
{
	int maxFileLenth  = 1 * 1024 * 1024 * 1024;
	int minFileLength = 5;
	
	int minSubdirectories = 1;
	int maxSubdirectories = 20;
	
	int minFiles = 20;
	int maxFiles = 1000;
	
	int minDepth = 3;
	int maxDepth = 5;
	
	int minFilenameLength = 3;
	int maxFilenameLength = 1024;
	
	String allowablefileCharacters = ",.<>?;:'\"[]{}\\`1234567890-=+_)(*&^%$#@!~" + (char) 0x2202 + (char)0x2203;
	String checksumFile = "checksums.txt";
	
	Random random = new Random();
	
	private String[] extensions = new String[]
	{
		"txt",
		"data",
		"zip",
		"bin",
		"junk",
		"png",
	};
	
	private String createRandomFilename()
	{
		
	}
	
	
	private void createFile(Path p, String name, PrintStream checksumStream)
	{
		
	}
	private void createDirectoryStructure(Path p, int depth, TreeSet<String> allFolders)
	{
		
	}

	public void createDirectoryStructure(Path p)
	{
		
	}
}
