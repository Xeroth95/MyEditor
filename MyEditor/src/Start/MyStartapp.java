package Start;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.MyFileFactory;

import GUI.MyGui;

public class MyStartapp {
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Projektdateien", "project");
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (!chooser.getSelectedFile().exists())
				JOptionPane.showMessageDialog(null, "Keine Datei ausgewählt!", "Fehler!", JOptionPane.ERROR_MESSAGE);
			else
				new MyGui("Test", new File("D:\\Users\\Microsoft Anwender\\Test"));
				//new MyGui("Test", chooser.getSelectedFile());
		}
		new MyGui("Test", new File("D:\\Users\\Microsoft Anwender\\Test"));
	}
}
