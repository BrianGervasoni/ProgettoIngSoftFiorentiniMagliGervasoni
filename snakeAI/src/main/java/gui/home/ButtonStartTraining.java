package gui.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonStartTraining extends JButton implements ActionListener {

	public ButtonStartTraining(String string) {
		super(string);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Comincia ora il training");

	}

}
