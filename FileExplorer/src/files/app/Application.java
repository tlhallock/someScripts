package files.app;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import files.gui.RootViewExtensions;
import files.util.SinglePanelFrame;


public final class Application
{
	private ExplorerSettings settings;
	private Logger logger;
	private ExtensionManager knownExtensions;
	private ColorSelector selector;
        private TemplateManager templates;
        private SystemInteractions system;
	
	
	private Application() {}
	
	public ExplorerSettings getSettings()
	{
		return settings;
	}
	public Logger getLogger()
	{
		return logger;
	}
	public ExtensionManager getKnownFileTypes() {
		return knownExtensions;
	}
	public ColorSelector getColorSelector()
        {
		return selector;
	}
        public SystemInteractions getSystem()
        {
            return system;
        }

    public TemplateManager getTemplates()
    {
        return templates;
    }
	
	
	
	
	static Application app;
	public static Application getApplication()
	{
		return app;
	}
	public static void launchApplication()
	{
		if (app != null)
			return;
		
		setFonts();
		
		app = new Application();
		app.logger = new Logger();
		app.settings = new ExplorerSettings();
		app.knownExtensions = ExtensionManager.load();
		app.selector = ColorSelector.load();
                app.templates = TemplateManager.load();
                app.system = SystemInteractions.load();
	}

	
	
	private static void setFonts()
	{
//		setUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.ITALIC,12));
		FontUIResource font = new javax.swing.plaf.FontUIResource("Serif", Font.PLAIN, 25);
		UIManager.put("Button.font", font);
		UIManager.put("ToggleButton.font", font);
		UIManager.put("RadioButton.font", font);
		UIManager.put("CheckBox.font", font);
		UIManager.put("ColorChooser.font", font);
		UIManager.put("ComboBox.font", font);
		UIManager.put("Label.font", font);
		UIManager.put("List.font", font);
		UIManager.put("MenuBar.font", font);
		UIManager.put("MenuItem.font", font);
		UIManager.put("RadioButtonMenuItem.font", font);
		UIManager.put("CheckBoxMenuItem.font", font);
		UIManager.put("Menu.font", font);
		UIManager.put("PopupMenu.font", font);
		UIManager.put("OptionPane.font", font);
		UIManager.put("Panel.font", font);
		UIManager.put("ProgressBar.font", font);
		UIManager.put("ScrollPane.font", font);
		UIManager.put("Viewport.font", font);
		UIManager.put("TabbedPane.font", font);
		UIManager.put("Table.font", font);
		UIManager.put("TableHeader.font", font);
		UIManager.put("TextField.font", font);
		UIManager.put("PasswordField.font", font);
		UIManager.put("TextArea.font", font);
		UIManager.put("TextPane.font", font);
		UIManager.put("EditorPane.font", font);
		UIManager.put("TitledBorder.font", font);
		UIManager.put("ToolBar.font", font);
		UIManager.put("ToolTip.font", font);
		UIManager.put("Tree.font", font);
	}

	public RootViewExtensions launchFileTypeOptions(Point p)
	{
		RootViewExtensions folderView1 = new RootViewExtensions();
		SinglePanelFrame.showPanel(folderView1, new Rectangle(p.x + 50,p.y + 50, 1000, 1000), "viewer1");
		return folderView1;
	}
}
