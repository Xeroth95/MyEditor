package GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import util.FileOpener;

public class MyPackagePane extends JPanel implements TreeSelectionListener {
	private static final long serialVersionUID = 1L;

	JTree packExplo;
	File startingDir;
	MyGui parent;
	
	public MyPackagePane(File directory, MyGui gui) {
		super();
		this.parent = gui;
		this.setLayout(new BorderLayout());
		this.startingDir = directory;
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(directory.getName());
		this.createNodes(top, directory);
		this.openTabs();
		this.packExplo = new JTree(top);
		this.packExplo.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.packExplo.addTreeSelectionListener(this);
		this.add(packExplo, BorderLayout.CENTER);
	}
	
	private void createNodes(DefaultMutableTreeNode parent, File dir) {
		if (!dir.exists()) {
			System.out.println("There is no " + dir.getAbsolutePath() + "!!!");
			return;
		}
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				parent.add(new DefaultMutableTreeNode(f.getName(), false));
			} else {
				DefaultMutableTreeNode newParent = new DefaultMutableTreeNode(f.getName());
				parent.add(newParent);
				createNodes(newParent, f);
			}
		}
	}
	
	private void openTabs() {
		FileOpener opener = new FileOpener(parent.getTabPane());
		opener.openAll(this.startingDir);
	}
	
	public void valueChanged(TreeSelectionEvent e) {
		TreePath treePath = e.getPath();
		String path = this.startingDir.getAbsolutePath();
		for (int i = 1; i < treePath.getPathCount(); i++) {
			path += "\\" + treePath.getPathComponent(i).toString();
			treePath = treePath.getParentPath();
		}
		File toOpen = new File(path);
		if (toOpen.isDirectory()) {
			return;
		} else {
			
		}
		
	}
	
	/*@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, this.WIDTH, this.HEIGHT);
	}*/
}
