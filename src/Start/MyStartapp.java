package Start;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.MyFileFactory;

import GUI.MyGui;

public class MyStartapp {
	public static void main(String[] args) {
		new MyGui("Mein Editor");
	}
}
