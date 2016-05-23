package dockable.test;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Collections;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dockable.app.DockableApplication;
import dockable.intf.PanelFactory;
import dockable.intf.PanelProperties;
import dockable.tree.LeafNode;
import dockable.tree.SplitNode;
import dockable.tree.TabNode;
import dockable.tree.TreeCreator;

public class TestDriver {

	
	public static void main(String[] args)
	{
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
		
		TreeCreator creator = new TreeCreator(application);
		
		PanelProperties props = new PanelProperties();
		props.setName("The editor");
		props.setCategory("Editor");
		props.setArgument("file", "none");
		
		LeafNode editorNode = creator.createPanel(props);
		
		props = new PanelProperties();
		props.setName("The Nothing");
		props.setCategory("Nothing");
		
		LeafNode nothingNode = creator.createPanel(props);
		

		TabNode createTab = creator.createTab(Collections.singleton(nothingNode));
		SplitNode split = creator.createSplit(editorNode, createTab, 100, true);
		
		creator.createWindow("The app", split, new Rectangle(500,500,500,500), JFrame.EXIT_ON_CLOSE);
		creator.commit();
		
		application.setVisible(true);
		
		application.showManagerDisplay();
	}
}
