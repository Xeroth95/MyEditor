package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.MyMenuPaneFactory;

public class MyGui implements ChangeListener {
	//javax.swing
	JFrame mainFrame;
	Container content;
	JPanel mainPanel;
	
	
	//MyClasses
	//MyBarPane menuBar;
	MyPackagePane packExplo;
	MyWritePane console;
	MyMenuPane menuBar;
	MyTabbedPane tabPane;
	
	public MyGui(String name) {
		
		this.mainFrame = new JFrame(name);
		this.content = this.mainFrame.getContentPane();
		this.content.setLayout(new BorderLayout());
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc;
		
		gbc = new GridBagConstraints();
		this.packExplo = new MyPackagePane(new java.io.File("D:\\Users\\Microsoft Anwender\\Test"));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 0.1;
		gbc.weighty = 0.9;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.mainPanel.add(this.packExplo, gbc);
		
		gbc = new GridBagConstraints();
		this.console = new MyWritePane();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.mainPanel.add(new JScrollPane(this.console), gbc);
		
		gbc = new GridBagConstraints();
		this.tabPane = new MyTabbedPane();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 0.9;
		gbc.weighty = 0.9;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.mainPanel.add(this.tabPane, gbc);
		
		this.menuBar = MyMenuPaneFactory.builtDefault(mainFrame, tabPane);
		this.tabPane.addTab("Neu", new MyWritePane());
		
		//this.content.add(new JScrollPane(this.console), BorderLayout.SOUTH);
		//this.content.add(this.packExplo, BorderLayout.WEST);
		this.content.add(this.menuBar, BorderLayout.NORTH);
		//this.content.add(this.tabPane, BorderLayout.CENTER);
		this.content.add(this.mainPanel, BorderLayout.CENTER);

		this.mainFrame.setSize(800, 600);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setVisible(true);
	}
	
	public void stateChanged(ChangeEvent e) {
		this.tabPane.getSelectedIndex();
	}
}