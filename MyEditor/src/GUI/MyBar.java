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
		tabPane = new JTabbedPane();
		menuBar = new MyMenuBar(parent, tabPane);
		tabPane.addTab("Test", new MyWritePanel());
		tabPane.addTab("Test2", new MyWritePanel());
		for (int i = 0; i < tabPane.getTabCount(); i++) {
			tabPane.setTabComponentAt(i, new ButtonTabComponent(tabPane));
		}
		this.add(menuBar, BorderLayout.NORTH);
		this.add(tabPane, BorderLayout.CENTER);
	}
}
