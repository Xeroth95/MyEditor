package GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class MyPackagePane extends JPanel{
	private static final long serialVersionUID = 1L;

	JTree packExplo;
	
	public MyPackagePane(File directory) {
		super();
		this.setLayout(new BorderLayout());
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(directory.getName());
		createNodes(top, directory);
		this.packExplo = new JTree(top);
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
	
	/*@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, this.WIDTH, this.HEIGHT);
	}*/
}
