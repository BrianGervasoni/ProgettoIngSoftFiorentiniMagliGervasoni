package gioco.snakeAI;
import java.util.ArrayList;
import java.util.List;

import boxes.*;


public class Snake {
	
	private List<SnakeBox> body;
	
	private int length;
	
	private Map map;
	
	private Direction direction;
	
	public Snake(Map map) {
		
		this.body = new ArrayList<SnakeBox>();
		this.length = 0;
		this.direction = Direction.Straight;
		this.map = map;
		
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
	
	public void addLength() {
		this.length++;
	}



	public void setLength(int length) {
		this.length = length;
	}




	public Boolean move(Direction dir) {
		
		int NCoordX = 0;
		int NCoordY = 0;
		
		Boolean appleEaten = false;
		
		int X = this.body.get(0).getXcoordinate();
		int Y = this.body.get(0).getYcoordinate();
		
		//controllo dove si stia muovendo al testa
		
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
		
		appleEaten = map.checkAppleCollision();	
					
		//controllo dove si stia muovendo il resto del corpo
		
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
		
		
		
		
		if(appleEaten == true) {
		
			SnakeBox newLast = new SnakeBox(SnakeBody.Tail, X, Y, body.get(body.size() -1));
			body.add(newLast);
			map.setBox(newLast, X, Y);
			appleEaten = false;
			
			map.setApple();
		}
		
		
		if(body.get(0).getXcoordinate() == 0 || body.get(0).getXcoordinate() == Map.X-1 ||body.get(0).getYcoordinate() == 0 || body.get(0).getYcoordinate() == Map.Y-1) {
			
			return false;
			
		}
		
		for(int i = 1; i < body.size(); i ++) {
			
			if(body.get(0).getXcoordinate() == body.get(i).getXcoordinate() && body.get(0).getYcoordinate() == body.get(i).getYcoordinate()) {
				return false;
			}
				
		}
		
		
		
		return true;
		
	}
	
	
	public void reset() {
		
		
	}

	
	
	
	
	
	
	//SOLO PER FINI DI TESTING!!!!!
	
	public int getXcoordinateBodyPiece(int i) {
		
		return body.get(i).getXcoordinate();
	}
	
	public int getYcoordinateBodyPiece(int i) {
		
		return body.get(i).getYcoordinate();
	}

	

}

