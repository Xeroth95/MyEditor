package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import util.MyFileFactory;

public class MyPackagePane extends JPanel implements MouseListener, TreeSelectionListener {
	private static final long serialVersionUID = 1L;

	private JTree packExplo;
	private File startingDir, currentDir, currentFile;
	private DefaultMutableTreeNode currentDirNode, currentNode;
	private MyGui parent;
	
	public MyPackagePane(MyGui gui) {
		super();
		this.parent = gui;
		this.setLayout(new BorderLayout());
		this.packExplo = new JTree((DefaultMutableTreeNode)null);
		this.packExplo.setCellRenderer(new MyTreeCellRenderer());
		this.packExplo.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.packExplo.addMouseListener(this);
		this.packExplo.addTreeSelectionListener(this);
		this.packExplo.setComponentPopupMenu(this.getPopupMenu());
		this.add(packExplo, BorderLayout.CENTER);
	}
	
	public void open(File directory, String name) {
		this.parent.getTabPane().closeAllCurrent();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(name);
		this.parent.setCurrentDir(directory);
		top.setAllowsChildren(true);
		this.createNodes(top, directory);
		this.currentDirNode = top;
		this.startingDir = directory;
		this.currentDir = directory;
		((DefaultTreeModel)this.packExplo.getModel()).setRoot(top);
		((DefaultTreeModel)this.packExplo.getModel()).reload(top);
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
				newParent.setAllowsChildren(true);
				parent.add(newParent);
				createNodes(newParent, f);
			}
		}
	}
	
	private File getFileFromTreePath(TreePath treePath) {
		String path = this.startingDir.getAbsolutePath();
		for (int i = 1; i < treePath.getPathCount(); i++) {
			path += File.separatorChar;
			path += treePath.getPathComponent(i);
		}
		File toReturn = new File(path);
		if (toReturn.exists())
			return toReturn;
		return null;
	}
	
	private JPopupMenu getPopupMenu() {
		/*if (this.packExplo.isSelectionEmpty())
			return null;*/
		JPopupMenu toReturn = new JPopupMenu();
		
		Action delete = new AbstractAction() {
			
			private boolean deleteDir(File dir) {
				if (dir.isDirectory()) {
					for (File file : dir.listFiles()) {
				         if (file.isDirectory())
				            deleteDir(file);
				         file.delete();
				    }
				}
			    return dir.delete();
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MyPackagePane.this.currentFile != null) {
					if (!deleteDir(MyPackagePane.this.currentFile)) {
						JOptionPane.showMessageDialog(null, ((MyPackagePane.this.currentFile.isDirectory()) ? "Der Ordner " : "Die Datei ") + "konnte nicht gel�scht werden!", "Fehler!", JOptionPane.ERROR_MESSAGE);
					} else {
						DefaultTreeModel model = (DefaultTreeModel) MyPackagePane.this.packExplo.getModel();
						model.removeNodeFromParent(MyPackagePane.this.currentNode);
					}
				}
			}
			
		};
		delete.putValue(Action.NAME, "L�schen");
		toReturn.add(delete);
		
		return toReturn;
	}
	
	public File getCurrentFile() {
		return this.currentDir;
	}
	
	public void addFileToCurrent(String name) {
		File file = new File(this.currentDir.getAbsolutePath() + File.separatorChar + name);
		if (file.exists()) {
			return;
			//TODO ask the user wether it should be replaced
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Datei konnte nicht erstellt werden!", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file.getName());
		newNode.setAllowsChildren(false);
		this.currentDirNode.add(newNode);
		((DefaultTreeModel) this.packExplo.getModel()).reload(this.currentDirNode);
		TreePath path = new TreePath(newNode.getPath());
		this.packExplo.setSelectionPath(path);
		this.parent.getTabPane().addWritePane(file);
	}
	
	public void addFolderToCurrent(String name) {
		File file = new File(this.currentDir.getAbsolutePath() + File.separatorChar + name);
		if (file.exists()) {
			return;
			//TODO ask the user wether it should be replaced
		} else {
			if (!file.mkdir()) {
				JOptionPane.showMessageDialog(null, "Ordner konnte nicht erstellt werden!", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file.getName());
		newNode.setAllowsChildren(true);
		this.currentDirNode.add(newNode);
		((DefaultTreeModel) this.packExplo.getModel()).reload(this.currentDirNode);
		TreePath path = new TreePath(newNode.getPath());
		this.packExplo.setSelectionPath(path);
		this.parent.getTabPane().addWritePane(file);
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
	
	public void valueChanged(TreeSelectionEvent e) {
		this.currentFile = this.getFileFromTreePath(e.getPath());
		this.currentNode = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
		if (currentFile != null) {
			if (currentFile.isDirectory()) {
				this.currentDir = currentFile;
				this.currentDirNode = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
			} else {
				this.currentDir = currentFile.getParentFile();
				this.currentDirNode = (DefaultMutableTreeNode) e.getPath().getPathComponent(e.getPath().getPathCount()-2);
			}
		}
	}
	
	private class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		@Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			if (value instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				if (node.getAllowsChildren()) {
					setIcon(UIManager.getIcon("FileView.directoryIcon"));
				} else {
					setIcon(UIManager.getIcon("FileView.fileIcon"));
				}
			}
			return this;
		}
	}
	
	@Override
	public JPopupMenu getComponentPopupMenu() {
		return null;
	}
}
