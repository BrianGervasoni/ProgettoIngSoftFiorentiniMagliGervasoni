package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonStartExecution extends JButton implements ActionListener {

	private CardLayout myCl;
	private JPanel jp;
	
	public ButtonStartExecution(String string, CardLayout myCl, JPanel jp) {
		
		super(string);
		addActionListener(this);
		this.myCl = myCl;
		this.jp = jp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Comincia l'esecuzione del gioco");

	}

}
