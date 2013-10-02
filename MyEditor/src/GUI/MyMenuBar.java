package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.text.DefaultEditorKit;

public class MyMenuBar extends JMenuBar implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JMenu[] menus;
	JMenuItem[] menuItems;
	JFrame parent;
	JTabbedPane tabPane;
	
	JMenu currentMenu;
	
	public MyMenuBar(JFrame parent, JTabbedPane tabPane) {
		super();
		this.parent = parent;
		this.tabPane = tabPane;
		menus = new JMenu[2];
		menuItems = new JMenuItem[4];
		menus[0] = new JMenu("Datei");
		menus[1] = new JMenu("Hilfe");
		currentMenu = new JMenu("Werkzeuge");
		menuItems[0] = new JMenuItem("Neue Datei...");
		menuItems[0].addActionListener(this);
		menuItems[1] = new JMenuItem("�ffnen...");
		menuItems[1].addActionListener(this);
		menuItems[2] = new JMenuItem("F.A.Q.");
		menuItems[2].addActionListener(this);
		menuItems[3] = new JMenuItem("�ber...");
		menuItems[3].addActionListener(this);
		this.add(menus[0]);
		this.add(menus[1]);
		menus[0].add(menuItems[0]);
		menus[0].add(menuItems[1]);
		menus[0].add(DefaultEditorKit.cutAction);
		menus[1].add(menuItems[2]);
		menus[1].add(menuItems[3]);
	}
	
	@Override
	public void actionPerformed(ActionEvent object) {
		if (object.getSource() == menuItems[0]){
            tabPane.addTab("Neu", new MyWritePanel());
       }
       if (object.getSource() == menuItems[1]){
            System.out.println("beenden wurde angeklickt");
       }
       if (object.getSource() == menuItems[2]){
            System.out.println("faq wurde angeklickt");
       }
       if (object.getSource() == menuItems[3]){
            System.out.println("über wurde angeklickt");
       }
       if (object.getSource() == menus[0]) {
    	   System.out.println("Interessant!");
       }
	}
}
