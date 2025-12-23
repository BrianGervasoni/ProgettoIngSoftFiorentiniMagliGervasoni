package gui.execution;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelExecution extends JPanel {

	public PanelExecution() {
		super();
		
		JLabel jj = new JLabel("Rendering mappa in corso");
		JLabel stats = new JLabel("Statistiche etc");
		ButtonStopExecution bse = new ButtonStopExecution("Termina esecuzione");
		ButtonToggle bt = new ButtonToggle("Toggle Exec => Train");
		
		add(jj);
		add(stats);
		add(bse);
		add(bt);
		
	}
	
}
