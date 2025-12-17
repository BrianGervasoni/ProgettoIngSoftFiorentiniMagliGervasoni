package gui.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonStartExecution extends JButton implements ActionListener {

	public ButtonStartExecution(String string) {
		
		super(string);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Comincia l'esecuzione del gioco");

	}

}
