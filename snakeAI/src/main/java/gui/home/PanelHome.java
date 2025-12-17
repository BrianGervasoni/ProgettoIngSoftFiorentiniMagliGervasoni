package gui.home;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class PanelHome extends JPanel {

	public PanelHome(){
		super();
	
		ButtonModifyHyperParam bmhp = new ButtonModifyHyperParam("Modifica HyperParametri");
		ButtonSelectModel bsm = new ButtonSelectModel("Seleziona modello");
		ButtonStartExecution bse = new ButtonStartExecution("Avvia esecutione");
		ButtonStartTraining bst = new ButtonStartTraining("Avvia allenamento");
		ButtonExit be = new ButtonExit("Esci");
		
		add(bmhp);
		add(bse);
		add(bst);
		add(bsm);
		add(be);
		
		setLayout(new GridLayout(5, 1, 10, 10));
		
	}
	
	
	
	
}
