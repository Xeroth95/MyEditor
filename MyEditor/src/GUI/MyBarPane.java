package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MyBarPane extends JPanel{
	private static final long serialVersionUID = 1L;
	
	MyMenuPane menuBar;
	MyTabbedPane tabPane;
	JFrame parent;
	
	public MyBarPane(JFrame parent) {
		super();
		this.parent = parent;
		this.setLayout(new BorderLayout());
		this.tabPane = new MyTabbedPane();
		this.menuBar = new MyMenuPane(parent, this.tabPane);
		this.tabPane.addTab("Test", null, new MyWritePane());
		this.tabPane.addTab("Test2", null, new MyWritePane(), "hihi");
		this.add(this.menuBar, BorderLayout.NORTH);
		this.add(this.tabPane, BorderLayout.CENTER);
	}
}
