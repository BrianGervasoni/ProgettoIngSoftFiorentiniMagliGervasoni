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
	public static void initialize(Map map) {
		
		map.initSnakeBody();			// questo piazza la testa iniziale
		map.setApple();
	}
	
	/**
	 * metodo per l'secuzione continua del gioco
	 * 
	 * @param map mappa di gioco
	 */
	public static void gameLoop(Map map) {
		map.gameLoop();
		
	}
	
	/**
	public static void close(Map map) {
		
		
	}
	
	*/
	
	
	/**
	 * metodo per la visualizzazione della mappa nella console
	 * 
	 * @param map mappa di gioco
	 */
	public static void visualize(Map map) {
		
		for(int i = 0; i < map.X; i++) {			// i sono le righe		questo for fa passare le righe
			for(int y = 0; y < map.Y; y++) {		// y sono le colonne	questo for fa passare le colonne
				
				if(y == 0 && i != 0) {
					System.out.println();
				}
				
				if(((y==0) && (i==0))||((y==0) && (i==map.X-1))||((y==map.Y-1) && (i==0))||((i==map.X-1) && (y==map.Y-1)))
					System.out.print("+");
				else if((i==0)||(i==map.X-1))
					System.out.print("--");
				else if((y==0)||(y==map.Y-1))
					System.out.print("|");
				else
					map.getBox(i, y).visual();
				
			}
		}
		
		System.out.println();
		System.out.println();
		
		/**
		for(int i = 0; i < map.X; i++) {
			for(int y = 0; y < map.Y; y++) {
				
				if(map.getBox(i, y) instanceof EmptyBox) {
					
					if(((EmptyBox) map.getBox(i, y)).getEnum()  == MapElem.Wall)
						System.out.print("W");
					else System.out.print("E");
					
				}else if(map.getBox(i, y) instanceof SnakeBox)
					System.out.print("S");
			}
			
			System.out.println();
			
		}
		*/
		
		
	}
	
	
	/**
	 * Metodo principale per l'esecuzione del programma
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map map = new Map();
		
		
		initialize(map);			// a posto!!!!!!!
		visualize(map);
		gameLoop(map);
		
		
		
		
		//close(map);

	}
	
	
	
		
	

}
