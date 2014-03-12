package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import GUI.MyTabbedPane;
import GUI.MyWritePane;

public class MyFileOpener {
	
	MyTabbedPane tabPane;
	
	public MyFileOpener(MyTabbedPane tabPane) {
		this.tabPane = tabPane;
	}
	
	public void openAll (File dir) {
		if (dir.isFile()) {
			this.tabPane.addWritePane(dir);
		} else {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				openAll(files[i]);
			}
		}
	}
}
