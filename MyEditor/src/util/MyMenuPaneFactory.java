package util;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import GUI.MyMenuPane;
import GUI.MyTabbedPane;
import GUI.MyWritePane;

public class MyMenuPaneFactory {
	public static MyMenuPane builtDefault(JFrame parent, MyTabbedPane tabPane) {
		MyMenuPane toReturn = new MyMenuPane(parent, tabPane);
		
		JMenu fileMenu = new JMenu("Datei");
		{
			MyMenuItem open = new MyMenuItem("Oeffnen...") { @Override public void execute(Component... components) {} };
			fileMenu.add(open);
			JMenu add = new JMenu("Hinzufuegen");
			{
				MyMenuItem file = new MyMenuItem("Datei") { @Override public void execute(Component... components) {} };
				MyMenuItem folder = new MyMenuItem("Ordner") {@Override public void execute(Component... components) {} };
				add.add(file);
				add.add(folder);
			}
			
			fileMenu.add(add);
			
			fileMenu.addSeparator();
			
			MyMenuItem save = new MyMenuItem("Speichern...") { @Override public void execute(Component... components) {} };
			fileMenu.add(save);
			MyMenuItem saveAs = new MyMenuItem("Speichern unter...") { @Override public void execute(Component... components) {} };
			fileMenu.add(saveAs);
			
			fileMenu.addSeparator();
			
			MyMenuItem exit = new MyMenuItem("Beenden...") { @Override public void execute(Component... components) {} };
			fileMenu.add(exit);
		}
		toReturn.addMenu(fileMenu);
		
		return toReturn;
	}
}
