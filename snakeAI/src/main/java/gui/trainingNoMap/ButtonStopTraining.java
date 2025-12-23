package gui.trainingNoMap;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonStopTraining extends JButton implements ActionListener {

	public ButtonStopTraining(String str) {
		
		super(str);
		addActionListener(this);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		((CardLayout) this.getParent().getParent().getLayout()).first(this.getParent().getParent());
		

	}

}
