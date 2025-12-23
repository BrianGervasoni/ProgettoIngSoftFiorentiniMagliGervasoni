package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonStartTraining extends JButton implements ActionListener {

	private CardLayout cl;
	private JPanel main;
	
	public ButtonStartTraining(String string, CardLayout myCl, JPanel jp) {
		super(string);
		addActionListener(this);
		cl = myCl;
		main = jp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Comincia ora il training");

	}

}
