package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.management.InstanceNotFoundException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.text.DefaultEditorKit;

public class MyMenuPane extends JMenuBar implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	ArrayList<JMenu> menus;
	ArrayList<JMenuItem> menuItems;
	JFrame parent;
	JTabbedPane tabPane;
	
	JMenu currentMenu;
	
	public MyMenuPane(JFrame parent, JTabbedPane tabPane) {
		super();
		this.parent = parent;
		this.tabPane = tabPane;
		menus = new ArrayList<JMenu>();
		menuItems = new ArrayList<JMenuItem>();
		menus.add(new JMenu("Datei"));
		menus.add(new JMenu("Hilfe"));
		menus.add(new JMenu("Werkzeuge"));
		menus.add(new JMenu("Optionen"));
		menuItems.add(new JMenuItem("Oeffen..."));
		menuItems.get(0).addActionListener(this);
		menuItems.add(new JMenuItem("Neue Datei..."));
		menuItems.get(1).addActionListener(this);
		menuItems.add(new JMenu("Hinzufuegen"));
		menuItems.get(2).addActionListener(this);
		menuItems.add(new JMenuItem("Ueber..."));
		menuItems.get(3).addActionListener(this);
		for (int i = 0; i < menus.size(); i++) {
			this.add(menus.get(i));
		}
		menus.get(0).add(menuItems.get(0));
		menus.get(0).add(menuItems.get(1));
		menus.get(0).add(DefaultEditorKit.cutAction);
		menus.get(0).add(menuItems.get(2));
		menus.get(1).add(menuItems.get(3));
		menuItems.get(2).add(new JMenuItem("Datei..."));
		menuItems.get(2).add(new JMenuItem("Ordner..."));
	}
	
	public void addMenu(JMenu menu) {
		this.menus.add(menu);
	}
	
	public void addMenus(JMenu[] menus) {
		for (int i = 0; i < menus.length; i++) {
			this.menus.add(menus[i]);
		}
	}
	
	public void addMenuItem(JMenu menu, JMenuItem item) throws InstanceNotFoundException {
		int index = this.menus.indexOf(menu);
		if (index > 0)
			this.menus.get(index).add(item);
		else
			throw new InstanceNotFoundException("No such Menu");
	}
	
	public void addMenuItems(JMenu menu, JMenuItem[] items) throws InstanceNotFoundException {
		int index = this.menus.indexOf(menu);
		if (index > 0)
			for (int i = 0; i < items.length; i++)
				this.menus.get(index).add(items[i]);
		else
			throw new InstanceNotFoundException("No such Menu");
	}
	
	@Override
	public void actionPerformed(ActionEvent object) {
		if (object.getSource() == menuItems.get(0)){
            tabPane.addTab("Neu", new MyWritePanel());
       }
       if (object.getSource() == menuItems.get(1)){
            System.out.println("beenden wurde angeklickt");
       }
       if (object.getSource() == menuItems.get(3)){
            System.out.println("faq wurde angeklickt");
       }
       if (object.getSource() == menuItems.get(4)){
            System.out.println("über wurde angeklickt");
       }
	}
}
