package progettoAI.snakeAI.SnakeLogic.game;

import javax.swing.JOptionPane;

import progettoAI.snakeAI.AI.hyperparameters.Hyperparameters;
import progettoAI.snakeAI.SnakeLogic.boxes.*;
import progettoAI.snakeAI.errorHandler.VoidBodyException;

public class GameMain {

	Map map;
	int tLastApple = 0;
	
	
	public GameMain() {
		
		this.map = new Map();
		
	}
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	
	public int getTickLastApple() {
		return tLastApple;
	}

	public void setTickLastApple(int tLastApple) {
		this.tLastApple = tLastApple;
	}
	
	/**
	 * metodo che permette di vuovere il serpente secondo una direzione scelta
	 * @param dir direzione da assegnare
	 */
	public void giveDirections(Direction dir) {
		
		try {
			map.makeSnakeMove(dir);
		}catch(VoidBodyException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		if(map.getAppleCollision()) {
			tLastApple = 0;
		}else {
			tLastApple++;
		}
		map.addTick();
		
	}
	
	/**
	 * se ritorna false la partita è ancora in corso
	 * se ritorna vero se il giocatore ha perso oppure ha vinto
	 * @return
	 */
	public boolean finish() {
		
		return map.checkDefeat() || map.checkVictory();
	}
	
	/**
	 * metoto per resettare la mappa 
	 * 
	 */
	public void reset() {
		tLastApple = 0;
		this.map = new Map();
	}
	
	/**
	 * metodo che ritorna l'identificativo in memoria della mappa
	 * @return 
	 */
	public String toStringMap() {
		
		return map.toString();
		
	}
	
	/**
	 * metodo che ritorna l'identificativo in memoria del GameMain
	 * @return 
	 */
	public String toStringGameMain() {
		
		return this.toString();
		
	}
	
	public double getMinDistanceToWall() {
	    int x = this.getMap().getSnake().getBodyPiece(0).getXcoordinate();
	    int y = this.getMap().getSnake().getBodyPiece(0).getYcoordinate();

	    int distLeft   = x;
	    int distRight  = this.getMap().X - 1 - x;
	    int distTop    = y;
	    int distBottom = this.getMap().Y - 1 - y;

	    return Math.min(Math.min(distLeft, distRight), Math.min(distTop, distBottom));
	}

}
