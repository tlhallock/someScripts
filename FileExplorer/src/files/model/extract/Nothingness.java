package files.model.extract;

import files.model.extract.Nothingness.TaskState;

public class Nothingness
{

	public static interface CopyTaskCompletionListener
	{
		void setCompletion(
				Nothingness.TaskState currentState,
				int totalFolders, int foldersCompleted,
				int totalFiles, int completedFiles,
				long totalBytes, long numBytes,
				String currentPath);
	}

	static class TimedCopyTaskCompletionListener implements CopyTaskCompletionListener
	{
		long lastTime;
		long wait;
		CopyTaskCompletionListener delegate;
		
		public TimedCopyTaskCompletionListener(long wait, CopyTaskCompletionListener delegate)
		{
			this.wait = wait;
			this.delegate = delegate;
		}
	
		public final void setCompletion(
				Nothingness.TaskState currentState,
				int totalFolders, int foldersCompleted,
				int totalFiles, int completedFiles,
				long totalBytes, long numBytes,
				String currentPath)
		{
			long now = System.currentTimeMillis();
			if (currentState != Nothingness.TaskState.Done && now < lastTime + wait)
			{
				return;
			}
			lastTime = now;
			
			delegate.setCompletion(currentState, totalFolders, foldersCompleted, totalFiles, completedFiles, totalBytes, numBytes, currentPath);
		}
	}

	static abstract class DebugCopyTaskCompletionListener implements CopyTaskCompletionListener
	{
		@Override
		public void setCompletion(Nothingness.TaskState currentState, 
				int totalFolders, int foldersCompleted, 
				int totalFiles, int completedFiles, 
				long totalBytes, long numBytes, 
				String currentPath)
		{
			StringBuilder builder = new StringBuilder();
			
			builder.append("Current state:").append(currentState.name()).append('\n');
			builder.append("\tCurrentPath:").append(currentPath).append('\n');
			builder.append("\tFolders:").append(foldersCompleted).append('/').append(totalFolders).append('\n');
			builder.append("\tFiles:").append(completedFiles).append('/').append(totalFiles).append('\n');
			builder.append("\tBytes:").append(numBytes).append('/').append(totalBytes).append('\n');
			
			handle(builder.toString());
		}
		
		abstract void handle(String string);
	}

	public enum TaskState
	{
		Preparing, CreatingFolders, Copying, Finishing, Done,
	}

	enum LinkCopyOption
	{
		IgnoreLinks, CopyFileLinkContents, LinkDestToSourceAbs, LinkDestToSourceRel,
	}

}
