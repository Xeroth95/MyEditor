package util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class MyFileFactory extends JDialog {
	
	public static String showFileDialog(String title, String message, JFrame parent) {
		final JDialog frame = new JDialog(parent, title, true);
		Container content = frame.getContentPane();
		content.setLayout(null);
		content.setPreferredSize(new java.awt.Dimension(400, 100));
		
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
				frame.dispose();
			}
			
		});
		content.add(add);
		JButton cancel = new JButton("Abbrechen");
		cancel.setBounds(290, 75, 100, 25);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				frame.dispose();
			}
			
		});
		content.add(cancel);
		frame.setContentPane(content);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		return textField.getText();
	}
	
	public static String showFolderDialog(String title, String message, JFrame parent) {
		final JDialog frame = new JDialog(parent, title, true);
		Container content = frame.getContentPane();
		content.setLayout(null);
		content.setPreferredSize(new java.awt.Dimension(400, 100));
		
		JLabel label = new JLabel(message);
		label.setBounds(10, 10, 380, 25);
		content.add(label);
		
		final JTextField textField = new JTextField();
		textField.setBounds(10, 40, 380, 25);
		content.add(textField);
		
		
		JButton add = new JButton("Erstellen");
		add.setBounds(180, 75, 100, 25);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText());
				frame.dispose();
			}
			
		});
		content.add(add);
		JButton cancel = new JButton("Abbrechen");
		cancel.setBounds(290, 75, 100, 25);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText(null);
				frame.dispose();
			}
			
		});
		content.add(cancel);
		frame.setContentPane(content);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

		return textField.getText();
	}
}
