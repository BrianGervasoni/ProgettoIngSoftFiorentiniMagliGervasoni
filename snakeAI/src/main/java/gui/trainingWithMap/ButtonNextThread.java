package gui.trainingWithMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonNextThread extends JButton implements ActionListener {

	public ButtonNextThread(String str) {
		
		super(str);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// qua ci deve essere un metodo per passare al thread successivo

	}

}
