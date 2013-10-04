package GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import util.MyFileOpener;

public class MyPackagePane extends JPanel implements MouseListener {
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
		this.packExplo.addMouseListener(this);
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
	
	private File getFileFromTreePath(TreePath treePath) {
		String path = this.startingDir.getAbsolutePath();
		for (int i = 1; i < treePath.getPathCount(); i++) {
			path += "\\" + treePath.getPathComponent(i);
		}
		File toReturn = new File(path);
		if (toReturn.exists())
			return toReturn;
		return null;
	}
	
	private void openTabs() {
		/*MyFileOpener opener = new MyFileOpener(parent.getTabPane());
		opener.openAll(this.startingDir);*/
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		int selRow = this.packExplo.getRowForLocation(e.getX(), e.getY());
		if (selRow != -1) {
			TreePath selPath = this.packExplo.getPathForLocation(e.getX(), e.getY());
			File file = this.getFileFromTreePath(selPath);
			int i = this.parent.getTabPane().getIndexOfWritePane(file);
			if (i != -1 && e.getClickCount() == 1) {
				this.parent.getTabPane().setSelectedIndex(i);
			}
			else if (i == -1 && e.getClickCount() == 2) {
				this.parent.getTabPane().addWritePane(this.getFileFromTreePath(selPath));
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	/*@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, this.WIDTH, this.HEIGHT);
	}*/
}
