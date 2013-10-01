package GUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyWritePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public MyWritePanel() {
		super();
		this.add(new JScrollPane(new JTextArea()));
	}
}
