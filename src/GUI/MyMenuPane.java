package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.management.InstanceNotFoundException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.MenuElement;

import util.MyMenuItem;

public class MyMenuPane extends JMenuBar implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	MyGui parent;
	JTabbedPane tabPane;
	
	JMenu currentMenu;
	
	public MyMenuPane(MyGui gui) {
		super();
		this.parent = gui;
		this.tabPane = gui.getTabPane();
	}
	
	public void addMenu(JMenu menu) {
		this.add(menu);
		this.setActionListener(menu);
	}
	
	public void addMenuItem(JMenu menu, JMenuItem item) {
		
	}
	
	public void addMenuItem(int index, JMenuItem item) {
		JMenu toChange = this.getMenu(index);
		toChange.add(item);
	}
	
	public void setActionListener(JMenu menu) {
		Component[] components = menu.getMenuComponents();
		for (Component c : components) {
			if (c instanceof JMenu) {
				this.setActionListener((JMenu) c);
			} else if (c instanceof JMenuItem) {
				((JMenuItem) c).addActionListener(this);
			}
		}
	}
	
	public void actionPerformed(ActionEvent object) {
		Object o = object.getSource();
		if (o instanceof MyMenuItem) {
			((MyMenuItem) o).execute(this.parent);
		}
	}
}