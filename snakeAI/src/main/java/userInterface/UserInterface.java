package userInterface;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.Controller;
import errorHandler.ThreadException;
import gioco.snakeAI.Map;
import guiStatic.GUIStatic;

public class UserInterface {

	private JFrame myFrame;
	private Controller controller;
	
	private JLabel lossAgent;
	private JLabel lossModel;
	private JTable renderedMap;

	private String modelPath;
	private int threadNumber;
	
	/**
	 * Se 0, si è in fase di esecuzione; se 1, si è in fase di allenamento senza mappa; se 2, si è in fase di allenamento con mappa
	 * Se -1, si è nel Menu principale.
	 *
	 */
	private int modeIndicator;
	
	
	public UserInterface() {
		myFrame = new JFrame("SnakeAI");
		controller = new Controller(this);
		modeIndicator = -1;
	}
	
	public void start() {
		GUIStatic.printMenu(myFrame, this);
	}
	

	public void viewMap(Map map) {
		renderMap(map);
	}
	
	
	public void setModeIndicator(int num) {
		modeIndicator = num;
	}
	
	
	public void hideMap() {
		modeIndicator = 1;
		GUIStatic.printTrainNoMap(myFrame, lossAgent, lossModel, this);
	}
	
	
	public int getMatchDuration() {
		return controller.getMap().getMatchDuration();
	}
	
	
	public int getSnakeLength() {
		return controller.getMap().getSnakeLength();
	}
	
	public JFrame getMyFrame() {
		return myFrame;
	}

	public void setMyFrame(JFrame myFrame) {
		this.myFrame = myFrame;
	}

	public void stopExecution() {
		modeIndicator = -1;
		controller.terminateExecution();
		GUIStatic.printExecution(myFrame, lossAgent, lossModel, this, renderedMap);
	}
	
	public void insertDirFileModel() {
		GUIStatic.insertDirFileModel(this);
	}
	
	public String getModelPath() {
		return modelPath;
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
			GUIStatic.printExecution(myFrame, lossAgent, lossModel, this, renderedMap);
		else if(modeIndicator == 1) 
			GUIStatic.printTrainNoMap(myFrame, lossAgent, lossModel, this);
		else if(modeIndicator == 2) 
			GUIStatic.printTrainWithMap(myFrame, lossAgent, lossModel, renderedMap, this);	
	}
	
	
	public void setLossAgent(double ar) {
		
		lossAgent = new JLabel("Loss Agent: " + String.valueOf(ar));
		renderRightState();		
	}
	
	
	public void setLossModel(double model) {
		
		lossAgent = new JLabel("Loss Model: " + String.valueOf(model));
		renderRightState();
	}
	
	
	public void exit() {
		controller.exit();
	}
	
	
	public void toggleFromExecToTrain() {
		modeIndicator = 1;
		controller.terminateExecution();
		GUIStatic.askThreadNumberBeforeTrain(myFrame, this);
	}
	
	
	public void toggleFromTrainToExec() {
		try {
			modeIndicator = 0;
			controller.toggleFromTrainToExec();
			GUIStatic.printExecution(myFrame, lossAgent, lossModel, this, renderedMap);
		}catch(ThreadException e) {
			//TODO
		}
		
	}
	
	
	public void mainMenu() {
		modeIndicator = -1;
		GUIStatic.printMenu(myFrame, this);
	}
	
	
	public void execution() {
		try {
			modeIndicator = 0;
			controller.startExecution();
			GUIStatic.printExecution(myFrame, lossAgent, lossModel, this, renderedMap);
		}catch(ThreadException e) {
			//TODO
		}
		
	}
	
	
	public void trainWithoutMap() {
		try {
			modeIndicator = 1;
			controller.startTraining(threadNumber);
			GUIStatic.printTrainNoMap(myFrame, lossAgent, lossModel, this);
		}catch(ThreadException e) {
			//TODO
		}
		
	}
	
	
	public void trainWithMap() {
		modeIndicator = 2;
		GUIStatic.printTrainWithMap(myFrame, lossAgent, lossModel, renderedMap, this);
	}

	
	public void stopTraining() {
		modeIndicator = -1;
		controller.terminateTraining();
		GUIStatic.printMenu(myFrame, this);
	}
	
	
	public void nextMap(){
		controller.nextMap();
	}
	
	
	public void previousMap() {
		controller.previousMap();
	}

	
	public void setModelpath(String filePath) {
		modelPath = filePath;
	}


	public void setThreadNumber(int th) {
		threadNumber = th;
	}
	
}
