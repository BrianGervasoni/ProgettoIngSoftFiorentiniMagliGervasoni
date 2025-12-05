package gioco.snakeAI;
import java.util.ArrayList;
import java.util.List;

import boxes.*;


public class Snake {
	
	//parti del corpo del serpente
	private List<SnakeBox> body;
	
	//lunghezza del serpente
	private int length;
	
	//mappa di gioco
	private Map map;
	
	//direction del serpente
	private Direction direction;
	
	/**
	 * costruttore della classe Snake
	 * 
	 * @param map mappa di gioco
	 */
	public Snake(Map map) {
		
		this.body = new ArrayList<SnakeBox>();
		this.length = 0;
		this.direction = Direction.Straight;
		this.map = map;
		
	}
	
	/**
	 * metodo usato per l'aggiunta della testa nell'Array body
	 * @param box casella in cui è stat creata la tesat del serpente
	 */
	public void setHead(SnakeBox box) {
		
		body.add(box);
		
	}
	/**
	 * metodo usato per restituire l'array body del serpente
	 * 
	 * @return l'array body
	 */
	
	public List getBody() {
		
		return body;
		
	}
	
	/**
	 * metodo per assegnare una direzione alla testa del serpente
	 * 
	 * @param dir direzione da assegnare alla testa del serpente
	 */
	public void setDirection(Direction dir) {
		
		this.direction = dir;
	}
	
	/**
	 * metodo che aggiunge un pezzo del corpo al serpente
	 * 
	 * @param piece pezzo da aggiungere all'array body del serpente
	 */
	public void addPiece(SnakeBox piece) {
		
		body.add(piece);
	}

	/**
	 * metodo usato per recuperare la parte del corpo del serpente alla posizione i dell'array body
	 * 
	 * @param i posizione i
	 * @return la parte del corpo
	 */
	public SnakeBox getBodyPiece(int i) {
		
		return body.get(i);
		
	}

	
	/**
	 * metodo per ritornare la lunghezza del serpente
	 * 
	 * @return la lunghezza del serpente
	 */
	public int getLength() {
		return length;
	}
	
	
	/**
	 * metodo per allungare il serpente
	 */
	public void addLength() {
		this.length++;
	}


	/**
	 * metodo che ritorna la lunghezza attuale del serpente
	 * 
	 * @param length lunghezza attuale del serpente
	 */
	public void setLength(int length) {
		this.length = length;
	}



	/**
	 * metodo usato per muovere il serpente lungo la mappa
	 * 
	 * @param dir direzione del movimento
	 * @return true(no collisioni) o false(si collisioni)
	 */
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
		
		
		//controllo collisione con la mela
		appleEaten = map.checkAppleCollision();	
		
		if(appleEaten == true) {
		
			SnakeBox newLast = new SnakeBox(SnakeBody.Tail, X, Y, body.get(body.size() -1));
			body.add(newLast);
			map.setBox(newLast, X, Y);
			appleEaten = false;
			
			map.setApple();
		}
		
		//controllo collisione con le pareti della mappa
		if(body.get(0).getXcoordinate() == 0 || body.get(0).getXcoordinate() == Map.X-1 ||body.get(0).getYcoordinate() == 0 || body.get(0).getYcoordinate() == Map.Y-1) {
			
			return false;
			
		}
		
		//controllo collisione con se stesso
		for(int i = 1; i < body.size(); i ++) {
			
			if(body.get(0).getXcoordinate() == body.get(i).getXcoordinate() && body.get(0).getYcoordinate() == body.get(i).getYcoordinate()) {
				return false;
			}
				
		}
		
		
		
		return true;
		
	}
	
	/**
	 * metodo per fare reset del serpente
	 */
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

