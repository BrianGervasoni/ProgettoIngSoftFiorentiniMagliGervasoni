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
	

	
	public void viewMap(Map map) {
		
		renderMap(map);
	}
	
	
	public void setModeIndicator(int num) {
		modeIndicator = num;
	}
	
	
	public void hideMap() {
		startTrainingPhaseWithoutMap();
	}
	
	public int getMatchDuration() {
		return controller.getMap().getMatchDuration();
	}
	
	public int getSnakeLength() {
		return controller.getMap().getSnakeLength();
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
