package snakeGame;

import enumSnake.BoxType;
import enumSnake.Food;

public class AppleBox extends Box{
	
	private Food apple;

	public AppleBox(int x, int y) {
		super(x, y);
		
	}

	@Override
	public BoxType getElementType() {
		// TODO Auto-generated method stub
		return apple;
	}

	@Override
	public void setElementType(BoxType boxType) {
		this.apple = (Food)boxType;
	}
}
