package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MyPackagePane extends JPanel{
	private static final long serialVersionUID = 1L;

	public MyPackagePane() {
		super();
		this.setLayout(new BorderLayout());
		this.add(new javax.swing.JScrollPane(new javax.swing.JTextArea()), BorderLayout.CENTER);
	}
	
	
}
