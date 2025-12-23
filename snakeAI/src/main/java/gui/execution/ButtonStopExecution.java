package gui.execution;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonStopExecution extends JButton implements ActionListener {

	public ButtonStopExecution(String str) {
		
		super(str);
		addActionListener(this);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		((CardLayout) this.getParent().getParent().getLayout()).first(this.getParent().getParent());
		

	}

}
