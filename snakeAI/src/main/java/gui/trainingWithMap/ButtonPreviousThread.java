package gui.trainingWithMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonPreviousThread extends JButton implements ActionListener {

	public ButtonPreviousThread(String str) {
		
		super(str);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// qua ci deve essere un metodo per passare al thread precedente

	}

}
