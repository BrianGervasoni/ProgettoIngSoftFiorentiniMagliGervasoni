package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ButtonSelectModel extends JButton implements ActionListener {


	
	public ButtonSelectModel(String string) {
		
		super(string);

		addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		

		
	}

}
