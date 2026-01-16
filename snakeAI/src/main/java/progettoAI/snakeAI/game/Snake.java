package progettoAI.snakeAI.game;
import java.util.ArrayList;
import java.util.List;

import progettoAI.snakeAI.boxes.*;


public class Snake {
	
	//parti del corpo del serpente
	private ArrayList<SnakeBox> body;
	
	/**
	 * costruttore della classe Snake
	 * 
	 * @param map mappa di gioco
	 */
	public Snake() {
		
		this.body = new ArrayList<SnakeBox>();
		setHead(new SnakeBox(SnakeBody.HEAD, -1, -1));
		addPiece(new SnakeBox(SnakeBody.TAIL, -1, -1));
		
	}
	
	/**
	 * metodo usato per l'aggiunta della testa nell'Array body
	 * @param box casella in cui è stat creata la tesat del serpente
	 */
	public void setHead(SnakeBox box) {
		box.setBodyType(SnakeBody.HEAD);
		
		if(body.isEmpty())
			body.add(0,box);
		else {
			if(body.size() == 1) 
				body.get(0).setBodyType(SnakeBody.TAIL);
			else
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
		
		int NCoordX = this.body.get(0).getXcoordinate();
		int NCoordY = this.body.get(0).getYcoordinate();
		
		int X = this.body.get(0).getXcoordinate();
		int Y = this.body.get(0).getYcoordinate();
		
		//controllo dove si stia muovendo al testa
		
		//si stava muovendo verso il basso
		if(X - this.body.get(1).getXcoordinate() == 1) {
			
			if(dir == Direction.RIGHT) {
			
				NCoordY = this.body.get(0).getYcoordinate() - 1;
	
			
			}else if(dir == Direction.LEFT) {
				
				NCoordY = this.body.get(0).getYcoordinate() + 1;

				
			}else {
				
				NCoordX = this.body.get(0).getXcoordinate() + 1;			//le X sono le righe

				
			}
			
		}
		
		//si stava muovendo verso l'alto
		if(X - this.body.get(1).getXcoordinate() == -1) {
			
			if(dir == Direction.RIGHT) {
			
				NCoordY = this.body.get(0).getYcoordinate() + 1;

			
			}else if(dir == Direction.LEFT) {
				
				NCoordY = this.body.get(0).getYcoordinate() - 1;

			}else {
				
				NCoordX = this.body.get(0).getXcoordinate() - 1;

				
			}
			
		}
		
		//si stava muovendo verso la destra
		if(Y - this.body.get(1).getYcoordinate() == 1) {
			
			if(dir == Direction.RIGHT) {
			
				NCoordX = this.body.get(0).getXcoordinate() + 1;
			
			}else if(dir == Direction.LEFT) {
				
				NCoordX = this.body.get(0).getXcoordinate() - 1;

				
			}else {
				
				NCoordY = this.body.get(0).getYcoordinate() + 1;

				
			}
			
		}
		
		//si stava muovendo verso la sinistra
		if(Y - this.body.get(1).getYcoordinate() == -1) {
			
			if(dir == Direction.RIGHT) {
			
				NCoordX = this.body.get(0).getXcoordinate() - 1;
			
			}else if(dir == Direction.LEFT) {
				
				NCoordX = this.body.get(0).getXcoordinate() + 1;

				
			}else {
				
				NCoordY = this.body.get(0).getYcoordinate() - 1;

				
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

