package guiStatic;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import errorHandler.ThreadException;
import fileManager.JsonFileManager;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;
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
				
				ui.editHyperParameters();
			}});	
		
		
		JButton buttonSelectModel = new JButton("Seleziona modello");
		buttonSelectModel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ui.createOrChooseModelFile();
			}});	
		
		
		JButton buttonStartExecution = new JButton("Avvia esecuzione");
		buttonStartExecution.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ui.execution();
				
			}});	
		
		
		/**
		 * Di default parte l'allenamento senza mappa
		 */
		JButton buttonStartTraining = new JButton("Avvia allenamento");
		buttonStartTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ui.trainWithoutMap();
			}});	
		
		
		JButton buttonExit = new JButton("Esci");
		buttonExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ui.exit();
				ui.getMyFrame().dispose();
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
	
	
	
	public static void loadHyperParamFile(JFrame jf, UserInterface ui) {
		
		JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("File JSON", "json");
        fileChooser.setFileFilter(filter);

		fileChooser.setCurrentDirectory(new File("iperparametri"));
		
		
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            ui.setHyperParamPath(filePath);
        } else {
        	ui.setHyperParamPath(null);
        }
		
	}


	public static void insertDirFileModel(UserInterface ui) {
		
		JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("File JSON", "json");
        fileChooser.setFileFilter(filter);

		fileChooser.setCurrentDirectory(new File("modelli"));
		
		
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            if (filePath.toLowerCase().endsWith(".json")) {
                filePath = filePath.substring(0, filePath.length() - 5);
            }

            ui.setModelpath(filePath);
        } else {
        	ui.setModelpath(null);
        }
        
        
        
        System.out.println(ui.getModelPath());
        ui.mainMenu();
		
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
				ui.trainWithMap();
			}});
		
		
		JButton buttonStopTraining = new JButton("Interrompi l'allenamento");
		buttonStopTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ui.stopTraining();
			}});
		
		
		JButton buttonToggle = new JButton("Toggle training => Exec");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ui.toggleFromTrainToExec();
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
				ui.hideMap();
			}});
		
		
		JButton buttonStopTraining = new JButton("Interrompi l'allenamento");
		buttonStopTraining.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ui.stopTraining();
			}});

		
		JButton buttonNextThread = new JButton("Thread successivo");
		buttonNextThread.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.nextMap();
				
			}});
		

		JButton buttonPreviousThread = new JButton("Thread precedente");
		buttonPreviousThread.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.previousMap();
				
			}});
		
		
		JButton buttonToggle = new JButton("Toggle training => Exec");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.toggleFromTrainToExec();
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

				ui.stopExecution();

			}});
		
		JButton buttonToggle = new JButton("Toggle Exec => Train");
		buttonToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ui.toggleFromExecToTrain();
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
		
		try {
			JsonFileManager.loadHyperparameters(ui.getHyperParamPath());
		}catch(IOException e) {
			GUIStatic.sendWarning(myFrame, "C'è un problema di i/o nel caricamento degli iperparametri: " + e.getMessage());
		}
		
		
		JLabel l1 = new JLabel("alphaW");
		//qui bisogna fare un get del valore
		JTextField jtf1 = new JTextField(String.valueOf(Hyperparameters.alphaW), 15);
		
		JLabel l2 = new JLabel("alphaB");
		//qui bisogna fare un get del valore
		JTextField jtf2 = new JTextField(String.valueOf(Hyperparameters.alphaB), 15);
		
		JLabel l3 = new JLabel("epoche");
		//qui bisogna fare un get del valore
		JTextField jtf3 = new JTextField(String.valueOf(Hyperparameters.epoche), 15);
		
		JLabel l4 = new JLabel("minibatchSize");
		//qui bisogna fare un get del valore
		JTextField jtf4 = new JTextField(String.valueOf(Hyperparameters.minibacthSize), 15);
		
		JLabel l5 = new JLabel("discount");
		//qui bisogna fare un get del valore
		JTextField jtf5 = new JTextField(String.valueOf(Hyperparameters.discount), 15);
		
		JLabel l6 = new JLabel("lambda");
		//qui bisogna fare un get del valore
		JTextField jtf6 = new JTextField(String.valueOf(Hyperparameters.lambda), 15);
		
		JLabel l7 = new JLabel("TimeStep");
		//qui bisogna fare un get del valore
		JTextField jtf7 = new JTextField(String.valueOf(Hyperparameters.timeStep), 15);
		
		JLabel l8 = new JLabel("motivation");
		//qui bisogna fare un get del valore
		JTextField jtf8 = new JTextField(String.valueOf(Hyperparameters.motivation), 15);
		
		JLabel l9 = new JLabel("entropyContribution");
		//qui bisogna fare un get del valore
		JTextField jtf9 = new JTextField(String.valueOf(Hyperparameters.entropyContribution), 15);
		
		
		
		JButton buttonAbort = new JButton("buttonAbort");
		buttonAbort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.mainMenu();
			}});
		

		JButton buttonDone = new JButton("Fatto");
		buttonDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				double alphaW = Double.parseDouble(jtf1.getText());
				double alphaB = Double.parseDouble(jtf2.getText());
				double minibatchSize = Double.parseDouble(jtf4.getText());
				double discount = Double.parseDouble(jtf5.getText());
				double lambda = Double.parseDouble(jtf6.getText()); 
				double motivation = Double.parseDouble(jtf8.getText());
				double entropyContribution = Double.parseDouble(jtf9.getText());	
				
				boolean readyToSave = true;
				
				readyToSave = checkBetweenZeroOne(alphaW);
				readyToSave = readyToSave && checkBetweenZeroOne(alphaB);
				readyToSave = readyToSave && checkBetweenZeroOne(minibatchSize);
				readyToSave = readyToSave && checkBetweenZeroOne(discount);
				readyToSave = readyToSave && checkBetweenZeroOne(lambda);
				readyToSave = readyToSave && checkBetweenZeroOne(motivation);
				readyToSave = readyToSave && checkBetweenZeroOne(entropyContribution);
				
				if(readyToSave) {
					Hyperparameters.alphaW = alphaW;
					Hyperparameters.alphaB = alphaB;
					Hyperparameters.discount = discount;
					Hyperparameters.entropyContribution = entropyContribution;
					Hyperparameters.epoche = Integer.parseInt(jtf3.getText());
					Hyperparameters.lambda = lambda;
					Hyperparameters.minibacthSize = minibatchSize;
					Hyperparameters.motivation = motivation;
					Hyperparameters.timeStep = Integer.parseInt(jtf7.getText());
					try {
						JsonFileManager.saveHyperparameters(ui.getHyperParamPath());
					}catch(IOException e) {
						GUIStatic.sendWarning(myFrame, "C'è un problema di i/o nel salvataggio degli iperparametri: " + e.getMessage());
					}
					
					ui.mainMenu();
					
				}else
					JOptionPane.showMessageDialog(myFrame, "Alcuni valori inseriti non sono corretti. Ricontrollare.");

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
	
	
	public static boolean checkBetweenZeroOne(Double db) {
		if(db > 0 && db < 1)
			return true;
		else return false;		
	}


	public static void askThreadNumberBeforeTrain(JFrame myFrame, UserInterface ui) {
		
		GUIStatic.resetFrame(myFrame);
		JPanel th = new JPanel();
		
		
		JTextField field = new JTextField("10", 15);
		JLabel label = new JLabel("Inserire numero di Thread dedicati all'allenamento");
		JButton jbutton = new JButton("Fatto");
		jbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Integer.valueOf(field.getText())>0) {
					ui.setThreadNumber(Integer.valueOf(field.getText()));
					ui.trainWithoutMap();
				}else
					JOptionPane.showMessageDialog(myFrame, "Il numero inserito non è corretto.");	
			}
		});
		
		
		JButton jbutton2 = new JButton("Annulla");
		jbutton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.mainMenu();
			}
		});
		
		th.add(field);
		th.add(label);
		th.add(jbutton);
		th.add(jbutton2);
		myFrame.add(th);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		myFrame.pack();		
		myFrame.setSize(640,480);
		myFrame.setVisible(true);
		
		
	}
	
	
	public static void sendWarning(JFrame jf, String str) {
		JOptionPane.showMessageDialog(jf, str);
	}
	
	
	public static void createNewModelOrChooseModel(JFrame jf, UserInterface ui) {
		
		GUIStatic.resetFrame(jf);
		JPanel jp = new JPanel();
		
		JLabel question = new JLabel("Effettua la seguente selezione:");
		
		JButton jb1 = new JButton("Crea un nuovo file modello");
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.createNewModelFile();
				
			}});
		
		JButton jb2 = new JButton("Seleziona un file modello dal filesystem");
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.insertDirFileModel();
				
			}});
		
		jp.add(question);
		jp.add(jb1);
		jp.add(jb2);
		jf.add(jp);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.pack();		
		jf.setSize(640,480);
		jf.setVisible(true);
	}
	
	
	
	
	public static void createNewFileModel(JFrame jf, UserInterface ui) {
		
		GUIStatic.resetFrame(jf);
		
		JPanel jp = new JPanel();
		JLabel jl = new JLabel("Inserisci il nome che vuoi dare al modello: ");
		JTextField jtf = new JTextField(15);
		
		JButton jb1 = new JButton("Annulla");
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.mainMenu();
	
			}	
		});
		

		JButton jb2 = new JButton("Fatto");
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(jtf.getText().isEmpty())
					GUIStatic.sendWarning(jf, "Il nome del modello non può essere vuoto. Inserire un nome");
				else ui.newModelFile(jtf.getText());
				
			}
			
			
		});
		
		
		jp.add(jl);
		jp.add(jtf);
		jp.add(jb1);
		jp.add(jb2);
		jf.add(jp);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.pack();		
		jf.setSize(640,480);
		jf.setVisible(true);
		
		
		
		
		
	}
	
	
}
