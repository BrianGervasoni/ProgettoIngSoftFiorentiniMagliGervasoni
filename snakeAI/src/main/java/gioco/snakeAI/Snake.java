package gioco.snakeAI;
import java.util.ArrayList;
import java.util.List;

import boxes.*;


public class Snake {
	
	private List<SnakeBox> body;
	
	private int length;
	
	
	public Snake() {
		
		this.body = new ArrayList<SnakeBox>();
		this.length = 0;
	}
	
	
	public void setHead(SnakeBox box) {
		
		body.add(box);
		
	}
	
	public void addPiece(SnakeBox piece) {
		
		body.add(piece);
	}

	
	public SnakeBox getBodyPiece(int i) {
		
		return body.get(i);
		
	}

	
	
	public int getLength() {
		return length;
	}




	public void setLength(int length) {
		this.length = length;
	}




	public void move(Direction dir) {
		
		
	}
	
	public void reset() {
		
		
	}


	

}

