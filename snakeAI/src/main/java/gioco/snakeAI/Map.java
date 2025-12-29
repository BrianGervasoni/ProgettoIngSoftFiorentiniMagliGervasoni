package gioco.snakeAI;
import boxes.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Map {
	
	public static final int X = 12; // grandezza massima del campo di gioco (righe)
	public static final int Y = 12; // grandezza massima del campo di gioco (colonne)

	//il campo di gioco è una matrice X * Y
	private Box[][] box;
	private Snake snake; // il serpente da muovere nel campo di gioco
	private AppleBox apple; // la mela che il serpente deve consumare
	private boolean appleCollision = false;
	
	/**
	 * costruttore della classe Map
	 * 
	 */
	public Map() {
		this.box = new Box[X][Y];
		this.apple = null;

		this.snake = new Snake();
		
		for(int i = 0; i < X ; i++) {
			for(int k = 0; k < Y ; k++) {
				if(i == 0 || i == X-1 || k == 0 || k == Y-1) {
					this.box[i][k] = new EmptyBox(MapElem.WALL, i, k);
					
				}
				else {
					this.box[i][k] = new EmptyBox(MapElem.EMPTY, i , k);
				}
			}
		}
		
	}
	

	public Map(Snake ss) {
		this.box = new Box[X][Y];
		this.apple = null;

		this.snake = ss;
		
		for(int i = 0; i < X ; i++) {
			for(int k = 0; k < Y ; k++) {
				if(i == 0 || i == X-1 || k == 0 || k == Y-1) {
					this.box[i][k] = new EmptyBox(MapElem.WALL, i, k);
					
				}
				else {
					this.box[i][k] = new EmptyBox(MapElem.EMPTY, i , k);
				}
			}
		}
		
	}
	
	
	/**
	 * metodo che controlla la collisione con la mela
	 * @return true(collisione) o false(no collisione)
	 */
	public boolean checkAppleCollision() {
		
		//controlliamo qua la mela
		
				if(snake.getBodyPiece(0).getXcoordinate() == this.getXapple() && snake.getBodyPiece(0).getYcoordinate() == this.getYapple()){
					
					return true;
				}
		
		return false;
		
	}
	/**
	 * metodo per la vittoria
	 * @return
	 */
	public boolean checkVictory() {
		if(allEmptyBoxes().isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * metodo che ritona il numero di righe della mappa(comprese le pareti)
	 * @return
	 */
	public int getRowLenght() {
		
		return X;
	}
	
	/**
	 * metodo che ritona il numero di colonne della mappa(comprese le pareti)
	 * @return
	 */
	public int getColumnLenght() {
		
		return Y;
	}
	
	/**
	 * metodo per sconfitta
	 * se la flag è falsa allora siamo ancora in gioco(we ball)
	 * se la flag è true allora abbiamo colliso
	 * @return
	 */
	public Boolean checkDefeat() {
		//controllo collisione con le pareti della mappa
		if(snake.getBodyPiece(0).getXcoordinate() == 0 || snake.getBodyPiece(0).getXcoordinate() == Map.X-1 || snake.getBodyPiece(0).getYcoordinate() == 0 || snake.getBodyPiece(0).getYcoordinate() == Map.Y-1) {
			
			
			return true;
			
		}
		
		//controllo collisione con se stesso
		for(int i = 1; i < snake.getBody().size(); i ++) {
			
			if(snake.getBodyPiece(0).getXcoordinate() == snake.getBodyPiece(i).getXcoordinate() && snake.getBodyPiece(0).getYcoordinate() == snake.getBodyPiece(i).getYcoordinate()) {
				
				return true;
			}
				
		}
		
		
		return false;
	}
	
	/**
	 * metodo che ritorna la lista contenente tutte le caselle vuote della mappa
	 * 
	 * @return array di caselle vuote della mappa
	 */
	public ArrayList<EmptyBox> allEmptyBoxes() {
		
		ArrayList<EmptyBox> ar = new ArrayList<EmptyBox>();
		for(int i = 0; i < X-1; i++) {
			for(int j = 0; j < Y-1; j++) {
				if(box[i][j].equals(MapElem.EMPTY)) {
					ar.add((EmptyBox)box[i][j]);
				}
				
			}
		}
		
		return ar;
	}
	
	
	/**
	 * 
	 *metodo per la creazione della mela nella mappa
	 *
	 * @return true o false
	 */
	public void setApple() {
		
		ArrayList<EmptyBox> ar = allEmptyBoxes();
		
		if(ar.isEmpty()) {
			return;
		}
		
		Random rand = new Random();
		
		int appleX = 0;
		int appleY = 0;
		int index = 0;
		
		
		index = rand.nextInt(ar.size());
		appleX = ar.get(index).getXcoordinate();
		appleY = ar.get(index).getYcoordinate();
		
			
		this.apple = new AppleBox(Food.APPLE, appleX, appleY);
		setBox(apple, appleX, appleY);	
		
		
	}
	
	/**
	 * metodo per il recupero della coordinata X della mela
	 * @return la coordinata X della mela
	 */
	public int getXapple() {
		
		return apple.getXcoordinate();
	}
	
	/**
	 * metodo per il recupero della coordinata Y della mela
	 * @return la coordinata Y della mela
	 */
	public int getYapple() {
		
		return apple.getYcoordinate();
	}
	
	
	/**
	public void changeBoxType() {
		
		
	}
	*/
	
	
	
	/**
	 * metodo che inizializza il serpente (la testa e la prima parte del corpo)
	 * 
	 */
	public void initSnakeBody() {
		
			//qui inizializziamo la testa
			Random rand = new Random();
			
			int randX = rand.nextInt(1, Map.X-1);
			int randY = rand.nextInt(1, Map.Y-1);
			
			SnakeBox heead = new SnakeBox(SnakeBody.HEAD, randX, randY);
			
			this.setBox(heead, randX, randY);
			snake.setHead(heead);
			
			
			//ora inizializziamo il primo pezzo di corpo

			int firstPieceX = 0;
			int firstPieceY = 0;

			int randA = rand.nextInt(1, 101);

			Boolean nonTrovato = true;
			
			do {
				if((randA <= 25) && (( (EmptyBox) box[randX+1][randY]).getEnum() == MapElem.EMPTY )) {
					firstPieceX = randX + 1;
					firstPieceY = randY;
					nonTrovato = false;
				}else if((randA>25)&&(randA<=50) && (( (EmptyBox) box[randX-1][randY]).getEnum() == MapElem.EMPTY )){
					firstPieceX = randX - 1;
					firstPieceY = randY;
					nonTrovato = false;
				}else if((randA>50)&&(randA<=75) && (( (EmptyBox) box[randX][randY+1]).getEnum() == MapElem.EMPTY )){
					firstPieceX = randX;
					firstPieceY = randY + 1;
					nonTrovato = false;
				}else if((randA>75)&&(randA<=100) && (( (EmptyBox) box[randX+1][randY-1]).getEnum() == MapElem.EMPTY )){
					firstPieceX = randX;
					firstPieceY = randY - 1;
					nonTrovato = false;
				}
				
				randA = rand.nextInt(1, 101);
				
			}while(nonTrovato);
			
			SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.BODY, firstPieceX, firstPieceY);
			setBox(firstBodyPiece, firstPieceX, firstPieceY);
			snake.addPiece(firstBodyPiece);
		
		
	}
	
	
	/**
	 * metodo di goco che si ripete  fino alla collisione del serpente con se stesso o i muri
	 * 
	 */
	public void makeSnakeMove(Direction dir) {
		
		
		/** 
		 * prima cosa: quando il serpente si muove, cambiare tutte le coordinate
		 * seconda cosa: aggiornare il tipo di box verso cui e da cui lo snake si muove
		 * terzo: ristampare il tutto aggiornato 
		 */

			snake.move(dir);
			
			if(checkAppleCollision() == true) {
				appleCollision = true;
				setApple();
			}else{
				appleCollision = false;
				snake.removeTail();
				
			}
			
			checkDefeat();
			resetSnakeBoxes();
			insertSnakeBoxes();
			
	}
	
	
	/**
	 * Ritorna la mela contenuta nella mappa
	 * @return AppleBox
	 */
	public AppleBox getApple() {
		return this.apple;
	}
	
	
	/**
	 * Imposta una mela personalizzata
	 */
	public void setApple(AppleBox app) {
		this.apple = app;
		setBox(app, app.getXcoordinate(), app.getYcoordinate());
	}
	
	
	
	/**
	 * metodo che resetta tutte le Box della mappa e le rende EmptyBox
	 * 
	 */
	public void resetSnakeBoxes() {
		
		for(int i = 1; i < X-1; i++) {
			for (int k = 1; k < Y-1; k++) {
				
				if(box[i][k] instanceof SnakeBox) {
					box[i][k] = new EmptyBox(MapElem.EMPTY, i, k);
					
				}
				
			}
			
		}
		
	}
	
	
	/**
	 * metodo che cambia le EmptyBox in SnakeBox sulla base delle coordiante del body
	 * 
	 */
	public void insertSnakeBoxes() {
		
		for(int i = 0; i < snake.getBody().size(); i++) {
			
			box[snake.getBodyPiece(i).getXcoordinate()][snake.getBodyPiece(i).getYcoordinate()] = snake.getBodyPiece(i);
			
		}
	}
	
	
	//metodi get e set dei parametri della classe Map
	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}
	
	public Box getBox(int X, int Y) {
		return this.box[X][Y];
	}

	public void setBox(Box box, int X, int Y) {
		this.box[X][Y] = box;
	}
	
	public boolean getAppleCollision() {
		return appleCollision;
	}

	/**
	 * metodo che ritorna l'identificativo in memoria della mappa
	 * @return 
	 */
	@Override
	public String toString() {
		return this.toString();
		
	}
	
	public int getSnakeLength() {
		return snake.getLenght();
	}
	
	
}
