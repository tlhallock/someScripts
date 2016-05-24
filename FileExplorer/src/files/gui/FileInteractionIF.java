package files.gui;

import files.app.Application;
import files.app.Logger;
import files.model.FileEntry;
import java.nio.file.Files;
import java.nio.file.Path;

public interface FileInteractionIF {

    void doubleClick(FileEntry entry);

    public static abstract class FileOpenInteraction implements FileInteractionIF {

        public void doubleClickFile(FileEntry entry)
        {
            System.out.println("Open " + entry.getPath());
        }

        public abstract void doubleClickDirectory(FileEntry entry);

        @Override
        public final void doubleClick(FileEntry entry) {

            Path p = entry.getPath();

            if (Files.isDirectory(p)) {
                doubleClickDirectory(entry);
            } else if (Files.isRegularFile(p)) {
                doubleClickFile(entry);
            } else {
                Application.getApplication().getLogger().log(Logger.LogLevel.Normal, "Ignoring file " + p);
            }
        }
    }
}
