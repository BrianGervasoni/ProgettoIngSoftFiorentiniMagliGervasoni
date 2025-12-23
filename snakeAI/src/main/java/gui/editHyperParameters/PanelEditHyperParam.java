package gui.editHyperParameters;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelEditHyperParam extends JPanel {

	private CardLayout myCl;
	private JPanel ancestor;
	
	
	public PanelEditHyperParam(CardLayout layout, JPanel jpanelmain) {
		
		
		super();
		
		this.setLayout(new GridLayout(4, 5));
		
		
		
		myCl = layout;
		ancestor = jpanelmain;
		
		JLabel l1 = new JLabel("alphaW");
		//qui bisogna fare un get del valore
		JTextField jtf1 = new JTextField("123", 15);
		
		JLabel l2 = new JLabel("alphaB");
		//qui bisogna fare un get del valore
		JTextField jtf2 = new JTextField("0,15", 15);
		
		
		JLabel l3 = new JLabel("epoche");
		//qui bisogna fare un get del valore
		JTextField jtf3 = new JTextField("315", 15);
		
		JLabel l4 = new JLabel("minipatchSize");
		//qui bisogna fare un get del valore
		JTextField jtf4 = new JTextField("13", 15);
		
		JLabel l5 = new JLabel("discount");
		//qui bisogna fare un get del valore
		JTextField jtf5 = new JTextField("0,11", 15);
		
		JLabel l6 = new JLabel("lambda");
		//qui bisogna fare un get del valore
		JTextField jtf6 = new JTextField("9", 15);
		
		JLabel l7 = new JLabel("TimeStep");
		//qui bisogna fare un get del valore
		JTextField jtf7 = new JTextField("9", 15);
		
		
		
		
		ButtonAbort ba = new ButtonAbort("Annulla");
		ButtonDone bd = new ButtonDone("Fatto");
		
		add(l1);
		add(jtf1);
		
		add(l2);
		add(jtf2);
		
		add(l3);
		add(jtf3);
		
		
		
		
		
		
		add(ba);
		add(bd);
		
		
	}
	
}
