package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyGui {
	//javax.swing
	JFrame mainFrame;
	JPanel mainPanel;
	JTextArea mainField;
	
	//MyClasses
	MyBarPane menuBar;
	MyPackagePane packExplo;
	
	public MyGui(String name) {
		mainFrame = new JFrame(name);
		mainFrame.setLayout(new BorderLayout());
		mainPanel = new JPanel();
		mainField = new JTextArea();
		menuBar = new MyBarPane(mainFrame);
		mainFrame.add(menuBar, BorderLayout.CENTER);
		this.packExplo = new MyPackagePane();
		mainFrame.add(this.packExplo, BorderLayout.WEST);
		//mainFrame.add(tabPane, BorderLayout.NORTH);
		//mainFrame.setContentPane(mainPanel);
		mainFrame.setSize(400, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
}