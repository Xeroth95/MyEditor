package util;

import java.awt.Component;

import javax.swing.JMenuItem;

public abstract class MyMenuItem extends JMenuItem {
	
	public MyMenuItem(String name) {
		super(name);
	}
	
	public abstract void execute(Component... components);
}
