package gui.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonSelectModel extends JButton implements ActionListener {

	public ButtonSelectModel(String string) {
		
		super(string);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Ora si seleziona il modello");
	}

}
