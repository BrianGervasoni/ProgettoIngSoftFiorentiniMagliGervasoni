package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonSelectModel extends JButton implements ActionListener {

	private CardLayout myCl;
	private JPanel ancestor;
	
	public ButtonSelectModel(String string, CardLayout myCl, JPanel jp) {
		
		super(string);
		this.myCl = myCl;
		this.ancestor = jp;
		addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Ora si seleziona il modello");
	}

}
