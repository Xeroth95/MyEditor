package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
		currentMenu = new JMenu("Werkzeuge");
		menuItems.add(new JMenuItem("Neue Datei..."));
		menuItems.get(0).addActionListener(this);
		menuItems.add(new JMenuItem("�ffnen..."));
		menuItems.get(1).addActionListener(this);
		menuItems.add(new JMenuItem("F.A.Q."));
		menuItems.get(2).addActionListener(this);
		menuItems.add(new JMenuItem("�ber..."));
		menuItems.get(3).addActionListener(this);
		this.add(menus.get(0));
		this.add(menus.get(1));
		menus.get(0).add(menuItems.get(0));
		menus.get(0).add(menuItems.get(1));
		menus.get(0).add(DefaultEditorKit.cutAction);
		menus.get(1).add(menuItems.get(2));
		menus.get(1).add(menuItems.get(3));
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
