package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MyMenuBar extends JMenuBar implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JMenu[] menus;
	JMenuItem[] menuItems;
	JFrame parent;
	
	public MyMenuBar(JFrame parent) {
		super();
		this.parent = parent;
		menus = new JMenu[2];
		menuItems = new JMenuItem[4];
		menus[0] = new JMenu("Datei");
		menus[1] = new JMenu("Hilfe");
		menuItems[0] = new JMenuItem("Neue Datei : ");
		menuItems[0].addActionListener(this);
		menuItems[1] = new JMenuItem("Öffnen : ");
		menuItems[1].addActionListener(this);
		menuItems[2] = new JMenuItem("F.A.Q.");
		menuItems[2].addActionListener(this);
		menuItems[3] = new JMenuItem("über...");
		menuItems[3].addActionListener(this);
		this.add(menus[0]);
		this.add(menus[1]);
		menus[0].add(menuItems[0]);
		menus[0].add(menuItems[1]);
		menus[1].add(menuItems[2]);
		menus[1].add(menuItems[3]);
	}
	
	@Override
	public void actionPerformed(ActionEvent object) {
		if (object.getSource() == menuItems[0]){
            System.out.println("öffnen wurde angeklickt");
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
	}
}
