package comp.model;

import java.awt.AWTException;

import snap.SnapshotManager;

public class Driver
{
	public static void main(String[] args) throws AWTException
	{
		SnapshotManager launchSnapshotManager = SnapshotManager.launchSnapshotManager();
		launchSnapshotManager.showAGui();
	}
}
