package gui.trainingWithMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTrainingWithMap extends JPanel {

	public PanelTrainingWithMap() {
		
		super();
		
		
		JLabel ll1 = new JLabel("Qua va renderizzata la mappa");
		JLabel ll = new JLabel("Statistiche e altra roba etc");
		
		ButtonShowRandomMap bsrm = new ButtonShowRandomMap("Mostra una mappa casuale");
		ButtonStopTraining bst = new ButtonStopTraining("Interrompi l'allenamento");
		
		add(ll1);
		add(ll);
		add(bsrm);
		add(bst);
		
		
	}
	
}
