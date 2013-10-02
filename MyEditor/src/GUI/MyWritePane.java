package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.EditorKit;

public class MyWritePane extends JPanel {
	private static final long serialVersionUID = 1L;

	JTextPane textPane;
	EditorKit editor;
	
	public MyWritePane() {
		super();
		this.setLayout(new BorderLayout());
		this.textPane = new JTextPane();
		this.editor = textPane.getEditorKit();
		this.add(new JScrollPane(this.textPane), BorderLayout.CENTER);
	}
}
