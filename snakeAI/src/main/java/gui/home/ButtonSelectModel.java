package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "File JSON", "json");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("Hai scelto questo file: " +
	            chooser.getSelectedFile().getName());
	    }
		
	    //qui va mandato il file caricato al thread manager
		
	}

}
