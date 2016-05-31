package files.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

public class CreateDirectoryStructure
{
	long maxFileLenth = 1 * 1024 * 1024 * 1024;
	double meanFileLength = 4.0 * 1024.0 * 1024.0;
	long minFileLength = 5;

	int minSubdirectories = 1;
	int maxSubdirectories = 5;

	int minFiles = 20;
	int maxFiles = 10000;

	int maxDepth = 5;

	int minFilenameLength = 3;
	int maxFilenameLength = 10; // 256;

	String allowablefileCharacters = ",.<>?;:'\"[]{}\\`1234567890-=+_)(*&^%$#@!~qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM" + (char) 0x2202 + (char) 0x2203;
	String checksumFile = "checksums.txt";

	Random random = new Random();

	private String[] extensions = new String[] { "txt", "data", "zip", "bin", "junk", "png", };

	private int randomInt(int min, int max)
	{
		if (min == max)
			return min;
		return min + random.nextInt(max - min);
	}
	private long randomLong(long min, long max)
	{
		if (min == max)
			return min;
		return min + Math.abs(random.nextLong() % (max - min));
	}

	private String createRandomFilename()
	{
		int length = randomInt(minFilenameLength, maxFilenameLength);
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			builder.append(allowablefileCharacters.charAt(random.nextInt(allowablefileCharacters.length())));
		return builder.toString();
	}

	private void createFile(Path p, BufferedWriter writer) throws IOException
	{
		double d = -Math.log(1-random.nextDouble())   *  meanFileLength;
		System.out.println(meanFileLength);
		long length = (long) Math.abs(Math.min(maxFileLenth, Math.max(minFileLength, d)));
		length = randomLong(minFileLength, length);
		
		byte[] buffer = new byte[1024];
		int written = 0;
		
		Path filepath = Paths.get(p.toString(), createRandomFilename() + "." + extensions[random.nextInt(extensions.length)]);

		MessageDigest digest = null;
		try
		{
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e)
		{
			System.out.println("No message digest...");
			e.printStackTrace();
		}
		
		System.out.println("Creating file of length " + FileUtils.byteCountToDisplaySize(length) + " at " + filepath);
		
		try (OutputStream output = Files.newOutputStream(filepath);)
		{
			while (written < length)
			{
				int toWrite = Math.min((int) (length - written), buffer.length);
				random.nextBytes(buffer);
				output.write(buffer, 0, toWrite);
				if (digest != null)
					digest.update(buffer, 0, toWrite);
				written += toWrite;
			}
		}

		if (digest != null)
			writer.write(filepath + "\t" + getDigest(digest.digest()) + "\n");
	}

	private void createDirectoryStructure(Path p, int depth, TreeSet<String> allFolders) throws IOException
	{
		if (depth > maxDepth)
			return;
		if (!Files.exists(p))
			Files.createDirectory(p);
		allFolders.add(p.toString());
		
		int numSubFolders = randomInt(minSubdirectories, maxSubdirectories);
		for (int i=0;i<numSubFolders;i++)
		{
			Path path = Paths.get(p.toString(), createRandomFilename());
			createDirectoryStructure(path, depth+1, allFolders);
		}
	}
	
	private void createFiles(Path p, TreeSet<String> allFolders) throws IOException
	{
		int numFiles = randomInt(minFiles, maxFiles);
		String[] array = allFolders.toArray(new String[0]);
		Path path = Paths.get(p.toString() + File.separator + checksumFile);
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);)
		{
			for (int i = 0; i < numFiles; i++)
				createFile(Paths.get(array[random.nextInt(array.length)]), writer);
		}
	}

	public void createDirectoryStructure(Path p) throws IOException
	{
		TreeSet<String> allFOlders = new TreeSet<String>();
		System.out.println("Creating directories...");
		createDirectoryStructure(p, 0, allFOlders);
		System.out.println("Creating files...");
		createFiles(p, allFOlders);
		System.out.println("done.");
	}

	private static String getDigest(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++)
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException
	{
		CreateDirectoryStructure c = new CreateDirectoryStructure();
		c.createDirectoryStructure(Paths.get("./test_directories"));
	}
}
