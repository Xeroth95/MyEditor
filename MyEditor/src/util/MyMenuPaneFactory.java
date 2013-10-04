package util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import GUI.MyGui;
import GUI.MyMenuPane;
import GUI.MyTabbedPane;
import GUI.MyWritePane;

public class MyMenuPaneFactory {
	public static MyMenuPane builtDefault(MyGui gui) {
		MyMenuPane toReturn = new MyMenuPane(gui);
		
		JMenu fileMenu = new JMenu("Datei");
		{
			MyMenuItem open = new MyMenuItem("Oeffnen...") { @Override public void execute(MyGui gui) {
				final JFileChooser chooser = new JFileChooser(gui.getStartingDirectory());
				//chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("C- und Assemblerdateien", "c", "asm");
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					if (!chooser.getSelectedFile().exists())
						JOptionPane.showMessageDialog(null, "Keine Datei ausgewählt!", "Fehler!", JOptionPane.ERROR_MESSAGE);
					else
						gui.getTabPane().addWritePane(chooser.getSelectedFile());
				}
			} };
			fileMenu.add(open);
			JMenu add = new JMenu("Hinzufuegen");
			{
				MyMenuItem file = new MyMenuItem("Datei") { @Override public void execute(MyGui gui) {
					String name = JOptionPane.showInputDialog("Geben sie hier den Namen der Datei an : ");
					gui.getPackageExplorer().addToCurrent(name);
				} };
				MyMenuItem folder = new MyMenuItem("Ordner") {@Override public void execute(MyGui gui) {
					String name = JOptionPane.showInputDialog("Geben sie hier den Namen der Datei an : ");
					gui.getPackageExplorer().addToCurrent(name);
				} };
				add.add(file);
				add.add(folder);
			}
			
			fileMenu.add(add);
			
			fileMenu.addSeparator();
			
			MyMenuItem save = new MyMenuItem("Speichern...") { @Override public void execute(MyGui gui) {gui.getTabPane().saveAll();} };
			fileMenu.add(save);
			MyMenuItem saveAs = new MyMenuItem("Speichern unter...") { @Override public void execute(MyGui gui) {} };
			fileMenu.add(saveAs);
			
			fileMenu.addSeparator();
			
			MyMenuItem exit = new MyMenuItem("Beenden...") { @Override public void execute(MyGui gui) {} };
			fileMenu.add(exit);
		}
		toReturn.addMenu(fileMenu);
		
		return toReturn;
	}
	
	static class myAction extends AbstractAction {

		public myAction() {
			super();
			this.putValue(Action.NAME, "Neues Dokument...");
		}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println(System.getProperty("user.dir"));
		}
		
	}
}
