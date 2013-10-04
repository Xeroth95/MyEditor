package GUI;

import java.awt.BorderLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.EditorKit;

public class MyWritePane extends JPanel {
	private static final long serialVersionUID = 1L;

	JTextPane textPane;
	EditorKit editor;
	MyGui parent;
	File myFile;
	
	public MyWritePane() {
		super();
		this.parent = null;
		this.setLayout(new BorderLayout());
		this.textPane = new JTextPane();
		this.editor = this.textPane.getEditorKit();
		this.add(new JScrollPane(this.textPane), BorderLayout.CENTER);
	}
	
	public MyWritePane(MyGui gui) {
		super();
		this.parent = gui;
		this.setLayout(new BorderLayout());
		this.textPane = new JTextPane();
		this.editor = this.textPane.getEditorKit();
		this.add(new JScrollPane(this.textPane), BorderLayout.CENTER);
	}
	
	public MyWritePane(String s, File myFile) {
		super();
		this.parent = null;
		this.myFile = myFile;
		this.setLayout(new BorderLayout());
		this.textPane = new JTextPane();
		this.textPane.setText(s);
		this.editor = this.textPane.getEditorKit();
		this.add(new JScrollPane(this.textPane), BorderLayout.CENTER);
	}
	
	public void save() {
		if (this.myFile != null) {
			try {
				new BufferedWriter(new FileWriter(this.myFile)).write(this.textPane.getText());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
