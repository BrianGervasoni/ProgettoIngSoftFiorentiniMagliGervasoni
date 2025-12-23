package gui.trainingNoMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTrainingNoMap extends JPanel {

	public PanelTrainingNoMap() {
		
		super();
		
		JLabel ll = new JLabel("Statistiche e altra roba etc");
		
		ButtonShowRandomMap bsrm = new ButtonShowRandomMap("Mostra una mappa casuale");
		ButtonStopTraining bst = new ButtonStopTraining("Interrompi l'allenamento");
		ButtonToggle bt = new ButtonToggle("Toggle Train => Exec");
		
		add(ll);
		add(bsrm);
		add(bst);
		add(bt);
		
	}
	
}
