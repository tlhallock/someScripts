package dockable.test;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dockable.app.DockableApplication;
import dockable.intf.PanelFactory;
import dockable.intf.PanelProperties;
import dockable.tree.LeafNode;
import dockable.tree.RootNode;
import dockable.tree.SplitNode;
import dockable.tree.TabNode;
import dockable.tree.FrameNode;

public class TestDriver {

	
	public static void main(String[] args)
	{
		
//		JFrame frame = new JFrame();
//		frame.setBounds(500,500,500,500);
//		frame.setTitle("Trying");
//		
//		JPanel panel = new JPanel();
//		panel.setBackground(Color.RED);
//		
//		IdkWhereThisGoes.fix(frame.getContentPane(), panel);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		if (true) return;
		
		
		DockableApplication application = new DockableApplication(
				new PanelFactory()
		{

			@Override
			public JComponent create(PanelProperties options)
			{
				switch (options.getCategory())
				{
				case "Editor":
					return new JTextArea();
				case "Nothing":
					JPanel jPanel = new JPanel();
					jPanel.setBackground(Color.YELLOW);
					return jPanel;
				}
				return null;
			}
		});
		
		PanelProperties props = new PanelProperties();
		props.setName("The editor");
		props.setCategory("Editor");
		props.setArgument("file", "none");
		
		LeafNode editorNode = application.createPanel(props);
		
		props = new PanelProperties();
		props.setName("The Nothing");
		props.setCategory("Nothing");
		
		LeafNode nothingNode = application.createPanel(props);

		RootNode root = application.newTreeRootNode();
		
		FrameNode window = root.createWindow("The app");
		
		
		SplitNode split = application.createSplit();
		window.setContent(split);
		split.setDivider(100);
		
		split.setLeft(editorNode);
		
		TabNode createTab = application.createTab();
		createTab.addTab(nothingNode);
		split.setRight(createTab);
		
		application.setTree(root);
		
		application.show();
		
		application.showManagerDisplay();
	}
}
