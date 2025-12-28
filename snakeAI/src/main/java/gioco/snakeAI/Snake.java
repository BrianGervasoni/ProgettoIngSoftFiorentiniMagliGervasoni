package gioco.snakeAI;
import java.util.ArrayList;
import java.util.List;

import boxes.*;


public class Snake {
	
	//parti del corpo del serpente
	private List<SnakeBox> body;
	
	/**
	 * costruttore della classe Snake
	 * 
	 * @param map mappa di gioco
	 */
	public Snake() {
		
		this.body = new ArrayList<SnakeBox>();
		
	}
	
	/**
	 * metodo usato per l'aggiunta della testa nell'Array body
	 * @param box casella in cui è stat creata la tesat del serpente
	 */
	public void setHead(SnakeBox box) {
		if(body.isEmpty())
			body.add(0,box);
		else {
			body.get(0).setBodyType(SnakeBody.BODY);
			body.add(0,box);
		}	
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
	public int getLenght() {
		return body.size();
		
	}

	/**
	 * metodo usato per muovere il serpente lungo la mappa
	 * 
	 * @param dir direzione del movimento
	 * @return true(no collisioni) o false(si collisioni)
	 */
	public void move(Direction dir) {
		
		int NCoordX = 0;
		int NCoordY = 0;
		
		int X = this.body.get(0).getXcoordinate();
		int Y = this.body.get(0).getYcoordinate();
		
		//controllo dove si stia muovendo al testa
		
		//si stava muovendo verso il basso
		if(X - this.body.get(1).getXcoordinate() == 1) {
			
			if(dir == Direction.RIGHT) {
			
				NCoordY = this.body.get(0).getYcoordinate() - 1;
				this.body.get(0).setYcoordinate(NCoordY);
			
			}else if(dir == Direction.LEFT) {
				
				NCoordY = this.body.get(0).getYcoordinate() + 1;
				this.body.get(0).setYcoordinate(NCoordY);
				
			}else {
				
				NCoordX = this.body.get(0).getXcoordinate() + 1;
				this.body.get(0).setXcoordinate(NCoordX);
				
			}
			
		}
		
		//si stava muovendo verso l'alto
		if(X - this.body.get(1).getXcoordinate() == -1) {
			
			if(dir == Direction.RIGHT) {
			
				NCoordY = this.body.get(0).getYcoordinate() + 1;
				this.body.get(0).setYcoordinate(NCoordY);
			
			}else if(dir == Direction.LEFT) {
				
				NCoordY = this.body.get(0).getYcoordinate() - 1;
				this.body.get(0).setYcoordinate(NCoordY);
			}else {
				
				NCoordX = this.body.get(0).getXcoordinate() - 1;
				this.body.get(0).setXcoordinate(NCoordX);
				
			}
			
		}
		
		//si stava muovendo verso la destra
		if(Y - this.body.get(1).getYcoordinate() == 1) {
			
			if(dir == Direction.RIGHT) {
			
				NCoordX = this.body.get(0).getXcoordinate() + 1;
				this.body.get(0).setXcoordinate(NCoordX);
			
			}else if(dir == Direction.LEFT) {
				
				NCoordX = this.body.get(0).getXcoordinate() - 1;
				this.body.get(0).setXcoordinate(NCoordX);
				
			}else {
				
				NCoordY = this.body.get(0).getYcoordinate() + 1;
				this.body.get(0).setYcoordinate(NCoordY);
				
			}
			
		}
		
		//si stava muovendo verso la sinistra
		if(Y - this.body.get(1).getYcoordinate() == -1) {
			
			if(dir == Direction.RIGHT) {
			
				NCoordX = this.body.get(0).getXcoordinate() - 1;
				this.body.get(0).setXcoordinate(NCoordX);
			
			}else if(dir == Direction.LEFT) {
				
				NCoordX = this.body.get(0).getXcoordinate() + 1;
				this.body.get(0).setXcoordinate(NCoordX);
				
			}else {
				
				NCoordY = this.body.get(0).getYcoordinate() - 1;
				this.body.get(0).setYcoordinate(NCoordY);
				
			}
			
		}
		
					
		//controllo dove si stia muovendo il resto del corpo
		
		
		body.add(0, (new SnakeBox(SnakeBody.HEAD, NCoordX, NCoordY)));
		body.get(1).setBodyType(SnakeBody.BODY);
		
	}
	
	/**
	 * metodo per la rimozione della coda dalla lista body
	 */
	public void removeTail() {
		
		body.remove(body.size()-1);
		body.get(body.size()-1).setBodyType(SnakeBody.TAIL);
		
	}
	
	
	/**
	 * metodo per fare reset del serpente
	 */
	public void reset() {
		
		this.body = new ArrayList<SnakeBox>();
	}
	

}

