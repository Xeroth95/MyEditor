package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.MyCompiler;
import util.MyLinker;
import util.MyMenuPaneFactory;

public class MyGui implements ChangeListener {
	//javax.swing
	private JFrame mainFrame;
	private Container content;
	private JPanel mainPanel;
	private String  os;
	private File currentDir, savingFile;
	
	//MyClasses
	//MyBarPane menuBar;
	private MyPackagePane packExplo;
	private MyWritePane console;
	private MyMenuPane menuBar;
	private MyTabbedPane tabPane;
	
	public MyGui(String name) {
		
		this.currentDir = null;
		
		this.mainFrame = new JFrame(name);
		this.content = this.mainFrame.getContentPane();
		this.content.setLayout(new BorderLayout());
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout());
		
		
		this.os = System.getProperty("os.name").toLowerCase();
		//GridBagConstraints gbc;
		
		//gbc = new GridBagConstraints();
		this.console = new MyWritePane(this);
		/*gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.mainPanel.add(new JScrollPane(this.console), gbc);*/
		JScrollPane scroll2 =  new JScrollPane(this.console);
		this.mainPanel.add(scroll2, BorderLayout.SOUTH);
		
		//gbc = new GridBagConstraints();
		this.tabPane = new MyTabbedPane(this);
		/*gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.mainPanel.add(this.tabPane, gbc);*/
		this.mainPanel.add(this.tabPane, BorderLayout.CENTER);
		
		//gbc = new GridBagConstraints();
		this.packExplo = new MyPackagePane(this);
		JScrollPane pane = new JScrollPane(this.packExplo);
		this.mainPanel.add(pane, gbc);*/
		JScrollPane scroll = new JScrollPane(this.packExplo);
		this.mainPanel.add(scroll, BorderLayout.WEST);
		
		this.menuBar = MyMenuPaneFactory.builtDefault(this);
		
		this.content.add(this.menuBar, BorderLayout.NORTH);
		this.content.add(this.mainPanel, BorderLayout.CENTER);

		this.mainFrame.setSize(800, 600);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setVisible(true);
		scroll.setPreferredSize(new Dimension(this.mainFrame.getWidth()/6, 5*this.mainFrame.getHeight()/6));
		scroll2.setPreferredSize(new Dimension(this.mainFrame.getWidth(), this.mainFrame.getHeight()/6));
		
		this.console.setEditable(false);
		MyLinker.init(this);
		System.setOut(new PrintStream(this.console.getOutputStream()));
	}
	
	public void stateChanged(ChangeEvent e) {
		this.tabPane.getSelectedIndex();
	}
	
	public MyTabbedPane getTabPane() {
		return this.tabPane;
	}
	
	public JFrame getFrame() {
		return this.mainFrame;
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
	
	public File getCurrentDirectory() {
		return this.currentDir;
	}
	
	public void setSavingFile(File savingFile) {
		this.savingFile = savingFile;
	}
	
	public File getSavingFile() {
		return this.savingFile;
	}

	public void setCurrentDir(File directory) {
		this.currentDir = directory;
	}
	
	public OutputStream getConsoleStream() {
		return this.console.getOutputStream();
	}
	
	public String getSystem() {
		return this.os;
	}
}
