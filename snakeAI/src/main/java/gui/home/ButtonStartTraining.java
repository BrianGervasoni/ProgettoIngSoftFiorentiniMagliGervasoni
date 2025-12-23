package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonStartTraining extends JButton implements ActionListener {

	
	public ButtonStartTraining(String string) {
		super(string);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		


	}

}
