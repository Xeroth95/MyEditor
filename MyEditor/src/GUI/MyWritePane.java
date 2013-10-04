package GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.EditorKit;

import util.MyPaneInterface;

public class MyWritePane extends JPanel implements MyPaneInterface {
	private static final long serialVersionUID = 1L;

	JTextPane textPane;
	EditorKit editor;
	MyGui parent;
	File myFile;
	Action[] actions;
	
	public MyWritePane() {
		super();
		this.parent = null;
		this.setLayout(new BorderLayout());
		this.textPane = new JTextPane();
		this.editor = this.textPane.getEditorKit();
		JScrollPane scroll = new JScrollPane(this.textPane);
		this.setupActions();
		this.textPane.setComponentPopupMenu(this.getPopupMenu());
		this.add(scroll, BorderLayout.CENTER);
	}
	
	public MyWritePane(MyGui gui) {
		super();
		this.parent = gui;
		this.setLayout(new BorderLayout());
		this.textPane = new JTextPane();
		this.editor = this.textPane.getEditorKit();
		JScrollPane scroll = new JScrollPane(this.textPane);
		this.setupActions();
		this.textPane.setComponentPopupMenu(this.getPopupMenu());
		this.add(scroll, BorderLayout.CENTER);
	}
	
	public MyWritePane(String s, File myFile) {
		super();
		this.parent = null;
		this.myFile = myFile;
		this.setLayout(new BorderLayout());
		this.textPane = new JTextPane();
		this.textPane.setText(s);
		this.editor = this.textPane.getEditorKit();
		JScrollPane scroll = new JScrollPane(this.textPane);
		this.setupActions();
		this.textPane.setComponentPopupMenu(this.getPopupMenu());
		this.add(scroll, BorderLayout.CENTER);
	}
	
	private void setupActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		
		Action cut = new DefaultEditorKit.CutAction();
		cut.putValue(Action.NAME, "Ausschneiden");
		actions.add(cut);
		
		Action copy = new DefaultEditorKit.CopyAction();
		copy.putValue(Action.NAME, "Kopieren");
		actions.add(copy);
		
		Action paste = new DefaultEditorKit.PasteAction();
		paste.putValue(Action.NAME, "Einfuegen");
		actions.add(paste);
		
		Action selectAll = this.textPane.getActionMap().get(DefaultEditorKit.selectAllAction);
		/*if (selectAll == null) {
			System.out.println("wuut");
		} else
			selectAll.putValue(Action.NAME, "Alles markieren");*/
		actions.add(selectAll);
		
		this.actions = actions.toArray(new Action[0]);
	}
	
	public File getFile() {
		return this.myFile;
	}
	
	public void save() {
		if (this.myFile != null) {
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.myFile), "utf-8"));
				writer.write(this.textPane.getText());
				writer.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	public JMenu getEditorMenu() {
		JMenu toReturn = new JMenu("Werkzeuge");
		Action[] actions = this.editor.getActions();
		
		for (int i = 0; i < actions.length; i++) {
			toReturn.add(actions[i]);
		}
		
		return toReturn;
	}

	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu toReturn = new JPopupMenu();
		for (int i = 0; i < this.actions.length; i++) {
			toReturn.add(actions[i]);
		}
		return toReturn;
	}
}
