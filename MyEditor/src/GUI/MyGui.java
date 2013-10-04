package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.MyMenuPaneFactory;

public class MyGui implements ChangeListener {
	//javax.swing
	private JFrame mainFrame;
	private Container content;
	private JPanel mainPanel;
	
	private File startingDir;
	
	//MyClasses
	//MyBarPane menuBar;
	private MyPackagePane packExplo;
	private MyWritePane console;
	private MyMenuPane menuBar;
	private MyTabbedPane tabPane;
	
	public MyGui(String name, File startingDir) {
		
		this.startingDir = startingDir;
		
		this.mainFrame = new JFrame(name);
		this.content = this.mainFrame.getContentPane();
		this.content.setLayout(new BorderLayout());
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc;
		
		gbc = new GridBagConstraints();
		this.console = new MyWritePane(this);
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
		this.tabPane = new MyTabbedPane(this);
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
		
		gbc = new GridBagConstraints();
		this.packExplo = new MyPackagePane(startingDir, this);
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
		
		this.menuBar = MyMenuPaneFactory.builtDefault(this);
		
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
	
	public MyTabbedPane getTabPane() {
		return this.tabPane;
	}
	
	public MyMenuPane getMenuBar() {
		return this.menuBar;
	}
	
	public MyWritePane getConsole() {
		return this.console;
	}
	
	public MyPackagePane getPackageExplorer() {
		return this.packExplo;
	}
	
	public File getStartingDirectory() {
		return this.startingDir;
	}
}