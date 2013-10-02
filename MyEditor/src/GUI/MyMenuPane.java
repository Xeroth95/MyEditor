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
	JMenuItem[] menuItems;
	JFrame parent;
	JTabbedPane tabPane;
	
	JMenu currentMenu;
	
	public MyMenuPane(JFrame parent, JTabbedPane tabPane) {
		super();
		this.parent = parent;
		this.tabPane = tabPane;
		menus = new ArrayList<JMenu>();
		menuItems = new JMenuItem[4];
		menus.add(new JMenu("Datei"));
		menus.add(new JMenu("Hilfe"));
		currentMenu = new JMenu("Werkzeuge");
		menuItems[0] = new JMenuItem("Neue Datei...");
		menuItems[0].addActionListener(this);
		menuItems[1] = new JMenuItem("�ffnen...");
		menuItems[1].addActionListener(this);
		menuItems[2] = new JMenuItem("F.A.Q.");
		menuItems[2].addActionListener(this);
		menuItems[3] = new JMenuItem("�ber...");
		menuItems[3].addActionListener(this);
		this.add(menus.get(0));
		this.add(menus.get(1));
		menus.get(0).add(menuItems[0]);
		menus.get(0).add(menuItems[1]);
		menus.get(0).add(DefaultEditorKit.cutAction);
		menus.get(1).add(menuItems[2]);
		menus.get(1).add(menuItems[3]);
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
	}
}
