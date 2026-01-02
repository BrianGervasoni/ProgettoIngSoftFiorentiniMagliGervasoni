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
import io.reactivex.rxjava3.core.Observable;

public class UserInterface {

	private JFrame myFrame;
	private Controller controller;
	
	private JLabel lossAgent;
	private JLabel lossModel;
	private JTable renderedMap;

	
	/**
	 * Se 0, si è in fase di esecuzione; se 1, si è in fase di allenamento senza mappa; se 2, si è in fase di allenamento con mappa
	 * Se -1, si è nel Menu principale.
	 *
	 */
	private int modeIndicator;
	
	
	public UserInterface(Controller ctr) {
		myFrame = new JFrame("SnakeAI");
		controller = ctr;
		modeIndicator = -1;
	}
	
	
	public void resetFrame() {
		
		Container cc = myFrame.getContentPane();
		cc.removeAll();
		myFrame.revalidate();
		myFrame.repaint();
	}
	
	public void viewMap(Map map) {
		
		renderMap(map);
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
				modeIndicator = 0;
				startExecutionPhase();
				controller.startExecution();
			}});	
		
		
		/**
		 * Di default parte l'allenamento senza mappa
		 */
		JButton buttonStartTraining = new JButton("Avvia allenamento");
		buttonStartTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startTrainingPhaseWithoutMap();
				controller.startTraining();
				modeIndicator = 1;
			}});	
		
		
		JButton buttonExit = new JButton("Esci");
		buttonExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.exit();
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
		
		JPanel training = new JPanel();

		JLabel duration = new JLabel("Durata partita: " + String.valueOf(controller.getMap().getMatchDuration()));
		JLabel snakeLength = new JLabel("Lunghezza snake: " + String.valueOf(controller.getMap().getSnakeLength()));
		
		JButton buttonShowRandomMap = new JButton("Mostra una mappa casuale");
		buttonShowRandomMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				modeIndicator = 2;
				startTrainingPhaseWithMap();
			}});
		
		
		JButton buttonStopTraining = new JButton("Interrompi l'allenamento");
		buttonStopTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.terminateTraining();
				modeIndicator = -1;
				stopTrainingPhase();
			}});
		
		
		JButton buttonToggle = new JButton("Toggle training => Exec");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				modeIndicator = 0;
				toggleFromTrainToExec();
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
	
	
	
	/**
	 * Passa alla schermata di allenamento con il rendering della mappa. Questo passaggio si può fare solo nella fase di allenamento senza la mappa
	 */
	public void startTrainingPhaseWithMap() {
		
		resetFrame();
		
		JPanel training = new JPanel();
		
		JLabel duration = new JLabel("Durata partita: " + String.valueOf(controller.getMap().getMatchDuration()));
		JLabel snakeLength = new JLabel("Lunghezza snake: " + String.valueOf(controller.getMap().getSnakeLength()));
		
		
		JButton buttonHideMap = new JButton("Nascondi mappa");
		buttonHideMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startTrainingPhaseWithoutMap();
				modeIndicator = 1;
				
			}});
		
		
		JButton buttonStopTraining = new JButton("Interrompi l'allenamento");
		buttonStopTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.terminateTraining();
				modeIndicator = -1;
				stopTrainingPhase();
			}});

		
		JButton buttonNextThread = new JButton("Thread successivo");
		buttonNextThread.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.nextMap();
				
			}});
		

		JButton buttonPreviousThread = new JButton("Thread precedente");
		buttonPreviousThread.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.previousMap();
				
			}});
		
		
		JButton buttonToggle = new JButton("Toggle training => Exec");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				modeIndicator = 0;
				toggleFromTrainToExec();
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
	
	/**
	 * Comincia la fase di esecuzione, accessibile dal menu. Di default mostra la mappa e non può essere nascosta
	 */
	public void startExecutionPhase() {
		
		resetFrame();
		
		JPanel exec = new JPanel();
		
		JLabel duration = new JLabel("Durata partita: " + String.valueOf(controller.getMap().getMatchDuration()));
		JLabel snakeLength = new JLabel("Lunghezza snake: " + String.valueOf(controller.getMap().getSnakeLength()));
		
		
		JButton buttonStopExecution = new JButton("Termina esecuzione");
		buttonStopExecution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.terminateExecution();
				modeIndicator = -1;
				startingMenu();
			}});
		
		JButton buttonToggle = new JButton("Toggle Exec => Train");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				modeIndicator = 1;
				toggleFromExecToTrain();
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
	
	public void stopTrainingPhase() {
		this.startingMenu();
	}
	
	
	public void stopExecutionPhase() {
		this.startingMenu();
	}
	
	public void insertDirFileModel() {
		//TODO non sappiamo come implementarlo, Davide aiutaci tu
	}
	
	
	public void renderMap(Map map) {
		
		DefaultTableModel model = new DefaultTableModel();
		renderedMap = new JTable(model);
		model.setRowCount(map.getRowLenght());
		model.setColumnCount(map.getColumnLenght());
		
		for(int i = 0; i < map.getRowLenght(); i++) {
			for(int y = 0; y < map.getColumnLenght(); y++) {
				
				if(((y==0) && (i==0))||((y==0) && (i==map.getRowLenght()-1))||((y==map.getColumnLenght()-1) && (i==0))||((i==map.getRowLenght()-1) && (y==map.getColumnLenght()-1)))
					renderedMap.setValueAt("+", i, y);
				else if((i==0)||(i==map.getRowLenght()-1))
					renderedMap.setValueAt("-", i, y);
				else if((y==0)||(y==map.getColumnLenght()-1))
					renderedMap.setValueAt("|", i, y);
				else
					renderedMap.setValueAt(map.getBox(i, y).visual(), i, y);

				
			}
		}
		
		renderRightState();

	}
	
	
	public void renderRightState() {
		if(modeIndicator == 0) 
			startExecutionPhase();
		else if(modeIndicator == 1) 
			startTrainingPhaseWithoutMap();
		else if(modeIndicator == 2) 
			startTrainingPhaseWithMap();	
	}
	
	
	public void setLossAgent(double ar) {
		
		lossAgent = new JLabel("Loss Agent: " + String.valueOf(ar));
		renderRightState();		
	}
	
	public void setLossModel(double model) {
		lossAgent = new JLabel("Loss Model: " + String.valueOf(model));
		renderRightState();
	}
	
	
	/*
	 * Chiudi il programma
	 */
	public void exit() {
		controller.exit();
	}
	
	
	public void toggleFromExecToTrain() {
		startTrainingPhaseWithoutMap();
		controller.toggleFromExecToTrain();
	}
	
	public void toggleFromTrainToExec() {
		startExecutionPhase();
		controller.toggleFromTrainToExec();
	}
	
	
	
}
