package snap;

import java.awt.AWTException;

public class Test {

	public static void main(String[] args) throws AWTException
	{
		SnapshotManager launchSnapshotManager = SnapshotManager.launchSnapshotManager();
		launchSnapshotManager.showAGui();
	}
}
