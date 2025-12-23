package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonStartExecution extends JButton implements ActionListener {

	public ButtonStartExecution(String string) {
		
		super(string);
		addActionListener(this);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		

	}

}
