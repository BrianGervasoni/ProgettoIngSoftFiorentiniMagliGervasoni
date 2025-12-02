package snakeGame;

import enumSnake.BoxType;
import enumSnake.Snake;

public class SnakeBox extends Box{
	
	private Snake elementType; 
	
	public SnakeBox(int x, int y) {
		
		super(x, y);
		this.setElementType(Snake.Head);
	}
	
	@Override
	public BoxType getElementType() {
		// TODO Auto-generated method stub
		return elementType;
	}

	@Override
	public void setElementType(BoxType boxType) {
		this.elementType = (Snake)boxType;
	}
}
