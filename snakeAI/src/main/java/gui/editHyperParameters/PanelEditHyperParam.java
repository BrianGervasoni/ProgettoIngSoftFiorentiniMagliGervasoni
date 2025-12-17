package gui.editHyperParameters;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelEditHyperParam extends JPanel {

	public PanelEditHyperParam() {
		
		
		super();
		JLabel ll = new JLabel("Primo parametro");
		JTextField jtf = new JTextField("123");
		ButtonAbort ba = new ButtonAbort("Annulla");
		
		add(ll);
		add(jtf);
		add(ba);
		
		
	}
	
}
