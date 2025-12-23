package gui.home;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonModifyHyperParam extends JButton implements ActionListener {

	private CardLayout myCl;
	private JPanel ancestor;
	
	public ButtonModifyHyperParam(String string, CardLayout cl, JPanel grandpa) {
		super(string);
		addActionListener(this);
		myCl = cl;
		ancestor = grandpa;
		
//		System.out.println(myCl + " " + ancestor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//((CardLayout) this.getParent().getParent().getLayout()).show(this.getParent().getParent(), "editHyper");
		
//		((CardLayout) ((PanelHome) this.getParent()).getParent().getLayout()).first(getFocusCycleRootAncestor());
		
		
		
//		myCl.show(ancestor, "editHyper");
		
		myCl.next(ancestor);
		
		System.out.println("click");
	}

}
