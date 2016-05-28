/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files.app;

import files.model.FileEntry;

/**
 *
 * @author thallock
 */
public class SystemInteractions
{
    protected SystemInteractions() {}
    
    
    
    public void launchTerminal(FileEntry directory)
    {
        System.out.println("Launching terminal at " + directory);
    }

    static SystemInteractions load() {
        return new SystemInteractions();
    }
}
