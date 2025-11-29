package gioco.snakeAI;
import boxes.*;

public class Map {

	private Box[][] box;
	private Snake snake;
	
	
	public Map() {
		this.box = new Box[11][11];
		
		for(int i = 0; i < 10; i++) {
			for(int k = 0; k < 10; k++) {
				if(i == 0 || i == 10 || k == 0 || k == 10) {
					this.box[i][k] = new EmptyBox(MapElem.Wall);
				}
				else {
					this.box[i][k] = new EmptyBox(MapElem.Empty);
				}
			}
		}
		
	}
	
	public void updateState(Direction dir) {
		
		
	}
	
	public boolean checkAppleCollision() {
		
		
		return true;
		
	}
	
	public boolean checkVictory() {
		
		return true;
	}
	
	public boolean checkDefeat() {
		
		return true;
	}
	
	public boolean setApple() {
		
		return false;
	}
	
	public void changeBoxType() {
		
		
	}
	
	public void reset() {
		
		
	}
	
	public void addSnakeBody() {
		
		
	}
	
	
	
}
