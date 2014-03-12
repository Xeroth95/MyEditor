package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

import util.MyMenuItem;
import util.MyPaneInterface;

public class MyWritePane extends JPanel implements MyPaneInterface {
	private static final long serialVersionUID = 1L;

	private JTextPane textPane;
	private EditorKit editor;
	private MyGui parent;
	private File myFile;
	private JMenuItem[] actions;
	private OutputStream os;
	
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
		this.os = new OutputStream() {

			@Override
		      public void write(final int b) throws IOException {
		        update(String.valueOf((char) b));
		      }

		      @Override
		      public void write(byte[] b, int off, int len) throws IOException {
		    	  update(new String(b, off, len));
		      }

		      @Override
		      public void write(byte[] b) throws IOException {
		        write(b, 0, b.length);
		      }
			
		};
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
		this.os = new OutputStream() {

			@Override
		      public void write(final int b) throws IOException {
		        update(String.valueOf((char) b));
		      }

		      @Override
		      public void write(byte[] b, int off, int len) throws IOException {
		    	  update(new String(b, off, len));
		      }

		      @Override
		      public void write(byte[] b) throws IOException {
		        write(b, 0, b.length);
		      }
			
		};
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
		this.os = new OutputStream() {

			@Override
		      public void write(final int b) throws IOException {
		        update(String.valueOf((char) b));
		      }

		      @Override
		      public void write(byte[] b, int off, int len) throws IOException {
		    	  update(new String(b, off, len));
		      }

		      @Override
		      public void write(byte[] b) throws IOException {
		        write(b, 0, b.length);
		      }
			
		};
	}
	
	private void setupActions() {
		ArrayList<JMenuItem> actions = new ArrayList<JMenuItem>();
		
		Action cut = new DefaultEditorKit.CutAction();
		cut.putValue(Action.NAME, "Ausschneiden");
		actions.add(new JMenuItem(cut));
		
		Action copy = new DefaultEditorKit.CopyAction();
		copy.putValue(Action.NAME, "Kopieren");
		actions.add(new JMenuItem(copy));
		
		Action paste = new DefaultEditorKit.PasteAction();
		paste.putValue(Action.NAME, "Einfuegen");
		actions.add(new JMenuItem(paste));
		
		Action save = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyWritePane.this.save();
			}
			
		};
		save.putValue(Action.NAME, "Speichern");
		actions.add(new JMenuItem(save));
		
		AbstractAction selectAll = (AbstractAction) this.textPane.getActionMap().get(DefaultEditorKit.selectAllAction);
		
		actions.add(new JMenuItem(selectAll));
		//TODO clearing wtf that is
		
		this.actions = actions.toArray(new JMenuItem[0]);
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
	
	public void update(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        Document doc = MyWritePane.this.textPane.getDocument();
		        try {
		          doc.insertString(doc.getLength(), text, null);
		        } catch (BadLocationException e) {
		          throw new RuntimeException(e);
		        }
		        MyWritePane.this.textPane.setCaretPosition(doc.getLength() - 1);
		      }
		    });
	}
	
	public OutputStream getOutputStream() {
		return this.os;
	}
	
	public void setEditable(boolean enable) {
		this.textPane.setEditable(enable);
	}
}
