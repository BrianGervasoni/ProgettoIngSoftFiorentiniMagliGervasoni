package gui.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonExit extends JButton implements ActionListener {

	public ButtonExit(String string) {
		super(string);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Chiusura del programma");
	}

}
