package userInterface;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import gui.editHyperParameters.ButtonAbort;
import gui.editHyperParameters.ButtonDone;
import gui.execution.ButtonStopExecution;
import gui.home.ButtonExit;
import gui.home.ButtonModifyHyperParam;
import gui.home.ButtonSelectModel;
import gui.home.ButtonStartExecution;
import gui.home.ButtonStartTraining;
import gui.trainingNoMap.ButtonShowRandomMap;
import gui.trainingNoMap.ButtonStopTraining;
import gui.trainingNoMap.ButtonToggle;
import gui.trainingWithMap.ButtonNextThread;
import gui.trainingWithMap.ButtonPreviousThread;

public class UserInterface {

	private JFrame myFrame;
	
	public UserInterface() {
		myFrame = new JFrame("SnakeAI");
	}
	
	
	public void resetFrame() {
		
		Container cc = myFrame.getContentPane();
		cc.removeAll();
		myFrame.revalidate();
		myFrame.repaint();
	}
	
	public void viewMap() {
		
	}
	
	public void hideMap() {
		
	}
	
	
	public void startingMenu() {
		
		resetFrame();
		
		JPanel menuPanel = new JPanel();
	
		ButtonModifyHyperParam bmhp = new ButtonModifyHyperParam("Modifica HyperParametri");
		ButtonSelectModel bsm = new ButtonSelectModel("Seleziona modello");
		ButtonStartExecution bse = new ButtonStartExecution("Avvia esecutione");
		ButtonStartTraining bst = new ButtonStartTraining("Avvia allenamento");
		ButtonExit be = new ButtonExit("Esci");
		
		menuPanel.setLayout(new GridLayout(5, 1, 10, 10));
		
		myFrame.add(menuPanel);
		
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	
	
	public void startTrainingPhaseWithoutMap() {
		
		resetFrame();
		
		JPanel training = new JPanel();
		
		JLabel ll = new JLabel("Statistiche e altra roba etc");
		
		ButtonShowRandomMap bsrm = new ButtonShowRandomMap("Mostra una mappa casuale");
		ButtonStopTraining bst = new ButtonStopTraining("Interrompi l'allenamento");
		ButtonToggle bt = new ButtonToggle("Toggle Train => Exec");
		
		training.add(bsrm);
		training.add(bst);
		training.add(bt);
		
		myFrame.add(training);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	
	public void startTrainingPhaseWithMap() {
		
		resetFrame();
		
		JPanel training = new JPanel();
		
		JLabel ll1 = new JLabel("Qua va renderizzata la mappa");
		JLabel ll = new JLabel("Statistiche e altra roba etc");
		
		ButtonShowRandomMap bsrm = new ButtonShowRandomMap("Mostra una mappa casuale");
		ButtonStopTraining bst = new ButtonStopTraining("Interrompi l'allenamento");
		
		ButtonNextThread bnt = new ButtonNextThread("Thread successivo");
		ButtonPreviousThread bpt = new ButtonPreviousThread("Thread precedente");
		
		training.add(bsrm);
		training.add(bst);
		training.add(bnt);
		training.add(bpt);
		
		myFrame.add(training);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	
	public void insertHyperParameters() {
		
		resetFrame();
		
		JPanel hyperParam = new JPanel();
		
		hyperParam.setLayout(new GridLayout(5, 4, 40, 40));
		
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
		
		JLabel l8 = new JLabel("motivation");
		//qui bisogna fare un get del valore
		JTextField jtf8 = new JTextField("0,16", 15);
		
		JLabel l9 = new JLabel("entropyContribution");
		//qui bisogna fare un get del valore
		JTextField jtf9 = new JTextField("0,78", 15);
		
		
		
		
		ButtonAbort ba = new ButtonAbort("Annulla");
		ButtonDone bd = new ButtonDone("Fatto");
		
		
		hyperParam.add(l1);
		hyperParam.add(jtf1);
		hyperParam.add(l2);
		hyperParam.add(jtf2);
		hyperParam.add(l3);
		hyperParam.add(jtf3);
		hyperParam.add(l4);
		hyperParam.add(jtf4);
		hyperParam.add(l5);
		hyperParam.add(jtf5);
		hyperParam.add(l6);
		hyperParam.add(jtf6);
		hyperParam.add(l7);
		hyperParam.add(jtf7);
		hyperParam.add(l8);
		hyperParam.add(jtf8);
		hyperParam.add(l9);
		hyperParam.add(jtf9);
		hyperParam.add(ba);
		hyperParam.add(bd);
		
		myFrame.add(hyperParam);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
		
	}
	
	
	public void startExecutionPhase() {
		
		resetFrame();
		
		JPanel exec = new JPanel();
		
		JLabel jj = new JLabel("Rendering mappa in corso");
		JLabel stats = new JLabel("Statistiche etc");
		ButtonStopExecution bse = new ButtonStopExecution("Termina esecuzione");
		ButtonToggle bt = new ButtonToggle("Toggle Exec => Train");
		
		exec.add(jj);
		exec.add(stats);
		exec.add(bse);
		exec.add(bt);
		
		myFrame.add(exec);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	public void stopTrainingPhase() {
		this.startingMenu();
	}
	
	
	public void stopExecutionPhase() {
		this.startingMenu();
	}
	
	public void insertDirFileModel() {
		//??
	}
	
	public void switchThreadMapTrainingPhase() {
		//??
	}
	
}
