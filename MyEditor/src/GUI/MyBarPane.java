/*package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import util.MyMenuPaneFactory;

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
		this.menuBar = MyMenuPaneFactory.builtDefault(parent, tabPane);//new MyMenuPane(parent, this.tabPane);
		this.tabPane.addTab("Test", null, new MyWritePane());
		this.tabPane.addTab("Test2", null, new MyWritePane(), "hihi");
	}
	
	public MyMenuPane getMenu() {
		return this.menuBar;
	}
	
	public MyTabbedPane getTabs() {
		return this.tabPane;
	}
}
*/