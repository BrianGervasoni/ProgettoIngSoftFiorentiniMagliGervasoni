package snakeGame;

import java.util.ArrayList;
import java.util.List;

import enumSnake.BoxType;
import enumSnake.Snake;

public class SnakeBox extends Box{
	
	private Snake elementType; 
	public static List<SnakeBox> snake = new ArrayList<SnakeBox>();
	
	public SnakeBox(int x, int y) {
		
		super(x, y);
	}
	
	@Override
	public BoxType getElementType() {
		// TODO Auto-generated method stub
		return elementType;
	}

	@Override
	public void setElementType(BoxType boxType) {
		this.elementType = (Snake)boxType;
		snake.add(this);
	}
	
	public SnakeBox getNext(int i) {
		return snake.get(i + 1);
	}
	
}
