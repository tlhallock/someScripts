/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.gui;

import files.model.FileEntry;
import java.nio.file.Files;
import java.util.LinkedList;

/**
 *
 * @author thallock
 */
public class FileHistory
{
    private LinkedList<FileEntry> visited = new LinkedList<>();
    
    public void push(FileEntry entry)
    {
        synchronized (visited)
        {
            visited.addLast(entry);
        }
    }
    
    public FileEntry pop()
    {
        synchronized (visited)
        {
            while (!visited.isEmpty())
            {
                FileEntry entry = visited.removeLast();
                if (!Files.exists(entry.getPath()))
                    continue;
                return entry;
            }
        }
        return null;
    }
    
    public boolean isEmpty()
    {
        synchronized (visited)
        {
            if (visited.isEmpty())
                return true;
            return visited.stream().anyMatch(x -> Files.exists(x.getPath()));
        }
    }
}
