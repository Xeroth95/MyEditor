package GUI;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.MyMenuPaneFactory;

public class MyGui {
	//javax.swing
	JFrame mainFrame;
	Container mainPanel;
	JTextArea mainField;
	
	//MyClasses
	//MyBarPane menuBar;
	MyPackagePane packExplo;
	MyWritePane console;
	MyMenuPane menuBar;
	MyTabbedPane tabPane;
	
	public MyGui(String name) {
		this.mainFrame = new JFrame(name);
		this.mainPanel = this.mainFrame.getContentPane();
		this.mainPanel.setLayout(new BorderLayout());
		this.mainField = new JTextArea();
		//this.menuBar = new MyBarPane(this.mainFrame);
		this.packExplo = new MyPackagePane(new java.io.File("D:\\Users\\Microsoft Anwender\\Test"));
		this.console = new MyWritePane();
		this.tabPane = new MyTabbedPane();
		this.menuBar = MyMenuPaneFactory.builtDefault(mainFrame, tabPane);
		
		this.tabPane.addTab("Neu", new MyWritePane());
		//this.mainPanel.add(this.menuBar, BorderLayout.CENTER);
		this.mainPanel.add(new JScrollPane(this.console), BorderLayout.SOUTH);
		this.mainPanel.add(this.packExplo, BorderLayout.WEST);
		this.mainPanel.add(this.menuBar, BorderLayout.NORTH);
		this.mainPanel.add(this.tabPane, BorderLayout.CENTER);
		//mainFrame.add(tabPane, BorderLayout.NORTH);
		//mainFrame.setContentPane(mainPanel);
		this.mainFrame.setSize(800, 600);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setVisible(true);
	}
}