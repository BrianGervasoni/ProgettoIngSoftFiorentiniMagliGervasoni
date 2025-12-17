package gui.home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonModifyHyperParam extends JButton implements ActionListener {

	public ButtonModifyHyperParam(String string) {
		super(string);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Adesso si modificano gli hyperparametri");

	}

}
