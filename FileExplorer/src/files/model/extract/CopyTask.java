package files.model.extract;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

import files.model.extract.Nothingness.CopyTaskCompletionListener;
import files.model.extract.Nothingness.TaskState;

public class CopyTask
{

	private Path source;
	private Path destination;
	
	
	// replace existing
	// preserve permissions
	// preserve time stamps
	// move things that are already on the same filesystem...
	// follow links
	// copy links
	// update links
	// delete source upon completion
	// delete source upon copied
	private boolean deleteSource;

	private LinkedList<Nothingness.CopyTaskCompletionListener> listeners = new LinkedList<>();

	private Nothingness.TaskState state;

	private LinkedList<CopyTaskItem> fileItems = new LinkedList<>();
	private LinkedList<Path> folderItems = new LinkedList<>();
	
	private long totalBytes;
	private int numCopiedFiles;
	private long numCopiedBytes;
	
	private int numDirectoriesCopied;
	
	
	public CopyTask(Path source, Path destination)
	{
		this.source = source;
		this.destination = destination;
	}
	private synchronized void prepare()
	{
		state = Nothingness.TaskState.Preparing;
		prepare(source, destination);
	}
	
	private synchronized void perform()
	{
		state = Nothingness.TaskState.CreatingFolders;
		makeDirectories();
		state = Nothingness.TaskState.Copying;
		copyFiles();
	}
	
	private void clean()
	{
		state = Nothingness.TaskState.Finishing;
		notifyListeners(destination.toString());
		
		System.out.println("Clean up");
	}
	
	public void run()
	{
		prepare();
		perform();
		clean();
		

		state = Nothingness.TaskState.Done;
		notifyListeners(destination.toString());
	}
	
	
	
	
	
	
	
	
	
	

	private void copyFiles()
	{
		for (CopyTaskItem item : fileItems)
		{
			copy(item);
			numCopiedFiles++;
			numCopiedBytes += item.numBytes;
			notifyListeners(item.source.toString());
		}
	}
	private void makeDirectories()
	{
		Collections.sort(folderItems);
		for (Path p : folderItems)
		{
			if (!p.toFile().mkdirs())
			{
				System.out.println("Unable to make directory " + p);
			}
			numDirectoriesCopied++;
			notifyListeners(p.toString());
		}
	}
	
	private void prepare(Path source, Path destination)
	{
		LinkedList<Path> toRecurse = new LinkedList<>();
		
		try (Stream<Path> list = Files.list(source);)
		{
			Iterator<Path> iterator = list.iterator();
			while (iterator.hasNext())
			{
				Path file = iterator.next();
				
				if (Files.isRegularFile(file))
				{
					CopyTaskItem item = new CopyTaskItem(file, destination.resolve(file.getFileName()));
					fileItems.addLast(item);
					totalBytes += item.numBytes;
				}
				else if (Files.isDirectory(file))
				{
					toRecurse.addLast(file);
					folderItems.addLast(destination.resolve(file.getFileName().toString()));
				}
				else
				{
					System.out.println("Ignoring weird thing at " + file);
				}
				notifyListeners(file.toString());
				
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Collections.sort(toRecurse);
		
		for (Path p : toRecurse)
		{
			Path fileName = p.getFileName();
			prepare(source.resolve(fileName), destination.resolve(fileName));
		}
	}
	
	
	
	public void addListener(Nothingness.CopyTaskCompletionListener listener)
	{
		listeners.add(listener);
	}
	public void removeListener(Nothingness.CopyTaskCompletionListener listener)
	{
		listeners.remove(listener);
	}
	private void notifyListeners(String currentPath)
	{
		for (Nothingness.CopyTaskCompletionListener listener : listeners)
		{
			listener.setCompletion(state,
					folderItems.size(), numDirectoriesCopied, 
					fileItems.size(), numCopiedFiles,
					totalBytes, numCopiedBytes, 
					currentPath);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private static final int BUFFER_SIZE = 4 * 1024;
	
	private void copy(CopyTaskItem item)
	{
		String srcStr = item.source.toString();
		String desStr = item.destination.toString();
		long maxBytes = item.numBytes;
		long copiedBytes = 0;
		long beginBytes = this.numCopiedBytes;
		
//		File testOutput = new File(desStr).getParentFile();
//		if (!testOutput.exists() && !testOutput.mkdirs())
//		{
//			System.out.println("Unable to copy file " + desStr);
//			return;
//		}
		
		try (	FileChannel in = new FileInputStream(srcStr).getChannel(); 
			FileChannel out = new FileOutputStream(desStr).getChannel())
		{
			ByteBuffer bytebuf = ByteBuffer.allocateDirect(BUFFER_SIZE);

			int bytesCount;
			while ((bytesCount = in.read(bytebuf)) > 0)
			{
				bytebuf.flip();
				out.write(bytebuf);
				bytebuf.clear();
				
				copiedBytes += bytesCount;
				numCopiedBytes = beginBytes + Math.min(maxBytes, copiedBytes);
				
				notifyListeners(srcStr);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

//	FileVisitor<? super Path> visitor = new PrepareFileVisitor();
//	Set<FileVisitOption> options = new TreeSet<>();
//	try
//	{
//		Files.walkFileTree(path, options , Integer.MAX_VALUE, visitor);
//	} catch (IOException e)
//	{
//		e.printStackTrace();
//	}
//	class PrepareFileVisitor implements FileVisitor<Path>
//	{
//		
//		
//		@Override
//		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
//		{
//			return FileVisitResult.CONTINUE;
//		}
//
//		@Override
//		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
//		{
//			System.out.println("Visiting " + file);
//			if (Files.isRegularFile(file))
//			{
//				totalBytes += file.toFile().length();
//				CopyTaskItem item = new CopyTaskItem(file, destination)
//			}
//			else if (Files.isDirectory(file))
//			{
//				
//			}
//			else
//			{
//				System.out.println("Ignoring weird thing at " + file);
//			}
//			return FileVisitResult.CONTINUE;
//		}
//
//		@Override
//		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
//		{
//			return FileVisitResult.CONTINUE;
//		}
//
//		@Override
//		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
//		{
//			return FileVisitResult.CONTINUE;
//		}
//	};
//	
	
	
	    
	    
	    
	    
	    
	    
}
