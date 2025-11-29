package gioco.snakeAI;

public class GameMain {

	
	public static void initialize(Map map) {
		
	}
	
	public static void gameLoop(Map map) {
	
		
	}
	
	public static void close(Map map) {
		
		
	}
	
	public static void visualize(Map map) {
		
		for(int i = 0; i < map.X; i++) {
			for(int y = 0; y < map.Y; y++) {
				
				if(y == 0 && i != 0) {
					System.out.println();
				}
				
				map.getBox(i, y).visual();
				
			}
		}
		
	}
	
	
	
	public static void main(String[] args) {
		
		Map map = new Map();
		visualize(map);
		
		//initialize(map);
		//gameLoop(map);
		//close(map);

	}
	
	
	
		
	

}
