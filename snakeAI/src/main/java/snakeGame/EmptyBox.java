package snakeGame;

import enumSnake.BoxType;
import enumSnake.MapElem;

public class EmptyBox extends Box{
 
	private MapElem mapElem;
	
	public EmptyBox(int x, int y) {
		super(x,y);
 }

	@Override
	public BoxType getElementType() {
		// TODO Auto-generated method stub
		return mapElem;
	}

	@Override
	public void setElementType(BoxType boxType) {
		this.mapElem = (MapElem) boxType;
	}
	
}
