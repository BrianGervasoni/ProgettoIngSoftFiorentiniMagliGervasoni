package gioco.snakeAI;
import java.util.ArrayList;
import java.util.List;

import boxes.*;


public class Snake {
	
	private List<SnakeBox> body;
	
	private int length;
	
	private Direction direction;
	
	public Snake() {
		
		this.body = new ArrayList<SnakeBox>();
		this.length = 0;
		this.direction = Direction.Straight;
		
	}
	
	
	public void setHead(SnakeBox box) {
		
		body.add(box);
		
	}
	
	public List getBody() {
		
		return body;
		
	}
	
	
	public void setDirection(Direction dir) {
		
		this.direction = dir;
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
		
		int NCoordX = 0;
		int NCoordY = 0;
		
		int X = this.body.get(0).getXcoordinate();
		int Y = this.body.get(0).getYcoordinate();
		
		//si stava muovendo verso il basso
		if(X - this.body.get(1).getXcoordinate() == 1) {
			
			if(dir == Direction.Right) {
			
				NCoordY = this.body.get(0).getYcoordinate() - 1;
			
				this.body.get(0).setYcoordinate(NCoordY);
			
			}else if(dir == Direction.Left) {
				
				NCoordY = this.body.get(0).getYcoordinate() + 1;
				
				this.body.get(0).setYcoordinate(NCoordY);
			}else {
				
				NCoordX = this.body.get(0).getXcoordinate() + 1;
				this.body.get(0).setXcoordinate(NCoordX);
				
			}
			
		}
		
		//si stava muovendo verso l'alto
		if(X - this.body.get(1).getXcoordinate() == -1) {
			
			if(dir == Direction.Right) {
			
				NCoordY = this.body.get(0).getYcoordinate() + 1;
			
				this.body.get(0).setYcoordinate(NCoordY);
			
			}else if(dir == Direction.Left) {
				
				NCoordY = this.body.get(0).getYcoordinate() - 1;
				
				this.body.get(0).setYcoordinate(NCoordY);
			}else {
				
				NCoordX = this.body.get(0).getXcoordinate() - 1;
				this.body.get(0).setXcoordinate(NCoordX);
				
			}
			
		}
		
		//si stava muovendo verso la destra
		if(Y - this.body.get(1).getYcoordinate() == 1) {
			
			if(dir == Direction.Right) {
			
				NCoordX = this.body.get(0).getXcoordinate() + 1;
			
				this.body.get(0).setXcoordinate(NCoordX);
			
			}else if(dir == Direction.Left) {
				
				NCoordX = this.body.get(0).getXcoordinate() - 1;
				
				this.body.get(0).setXcoordinate(NCoordX);
				
			}else {
				
				NCoordY = this.body.get(0).getYcoordinate() + 1;
				this.body.get(0).setYcoordinate(NCoordY);
				
			}
			
		}
		
		//si stava muovendo verso la sinistra
		if(Y - this.body.get(1).getYcoordinate() == -1) {
			
			if(dir == Direction.Right) {
			
				NCoordX = this.body.get(0).getXcoordinate() - 1;
			
				this.body.get(0).setXcoordinate(NCoordX);
			
			}else if(dir == Direction.Left) {
				
				NCoordX = this.body.get(0).getXcoordinate() + 1;
				
				this.body.get(0).setXcoordinate(NCoordX);
				
			}else {
				
				NCoordY = this.body.get(0).getYcoordinate() - 1;
				this.body.get(0).setYcoordinate(NCoordY);
				
			}
			
		}
		
		int A = 0;
		int B = 0;
		
		for(int i = 1; i < body.size(); i++) {
			
			A = body.get(i).getXcoordinate();
			B = body.get(i).getYcoordinate();
			
			body.get(i).setXcoordinate(X);
			body.get(i).setYcoordinate(Y);
			
			X = A;
			Y = B;
		}
		
		
		
		
		
	}
	
	
	public void reset() {
		
		
	}


	

}

