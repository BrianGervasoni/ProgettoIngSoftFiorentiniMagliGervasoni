package gui.editHyperParameters;



import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonAbort extends JButton implements ActionListener{

	public ButtonAbort(String string) {
		
		super(string);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		
	}

}
