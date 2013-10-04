package util;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MyFileFactory extends JFrame {
	
	public static void main(String[] args) {
		String home = MyFileFactory.showAddDialog("LANGE MESSAGE BLBLABL", "WUTUTUTUSTUASTUAST");
	}
	
	JLabel message;
	JComboBox<String> box;
	JTextField textField;
	JButton add;
	JButton cancel;
	boolean pressedButton;
	
	private MyFileFactory(String title, String message) {
		super(title);
		pressedButton = false;
		Container content = this.getContentPane();
		content.setLayout(null);
		content.setPreferredSize(new java.awt.Dimension(400, 100));
		
		this.message = new JLabel(message);
		this.message.setBounds(10, 10, 380, 25);
		content.add(this.message);
		
		this.textField = new JTextField();
		this.textField.setBounds(10, 40, 300, 25);
		content.add(this.textField);
		
		this.box = new JComboBox<String>(new String[]{".c", ".asm"});
		this.box.setBounds(320, 40, 70, 25);
		content.add(this.box);
		
		this.add = new JButton("Erstellen");
		this.add.setBounds(180, 75, 100, 25);
		this.add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyFileFactory.this.textField.setText(MyFileFactory.this.textField.getText() + (String) MyFileFactory.this.box.getSelectedItem());
				MyFileFactory.this.pressedButton = true;
			}
			
		});
		content.add(add);
		
		this.cancel = new JButton("Abbrechen");
		this.cancel.setBounds(290, 75, 100, 25);
		this.cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyFileFactory.this.textField.setText(null);
				MyFileFactory.this.pressedButton = true;
			}
			
		});
		content.add(cancel);
		this.setContentPane(content);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public String getInput() {
		while (!pressedButton) {
			System.out.println("Und");
		}
		return this.textField.getText();
	}
	
	public static String showAddDialog(String title, String message) {
		MyFileFactory mff = new MyFileFactory(title, message);
		return mff.getInput();
	}
	
	/*public static String showAddDialog(String title, String message) {
		JFrame frame = new JDialog();
		Container content = frame.getContentPane();
		content.setLayout(null);
		content.setPreferredSize(new java.awt.Dimension(400, 100));
		
		final MyBoolean toEnd = new MyBoolean();
		
		JLabel label = new JLabel(message);
		label.setBounds(10, 10, 380, 25);
		content.add(label);
		
		final JTextField textField = new JTextField();
		textField.setBounds(10, 40, 300, 25);
		content.add(textField);
		
		final JComboBox<String> box = new JComboBox<String>(new String[]{".c", ".asm"});
		box.setBounds(320, 40, 70, 25);
		content.add(box);
		
		JButton add = new JButton("Erstellen");
		add.setBounds(180, 75, 100, 25);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText() + (String)box.getSelectedItem());
				toEnd.setBool(true);
			}
			
		});
		content.add(add);
		JButton cancel = new JButton("Abbrechen");
		cancel.setBounds(290, 75, 100, 25);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				toEnd.setBool(true);
			}
			
		});
		content.add(cancel);
		frame.setContentPane(content);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while (!toEnd.getBool()) {
			try {
				frame.repaint();
				Thread.sleep(5);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		frame.dispose();
		return textField.getText();
	}
	
	static class MyBoolean {
		Boolean bool;
		
		public MyBoolean() {
			this.bool = false;
		}
		
		public void setBool(boolean bool) {
			this.bool = bool;
		}
		
		public boolean getBool() {
			return this.bool;
		}
	}*/
}
