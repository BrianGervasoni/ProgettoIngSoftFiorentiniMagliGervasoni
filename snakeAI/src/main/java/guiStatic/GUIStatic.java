package guiStatic;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import userInterface.UserInterface;

public class GUIStatic {

	
	public static void resetFrame(JFrame jf) {
		
		Container cc = jf.getContentPane();
		cc.removeAll();
		jf.revalidate();
		jf.repaint();
	}
	
	
	public static void printMenu(JFrame jf, UserInterface ui) {
		
		GUIStatic.resetFrame(jf);
		
		JPanel menuPanel = new JPanel();
	
		JButton modifyHyperParam = new JButton("Modifica HyperParametri");
		modifyHyperParam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				insertHyperParameters();
			}});	
		
		
		JButton buttonSelectModel = new JButton("Seleziona modello");
		buttonSelectModel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				insertDirFileModel();
			}});	
		
		
		JButton buttonStartExecution = new JButton("Avvia esecutione");
		buttonStartExecution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.setModeIndicator(0);
				ui.startExecutionPhase();
				//ui.controller.startExecution();
			}});	
		
		
		/**
		 * Di default parte l'allenamento senza mappa
		 */
		JButton buttonStartTraining = new JButton("Avvia allenamento");
		buttonStartTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.startTrainingPhaseWithoutMap();
				//controller.startTraining();
				ui.setModeIndicator(1);
			}});	
		
		
		JButton buttonExit = new JButton("Esci");
		buttonExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ui.exit();
				//exit();
			}});	
	
		menuPanel.add(modifyHyperParam);
		menuPanel.add(buttonSelectModel);
		menuPanel.add(buttonStartExecution);
		menuPanel.add(buttonStartTraining);
		menuPanel.add(buttonExit);
		
		
		menuPanel.setLayout(new GridLayout(5, 1, 10, 10));
		
		jf.add(menuPanel);
		
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.pack();		
		jf.setSize(640,480);
		jf.setVisible(true);
	}
	
	
	
	public static void printTrainNoMap(JFrame myFrame, JLabel lossAgent, JLabel lossModel, UserInterface ui) {
		
		GUIStatic.resetFrame(myFrame);
		
		JPanel training = new JPanel();

		JLabel duration = new JLabel("Durata partita: " + String.valueOf(ui.getMatchDuration()));
		JLabel snakeLength = new JLabel("Lunghezza snake: " + String.valueOf(ui.getSnakeLength()));
		
		JButton buttonShowRandomMap = new JButton("Mostra una mappa casuale");
		buttonShowRandomMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.setModeIndicator(2);
				ui.startTrainingPhaseWithMap();
			}});
		
		
		JButton buttonStopTraining = new JButton("Interrompi l'allenamento");
		buttonStopTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//controller.terminateTraining();
				ui.setModeIndicator(-1);
				ui.stopTrainingPhase();
			}});
		
		
		JButton buttonToggle = new JButton("Toggle training => Exec");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.setModeIndicator(0);
				//toggleFromTrainToExec();
			}});
		
		
		training.add(lossAgent);
		training.add(lossModel);
		training.add(duration);
		training.add(snakeLength);
		training.add(buttonShowRandomMap);
		training.add(buttonStopTraining);
		training.add(buttonToggle);
		
		
		myFrame.add(training);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	
	
	
	
	public static void printTrainWithMap(JFrame myFrame, JLabel lossAgent, JLabel lossModel, JTable renderedMap, UserInterface ui) {
		
		GUIStatic.resetFrame(myFrame);
		
		JPanel training = new JPanel();
		
		JLabel duration = new JLabel("Durata partita: " + String.valueOf(ui.getMatchDuration()));
		JLabel snakeLength = new JLabel("Lunghezza snake: " + String.valueOf(ui.getSnakeLength()));
		
		
		JButton buttonHideMap = new JButton("Nascondi mappa");
		buttonHideMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
		//		ui.startTrainingPhaseWithoutMap();
				ui.setModeIndicator(1);
				
			}});
		
		
		JButton buttonStopTraining = new JButton("Interrompi l'allenamento");
		buttonStopTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			//	controller.terminateTraining();
				ui.setModeIndicator(-1);
				ui.stopTrainingPhase();
			}});

		
		JButton buttonNextThread = new JButton("Thread successivo");
		buttonNextThread.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//controller.nextMap();
				
			}});
		

		JButton buttonPreviousThread = new JButton("Thread precedente");
		buttonPreviousThread.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//controller.previousMap();
				
			}});
		
		
		JButton buttonToggle = new JButton("Toggle training => Exec");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.setModeIndicator(0);
			}});
		
		
		
		training.add(renderedMap);
		training.add(lossAgent);
		training.add(lossModel);
		training.add(duration);
		training.add(snakeLength);
		training.add(buttonHideMap);
		training.add(buttonToggle);
		training.add(buttonStopTraining);
		training.add(buttonNextThread);
		training.add(buttonPreviousThread);
		
		myFrame.add(training);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	
	
	public static void printExecution(JFrame myFrame, JLabel lossAgent, JLabel lossModel, UserInterface ui, JTable renderedMap) {
		
		GUIStatic.resetFrame(myFrame);
		
		JPanel exec = new JPanel();
		
		JLabel duration = new JLabel("Durata partita: " + String.valueOf(ui.getMatchDuration()));
		JLabel snakeLength = new JLabel("Lunghezza snake: " + String.valueOf(ui.getSnakeLength()));
		
		
		JButton buttonStopExecution = new JButton("Termina esecuzione");
		buttonStopExecution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				controller.terminateExecution();
				setModeIndicator(-1);
			}});
		
		JButton buttonToggle = new JButton("Toggle Exec => Train");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.setModeIndicator(1);
//				toggleFromExecToTrain();
			}});
		
		exec.add(renderedMap);
		exec.add(lossAgent);
		exec.add(lossModel);
		exec.add(duration);
		exec.add(snakeLength);
		exec.add(buttonStopExecution);
		exec.add(buttonToggle);
		
		myFrame.add(exec);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	
	
	/**
	 * Schermata per inserire gli iperparametri, accessibile dal menu
	 */
	public static void insertHyperParameters(JFrame myFrame, UserInterface ui) {
		
		GUIStatic.resetFrame(myFrame);
		
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
		
		
		
		JButton buttonAbort = new JButton("buttonAbort");
		buttonAbort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startingMenu();

			}});
		

		JButton buttonDone = new JButton("Fatto");
		buttonDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//TODO: non è chiaro come sia da gestire
				startingMenu();
			}});

		
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
		hyperParam.add(buttonAbort);
		hyperParam.add(buttonDone);
		
		myFrame.add(hyperParam);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
		
	}
	
	
	
	
	
	
	
	
	
}
