package userInterface;

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
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import gioco.snakeAI.Map;



public class UserInterface {

	private JFrame myFrame;
	private Controller controller;
	
	
	public UserInterface(Controller ctr) {
		myFrame = new JFrame("SnakeAI");
		controller = ctr;
	}
	
	
	public void resetFrame() {
		
		Container cc = myFrame.getContentPane();
		cc.removeAll();
		myFrame.revalidate();
		myFrame.repaint();
	}
	
	public void viewMap() {
		renderMap(new Map());
	}
	
	public void hideMap() {
		startTrainingPhaseWithoutMap();
	}
	
	/**
	 * Fai renderizzare alla gui il menu principale
	 */
	public void startingMenu() {
		
		resetFrame();
		
		JPanel menuPanel = new JPanel();
	
		JButton modifyHyperParam = new JButton("Modifica HyperParametri");
		modifyHyperParam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopTrainingPhase();
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
				startExecutionPhase();
			}});	
		
		
		JButton buttonStartTraining = new JButton("Avvia allenamento");
		buttonStartTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startTrainingPhaseWithoutMap();
			}});	
		
		
		JButton buttonExit = new JButton("Esci");
		buttonExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}});	
	
		menuPanel.add(modifyHyperParam);
		menuPanel.add(buttonSelectModel);
		menuPanel.add(buttonStartExecution);
		menuPanel.add(buttonStartTraining);
		menuPanel.add(buttonExit);
		
		
		menuPanel.setLayout(new GridLayout(5, 1, 10, 10));
		
		myFrame.add(menuPanel);
		
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	
	/**
	 * Mostra la schermata di allenamento senza la mappa; dal menu si passa a questa schermata di default quando si vuole far cominciare l'allenamento
	 */
	public void startTrainingPhaseWithoutMap() {
		
		resetFrame();
		
		//TODO comando per il ThreadAIManager di cominciare la fase di esecuzione con la mappa
		
		JPanel training = new JPanel();
		
		JLabel ll = new JLabel("Statistiche e altra roba etc");
		

		JButton buttonShowRandomMap = new JButton("Mostra una mappa casuale");
		buttonShowRandomMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				viewMap();
				
			}});
		
		
		JButton buttonStopTraining = new JButton("Interrompi l'allenamento");
		buttonStopTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopTrainingPhase();
				
			}});
		
		
		JButton buttonToggle = new JButton("Toggle traing => Exec");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggleFromTrainToExec();
				
			}});
		
		
		training.add(ll);
		training.add(buttonShowRandomMap);
		training.add(buttonStopTraining);
		training.add(buttonToggle);
		
		myFrame.add(training);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	
	
	/**
	 * Passa alla schermata di allenamento con il rendering della mappa. Questo passaggio si può fare solo nella fase di allenamento senza la mappa
	 */
	public void startTrainingPhaseWithMap() {
		
		resetFrame();
		
		JPanel training = new JPanel();
		
		JLabel ll1 = new JLabel("Qua va renderizzata la mappa");
		JLabel ll = new JLabel("Statistiche e altra roba etc");
		
		JButton buttonShowRandomMap = new JButton("Mostra una mappa casuale");
		buttonShowRandomMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				switchThreadMapTrainingPhase();
				
			}});
		
		
		JButton buttonStopTraining = new JButton("Interrompi l'allenamento");
		buttonStopTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopTrainingPhase();
				
			}});

		
		JButton buttonNextThread = new JButton("Thread successivo");
		buttonNextThread.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO non sappiamo come gestire la cosa
				
			}});
		

		JButton buttonPreviousThread = new JButton("Thread precedente");
		buttonPreviousThread.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO non sappiamo come gestire la cosa
				
			}});
		
		training.add(ll1);
		training.add(ll);
		training.add(buttonShowRandomMap);
		training.add(buttonStopTraining);
		training.add(buttonNextThread);
		training.add(buttonPreviousThread);
		
		myFrame.add(training);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
	}
	
	/**
	 * Schermata per inserire gli iperparametri, accessibile dal menu
	 */
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
				startingMenu();
				//TODO: salvare i valori per il ThreadAIManager
				
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
	
	/**
	 * Comincia la fase di esecuzione, accessibile dal menu. Di default mostra la mappa e non può essere nascosta
	 */
	public void startExecutionPhase() {
		
		resetFrame();
		
		//TODO manda comando al ThreadAIManager di iniziare l'esecuzione
		
		JPanel exec = new JPanel();
		
		JLabel jj = new JLabel("Rendering mappa in corso");
		JLabel stats = new JLabel("Statistiche etc");
		
		JButton buttonStopExecution = new JButton("Termina esecuzione");
		buttonStopExecution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startingMenu();
				//TODO manda comando per interrompere l'esecuzione al ThreadAIManager
				
			}});
		
		JButton buttonToggle = new JButton("Toggle Exec => Train");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggleFromExecToTrain();
			}});
		
		exec.add(jj);
		exec.add(stats);
		exec.add(buttonStopExecution);
		exec.add(buttonToggle);
		
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
		//TODO non sappiamo come implementarlo, Davide aiutaci tu
	}
	
	public void switchThreadMapTrainingPhase() {
		//TODO non è chiaro come si gestisca
	}
	
	public JTable renderMap(Map map) {
		
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
		model.setRowCount(map.getRowLenght());
		model.setColumnCount(map.getColumnLenght());
		
		for(int i = 0; i < map.getRowLenght(); i++) {			// i sono le righe		questo for fa passare le righe
			for(int y = 0; y < map.getColumnLenght(); y++) {		// y sono le colonne	questo for fa passare le colonne
				
				if(((y==0) && (i==0))||((y==0) && (i==map.getRowLenght()-1))||((y==map.getColumnLenght()-1) && (i==0))||((i==map.getRowLenght()-1) && (y==map.getColumnLenght()-1)))
					table.setValueAt("+", i, y);
				else if((i==0)||(i==map.getRowLenght()-1))
					table.setValueAt("-", i, y);
				else if((y==0)||(y==map.getColumnLenght()-1))
					table.setValueAt("|", i, y);
				else
					table.setValueAt(map.getBox(i, y).visual(), i, y);

				
			}
		}
		
		return table;
	}
	
	
	/*
	 * Chiudi il programma
	 */
	public void exit() {
		//TODO da far quittare il programma
	}
	
	
	public void toggleFromExecToTrain() {
		startTrainingPhaseWithoutMap();
		//TODO comando per il ThreadAIManager di interrompere l'esecuzione
	}
	
	public void toggleFromTrainToExec() {
		startExecutionPhase();
		//TODO comando per il ThreadAIManager di interrompere l'allenamento
	}
	
	
	
}
