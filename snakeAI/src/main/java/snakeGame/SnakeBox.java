package snakeGame;

import java.util.List;

public class SnakeBox extends Box{
	
	List<Snake> snake; 
	
	public SnakeBox(int x, int y) {
		
		super(x, y);
		snake.add(Snake.Head);
	}

	public List<Snake> getSnake() {
		return snake;
	}

	public void setSnake(List<Snake> snake) {
		this.snake = snake;
	}
}
