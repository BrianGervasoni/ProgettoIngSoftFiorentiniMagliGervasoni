package gioco.snakeAI;
import boxes.*;

public class Map {
	
	 public static final int X = 12;
	 public static final int Y = 12;

	private Box[][] box;
	private Snake snake;
	
	
	public Map() {
		this.box = new Box[X][Y];

		
		for(int i = 0; i < X ; i++) {
			for(int k = 0; k < Y ; k++) {
				if(i == 0 || i == X-1 || k == 0 || k == Y-1) {
					this.box[i][k] = new EmptyBox(MapElem.Wall, i, k);
					
				}
				else {
					this.box[i][k] = new EmptyBox(MapElem.Empty, i , k);
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

	public Box getBox(int X, int Y) {
		return this.box[X][Y];
	}

	public void setBox(Box box, int X, int Y) {
		this.box[X][Y] = box;
	}
	
	public void vis(int X, int Y) {
		System.out.println(((EmptyBox) box[X][Y]).getElem());
		
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public static int getX() {
		return X;
	}

	public static int getY() {
		return Y;
	}
	
	
	
}
