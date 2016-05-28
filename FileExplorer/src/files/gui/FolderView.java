/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.gui;

import files.model.FileEntry;
import javax.swing.JComponent;

/**
 *
 * @author thallock
 */
public interface FolderView
{
    public JComponent getView();
    public FileEntry getCurrentDirectoy();
    
    public void showRootDirectory(FileEntry entry);
    public void showExistingDirectory(FileEntry entry);
    public void goUpToDirectory(FileEntry entry);
    
    public void setInteraction(FileInteractionIF interaction);
}
