package gui.home;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class PanelHome extends JPanel {

	private CardLayout myCl;
	private JPanel ancestor;
	
	
	public PanelHome(CardLayout cl, JPanel grandpa){
		super();
		
		myCl = cl;
		ancestor = grandpa;
		
		ButtonModifyHyperParam bmhp = new ButtonModifyHyperParam("Modifica HyperParametri", myCl, ancestor);
		ButtonSelectModel bsm = new ButtonSelectModel("Seleziona modello", myCl, ancestor);
		ButtonStartExecution bse = new ButtonStartExecution("Avvia esecutione", myCl, ancestor);
		ButtonStartTraining bst = new ButtonStartTraining("Avvia allenamento", myCl, ancestor);
		ButtonExit be = new ButtonExit("Esci");
		
		add(bmhp);
		add(bse);
		add(bst);
		add(bsm);
		add(be);
		
		setLayout(new GridLayout(5, 1, 10, 10));
		
	}
	
	
	
	
}
