package userInterface;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	private String hyperparametersPath;
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
		modelPath = null;
		hyperparametersPath = null;
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
		try {
			controller.terminateExecution();
		}catch(ThreadException e) {
			GUIStatic.sendWarning(myFrame, "Si è verificato un problema nei thread nell'interrompere l'esecuzione: " + e.getMessage());
		}
		
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
		try {
			controller.exit();
		}catch(ThreadException e) {
			GUIStatic.sendWarning(myFrame, "Si è verificato un problema nei thread nel chiudere il programma: " + e.getMessage());
		}catch(IOException e) {
			GUIStatic.sendWarning(myFrame, "Si è verificato un problema di i/o nel chiudere il programma: " + e.getMessage());

		}
		
	}
	
	
	public void toggleFromExecToTrain() {
		modeIndicator = 1;
		try {
			controller.terminateExecution();
			GUIStatic.askThreadNumberBeforeTrain(myFrame, this);
		}catch(ThreadException e) {
			GUIStatic.sendWarning(myFrame, "Si è verificato un problema nei thread nel fare il toggle execution=>training: " + e.getMessage());
		}
		
		
	}
	
	
	public void toggleFromTrainToExec() {
		try {
			modeIndicator = 0;
			controller.toggleFromTrainToExec();
			GUIStatic.printExecution(myFrame, lossAgent, lossModel, this, renderedMap);
		}catch(ThreadException e) {
			GUIStatic.sendWarning(myFrame, "Si è verificato un problema nei thread nel fare il toggle: " + e.getMessage());
		}catch(IOException e) {
			GUIStatic.sendWarning(myFrame, "Si è verificato un problema di input output nel fare il toggle: " + e.getMessage());

		}
		
	}
	
	
	public void mainMenu() {
		modeIndicator = -1;
		GUIStatic.printMenu(myFrame, this);
	}
	
	
	public void execution() {
	
		System.out.println(getModelPath());
		
		if(getModelPath() != null) {
			
			try {
				modeIndicator = 0;
				controller.startExecution(modelPath);
				GUIStatic.printExecution(myFrame, lossAgent, lossModel, this, renderedMap);
			}catch(ThreadException e) {
				GUIStatic.sendWarning(myFrame, "C'è un problema di thread a far partire l'esecuzione: " + e.getMessage());
			}catch(IOException e) {
				GUIStatic.sendWarning(myFrame, "C'è un problema di input output a far partire l'esecuzione: " + e.getMessage());

			}
		
		}else GUIStatic.sendWarning(myFrame, "Non è stato selezionato un file model, oppure non è leggibile");
		
	}
	
	
	public void trainWithoutMap() {

		if(getModelPath() == null)
			GUIStatic.sendWarning(myFrame, "Non è stato selezionato un file model, oppure non è leggibile");

		else{
			
			GUIStatic.askThreadNumberBeforeTrain(myFrame, this);
			try {
				modeIndicator = 1;
				controller.startTraining(threadNumber, modelPath);
				GUIStatic.printTrainNoMap(myFrame, lossAgent, lossModel, this);
			}catch(ThreadException e) {
				GUIStatic.sendWarning(myFrame, "C'è un problema a far partire l'allenamento: " + e.getMessage());
			}catch(IOException e) {
				GUIStatic.sendWarning(myFrame, "C'è un problema di input output: " + e.getMessage());
			}
		
		}
		
		
	}
	
	
	public void trainWithMap() {
		modeIndicator = 2;
		GUIStatic.printTrainWithMap(myFrame, lossAgent, lossModel, renderedMap, this);
	}

	
	public void stopTraining() {
		modeIndicator = -1;
		try {
			controller.terminateTraining();
		}catch(ThreadException e) {
			GUIStatic.sendWarning(myFrame, "C'è un problema con l'interruzione dei thread dell'allenamento: " + e.getMessage());
		}catch(IOException e) {
			GUIStatic.sendWarning(myFrame, "C'è un problema di input output con l'interruzione dell'allenamento: " + e.getMessage());

		}
		
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
	
	public void gestisciErroreAgent(Throwable e) {
		GUIStatic.sendWarning(myFrame, "Si è verificato un problema con i thread agent:" + e.getMessage());
	}
	
	public void gestisciErroreModel(Throwable e) {
		GUIStatic.sendWarning(myFrame, "Si è verificato un problema con i thread model:"+ e.getMessage());
	}

	public void setHyperParamPath(String filePath) {
		hyperparametersPath = filePath;
	}
	
	public String getHyperParamPath() {
		return hyperparametersPath;
	}

	public void createOrChooseModelFile() {
		
		GUIStatic.createNewModelOrChooseModel(myFrame, this);
	}

	
	

	public void editHyperParameters() {
		
		GUIStatic.loadHyperParamFile(myFrame, this);
		
		if(getHyperParamPath() == null)
			GUIStatic.sendWarning(myFrame, "Il file degli hyperParameters non è valido.");
		else GUIStatic.insertHyperParameters(myFrame, this);
	}

	
	public void newModelFile(String text) {
		
        File newModel = new File("modelli/" + text + ".json");

        try{
        	FileWriter writer = new FileWriter(newModel);
        } catch (IOException e) {
            GUIStatic.sendWarning(myFrame, "Errore durante la creazione del file: " + e.getMessage());
        }
        
        
        System.out.println(newModel.getAbsolutePath());
        setModelpath(newModel.getAbsolutePath());
        mainMenu();
		
	}

	
	
	
	public void createNewModelFile() {
		GUIStatic.createNewFileModel(myFrame, this);
		
	}
	
}
