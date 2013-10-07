package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import util.MyPaneInterface;

public class MyTabbedPane extends JTabbedPane {
	
	private MyGui parent;
	private MouseListener mouseListener;
	protected ArrayList<MyPaneInterface> myPanes;
	
	public MyTabbedPane(MyGui gui) {
		super();
		this.parent = gui;
		this.myPanes = new ArrayList<MyPaneInterface>();
	}
	
	public MyTabbedPane(int tabPlacement, MyGui gui) {
		super(tabPlacement);
		this.parent = gui;
		this.myPanes = new ArrayList<MyPaneInterface>();
	}
	
	public MyTabbedPane(int tabPlacement, int tabLayoutPolicy, MyGui gui) {
		super(tabPlacement, tabLayoutPolicy);
		this.parent = gui;
		this.myPanes = new ArrayList<MyPaneInterface>();
	}
	
	public void closeAllCurrent() {
		System.out.println(this.getTabRunCount());
		while (this.getTabRunCount() > 0)
			this.remove(0);
	}
	
	/*public void addTab(String title, Component component) {
		if (component instanceof MyWritePane) writePanes.add((MyWritePane) component);
		super.addTab(title, component);
		this.setTabComponentAt(this.getTabCount()-1, new TabElement(this));
	}
	
	public void addTab(String title, Icon icon, Component component) {
		if (component instanceof MyWritePane) writePanes.add((MyWritePane) component);
		super.addTab(title, icon, component);
		this.setTabComponentAt(this.getTabCount()-1, new TabElement(this));
	}
	
	public void addTab(String title, Icon icon, Component component, String tip) {
		if (component instanceof MyWritePane) writePanes.add((MyWritePane) component);
		super.addTab(title, icon, component, tip);
		this.setTabComponentAt(this.getTabCount()-1, new TabElement(this));
	}*/
	
	public void addWritePane(File file) {
		if (file.isFile()) {
			this.open(file);
			this.setTabComponentAt(this.getTabCount()-1, new TabElement(this));
			this.setSelectedIndex(this.getTabCount()-1);
		}
	}
	
	public MyWritePane[] getWritePanes() {
		return this.myPanes.toArray(new MyWritePane[0]);
	}
	
	public int getIndexOfWritePane(File file) {
		for (int i = 0; i < this.myPanes.size(); i++) {
			if (myPanes.get(i) instanceof MyWritePane && ((MyWritePane) myPanes.get(i)).getFile().getAbsolutePath().equals(file.getAbsolutePath()))
				return i;
		}
		return -1;
	}
	
	private void open(File file) {
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = "";
			for (int c = reader.read(); c != -1; c = reader.read()) {
				text += (char) c;
			}
			MyWritePane writePane = new MyWritePane(text, file);
			writePane.addMouseListener(this.mouseListener);
			this.myPanes.add(writePane);
			this.addTab(file.getName(), writePane);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try { reader.close(); } catch (IOException ioe) { ioe.printStackTrace(); }
		}
	}
	
	public void saveAll() {
		for (MyPaneInterface writePane : this.myPanes) {
			writePane.save();
		}
	}
	
	@Override
	public void remove(int index) {
		this.myPanes.remove(index);
		super.remove(index);
	}
	
	@Override
	public void remove(Component c) {
		/* TODO -- make same as remove(int) */
		super.remove(c);
	}
		
	private class TabElement extends JPanel{
		
		private final JTabbedPane pane;
		
		public TabElement(final MyTabbedPane pane) {
			super(new FlowLayout(FlowLayout.LEFT, 0, 0));
			if (pane == null) {
	            throw new NullPointerException("TabbedPane is null");
	        }
	        this.pane = pane;
	        setOpaque(false);
	        
	        //make JLabel read titles from JTabbedPane
	        JLabel label = new JLabel() {
	            public String getText() {
	                int i = pane.indexOfTabComponent(TabElement.this);
	                if (i != -1) {
	                    return pane.getTitleAt(i);
	                }
	                return null;
	            }
	        };
	        
	        JLabel picLabel = new JLabel() {
	        	public Icon getIcon() {
	        		int i = pane.indexOfTabComponent(TabElement.this);
	        		if (i != -1) {
	        			return pane.getIconAt(i);
	        		}
	        		return null;
	        	}
	        };
	        
	        JLabel tip = new JLabel() {
	        	public String getString() {
	        		int i = pane.indexOfTabComponent(TabElement.this);
	        		if (i != -1)
	        			return pane.getToolTipTextAt(i);
	        		return null;
	        	}
	        };
	        
	        String tipText = tip.getText();
	        if (tipText != null && !tipText.equals("")) {
	        	setToolTipText(tipText);
	        	picLabel.setToolTipText(tipText);
	        	label.setToolTipText(tipText);
	        }
	        
	        add(picLabel);
	        picLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
	        add(label);
	        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
	        JButton button = new TabButton();
	        add(button);
	        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		}
		
		private class TabButton extends JButton implements ActionListener{
			public TabButton() {
				int size = 17;
				setPreferredSize(new java.awt.Dimension(size, size));
				setToolTipText("close this tab");
				setUI(new BasicButtonUI());
				setContentAreaFilled(false);
				setFocusable(false);
				setBorder(BorderFactory.createEtchedBorder());
				setBorderPainted(false);
				addMouseListener(buttonMouseListener);
				setRolloverEnabled(true);
				addActionListener(this);
			}
			
			public void actionPerformed(ActionEvent e) {
				int i = pane.indexOfTabComponent(TabElement.this);
				if (i != -1) {
					pane.remove(i);
				}
			}
			
			public void updateUI() {} //no update for you
			
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				if (getModel().isPressed()) {
					g2.translate(1, 1);
				}
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.BLACK);
				if (getModel().isRollover()) {
					g2.setColor(Color.MAGENTA);
				}
				int delta = 6;
				g2.drawLine(delta, delta, getWidth()-delta-1, getHeight()-delta-1);
				g2.drawLine(getWidth()-delta-1, delta, delta, getHeight()-delta-1);
				g2.dispose();
			}
		}
		
		public final MouseListener buttonMouseListener = new MouseAdapter() { //this should actually be private final static but w/e
	        public void mouseEntered(MouseEvent e) {
	            Component component = e.getComponent();
	            if (component instanceof AbstractButton) {
	                AbstractButton button = (AbstractButton) component;
	                button.setBorderPainted(true);
	            }
	        }

	        public void mouseExited(MouseEvent e) {
	            Component component = e.getComponent();
	            if (component instanceof AbstractButton) {
	                AbstractButton button = (AbstractButton) component;
	                button.setBorderPainted(false);
	            }
	        }
	    };
	}
	
}
