package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import GUI.MyTabbedPane;
import GUI.MyWritePane;

public class FileOpener {
	
	MyTabbedPane tabPane;
	BufferedReader reader;
	
	public FileOpener(MyTabbedPane tabPane) {
		this.tabPane = tabPane;
	}
	
	public void openAll (File dir) {
		if (dir.isFile()) {
			try {
				this.reader = new BufferedReader(new FileReader(dir));
				String text = "";
				for (int c = this.reader.read(); c != -1; c = this.reader.read()) {
					text += (char) c;
				}
				System.out.println(dir.getName() + " : \n" + text + "\n");
				this.tabPane.addTab(dir.getName(), new MyWritePane(text, dir));
				this.reader.close();
			} catch (FileNotFoundException fne) {
				fne.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				openAll(files[i]);
			}
		}
	}
}
