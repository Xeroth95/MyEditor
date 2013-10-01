package GUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyWritePanel extends JPanel {
	public MyWritePanel() {
		super();
		this.add(new JScrollPane(new JTextArea()));
	}
}
