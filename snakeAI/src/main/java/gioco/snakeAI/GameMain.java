package gioco.snakeAI;

import boxes.*;

public class GameMain {

	Map map;
	
	
	public GameMain() {
		
		this.map = new Map();
		
	}
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * costruttore della classe GameMain
	 * 
	 * @param map mappa di gioco
	 */
	public void initialize(Map map) {
		
		map.initSnakeBody();			// questo piazza la testa iniziale
		map.setApple();
	}
	
	
	/**
	 * metodo che permette di vuovere il serpente secondo una direzione scelta
	 * @param dir direzione da assegnare
	 */
	public void giveDirections(Direction dir) {
		map.makeSnakeMove(dir);
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
		
		this.map = new Map();
		initialize(map);
		
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
	

}
