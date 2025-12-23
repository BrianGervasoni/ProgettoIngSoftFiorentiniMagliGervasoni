package gui.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonExit extends JButton implements ActionListener {

	public ButtonExit(String string) {
		super(string);
		addActionListener(this);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Ora chiudo il programma");
	}

}
