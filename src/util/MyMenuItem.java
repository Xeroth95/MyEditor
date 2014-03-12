package util;

import java.awt.Component;

import javax.swing.JMenuItem;

import GUI.MyGui;

public abstract class MyMenuItem extends JMenuItem {
	
	public MyMenuItem(String name) {
		super(name);
	}
	
	public abstract void execute(MyGui gui);
}
