package util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
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
		final JMenu fileMenu = new JMenu("Datei");
		{
			MyMenuItem open = new MyMenuItem("Oeffnen...") { @Override public void execute(MyGui gui) {
				final JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Projektdateien", "project");
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile().exists()) {
						File current = ZipCompromiser.unzip(chooser.getSelectedFile());
						gui.setSavingFile(chooser.getSelectedFile());
						gui.getPackageExplorer().open(current, chooser.getSelectedFile().getName());
						setMenuEnabled(fileMenu);
					} else
						return;
				} else 
					return;
			} };
			fileMenu.add(open);
			JMenu add = new JMenu("Neu");
			{
				MyMenuItem project = new MyMenuItem("Projekt") {

					@Override
					public void execute(MyGui gui) {
						File project;
						final JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
						chooser.setDialogTitle("Projekt erstellen");
						chooser.setApproveButtonText("Erstellen");
						chooser.setApproveButtonMnemonic('\n');
						chooser.setApproveButtonToolTipText("Klicken Sie hier um ein Projekt zu erstellen");
						chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						chooser.setAcceptAllFileFilterUsed(false);
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Projektdateien", "project");
						chooser.setFileFilter(filter);
						if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
							if (!chooser.getSelectedFile().exists()) {
								project = chooser.getSelectedFile();
								if (!project.getAbsolutePath().endsWith(".project")) {
									project = new File(project.getAbsolutePath() + ".project");
								}
								try {
									project.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								int option = JOptionPane.showConfirmDialog(null, "Möchten Sie " + chooser.getSelectedFile().getName() + " wirklich dauerhaft löschen ?", "Achtung!", JOptionPane.YES_NO_OPTION);
								if (option == JOptionPane.OK_OPTION)
									project = chooser.getSelectedFile();
								else
									return;
							}
						} else
							return;
						try {
							gui.getPackageExplorer().open(Files.createTempDirectory(null).toFile(), project.getName());
							gui.setSavingFile(project);
							setMenuEnabled(fileMenu);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				};
				MyMenuItem file = new MyMenuItem("Datei") { @Override public void execute(MyGui gui) {
					String result = MyFileFactory.showFileDialog("Eine Datei erstellen...", "Geben sie hier den Namen der Datei an :", gui.getFrame());
					if (result == null || result.equals(""))
						return;
					gui.getPackageExplorer().addFileToCurrent(result);
				} };
				MyMenuItem folder = new MyMenuItem("Ordner") {@Override public void execute(MyGui gui) {
					String result = MyFileFactory.showFolderDialog("Einen Ordner erstellen...", "Geben sie hier den Namen des Ordners an : ", gui.getFrame());
					if (result == null || result.equals(""))
						return;
					gui.getPackageExplorer().addFolderToCurrent(result);
				} };
				add.add(project);
				file.setEnabled(false);
				add.add(file);
				folder.setEnabled(false);
				add.add(folder);
			}
			
			fileMenu.add(add);
			
			fileMenu.addSeparator();
			
			final JMenuItem save = new MyMenuItem("Speichern...") { @Override public void execute(MyGui gui) {
				File toSave = gui.getSavingFile();
				if (toSave == null) {
					final JFileChooser chooser = new JFileChooser(gui.getSavingFile());
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.setAcceptAllFileFilterUsed(false);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Projektdateien", "project");
					chooser.setFileFilter(filter);
					if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						if (!chooser.getSelectedFile().exists()) {
							toSave = chooser.getSelectedFile();
							if (!toSave.getAbsolutePath().endsWith(".project")) {
								toSave = new File(toSave.getAbsolutePath() + ".project");
							}
							try {
								toSave.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							int option = JOptionPane.showConfirmDialog(null, "Möchten Sie " + chooser.getSelectedFile().getName() + " wirklich dauerhaft löschen ?", "Achtung!", JOptionPane.YES_NO_OPTION);
							if (option == JOptionPane.OK_OPTION)
								toSave = chooser.getSelectedFile();
							else
								return;
						}
					} else
						return;
					gui.setSavingFile(toSave);
				}
				gui.getTabPane().saveAll();
				ZipCompromiser.zip(gui.getCurrentDirectory(), gui.getSavingFile());
			} };
			save.setEnabled(false);
			fileMenu.add(save);
			final JMenuItem saveAs = new MyMenuItem("Speichern unter...") { @Override public void execute(MyGui gui) {
				File toSave;
				final JFileChooser chooser = new JFileChooser(gui.getSavingFile());
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Projektdateien", "project");
				chooser.setFileFilter(filter);
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					if (!chooser.getSelectedFile().exists()) {
						toSave = chooser.getSelectedFile();
						if (!toSave.getAbsolutePath().endsWith(".project")) {
							toSave = new File(toSave.getAbsolutePath() + ".project");
						}
						try {
							toSave.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						int option = JOptionPane.showConfirmDialog(null, "Möchten Sie " + chooser.getSelectedFile().getName() + " wirklich dauerhaft löschen ?", "Achtung!", JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.OK_OPTION)
							toSave = chooser.getSelectedFile();
						else
							return;
					}
				} else
					return;
				gui.setSavingFile(toSave);
				gui.getTabPane().saveAll();
				ZipCompromiser.zip(gui.getCurrentDirectory(), gui.getSavingFile());
			} };
			saveAs.setEnabled(false);
			fileMenu.add(saveAs);
			
			fileMenu.addSeparator();
			
			MyMenuItem exit = new MyMenuItem("Beenden...") { @Override public void execute(MyGui gui) {System.exit(0);} };
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
	
	static class MyString {
		String str;
		
		public MyString() {
			this.str = null;
		}
		
		public String get() {
			return this.str;
		}
		
		public void set(String str) {
			this.str = str;
		}
	}
	
	public static void setMenuEnabled(JMenu menu) {
		for (int i = 0; i < menu.getMenuComponentCount(); i++) {
			menu.getMenuComponent(i).setEnabled(true);
			if (menu.getMenuComponent(i) instanceof JMenu) {
				setMenuEnabled((JMenu) menu.getMenuComponent(i));
			}
		}
	}
}

/*
 * JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Speichern unter...");
                    chooser.setDialogType(JFileChooser.SAVE_DIALOG);
                    chooser.addChoosableFileFilter(new FileFilter() {
                        public boolean accept(File f) {
                            if (f.isDirectory())
                                return true;
                            return f.getName().toLowerCase().endsWith(".xml");
                        }

                        public String getDescription() {
                            return "xml-File (*.xml)";
                        }
                    });
 * */
