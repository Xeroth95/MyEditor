package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class MyWritePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public MyWritePanel() {
		super();
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(new JTextPane()), BorderLayout.CENTER);
	}
}
