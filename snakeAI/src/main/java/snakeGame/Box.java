package snakeGame;

import enumSnake.BoxType;

public abstract class Box {
	
	int x;
	int y;
	
	public Box(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public abstract BoxType getElementType(); 
	public abstract void setElementType(BoxType boxType); 
	
}
