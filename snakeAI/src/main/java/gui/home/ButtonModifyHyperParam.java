package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonModifyHyperParam extends JButton implements ActionListener {

	public ButtonModifyHyperParam(String string) {
		super(string);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		((CardLayout) this.getParent().getParent().getLayout()).next(this.getParent().getParent());
		System.out.println("click");
	}

}
