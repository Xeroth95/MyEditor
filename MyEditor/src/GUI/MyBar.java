package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Test.ButtonTabComponent;

public class MyBar extends JPanel{
	private static final long serialVersionUID = 1L;
	
	MyMenuBar menuBar;
	JTabbedPane tabPane;
	JFrame parent;
	
	public MyBar(JFrame parent) {
		super();
		this.parent = parent;
		this.setLayout(new BorderLayout());
		tabPane = new MyTabbedBar();
		menuBar = new MyMenuBar(parent, tabPane);
		tabPane.addTab("Test", null, new MyWritePanel());
		tabPane.addTab("Test2", null, new MyWritePanel(), "hihi");
		this.add(menuBar, BorderLayout.NORTH);
		this.add(tabPane, BorderLayout.CENTER);
	}
}
