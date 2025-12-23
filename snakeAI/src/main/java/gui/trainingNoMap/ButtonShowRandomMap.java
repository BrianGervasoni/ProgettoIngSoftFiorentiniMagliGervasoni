package gui.trainingNoMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonShowRandomMap extends JButton implements ActionListener {

	public ButtonShowRandomMap(String str) {
		super(str);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
