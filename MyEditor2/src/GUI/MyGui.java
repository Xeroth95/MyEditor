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
	MyBar menuBar;
	
	public MyGui(String name) {
		mainFrame = new JFrame(name);
		mainPanel = new JPanel();
		mainField = new JTextArea();
		menuBar = new MyBar(mainFrame);
		mainFrame.add(menuBar, BorderLayout.NORTH);
		//mainFrame.add(tabPane, BorderLayout.NORTH);
		mainFrame.add(new JScrollPane(mainField), BorderLayout.CENTER);
		//mainFrame.setContentPane(mainPanel);
		mainFrame.setSize(400, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
}