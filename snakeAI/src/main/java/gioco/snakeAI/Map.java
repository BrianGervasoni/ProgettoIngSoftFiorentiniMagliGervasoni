package gioco.snakeAI;
import boxes.*;

import java.util.ArrayList;
import java.util.Random;

public class Map {
	
	 public static final int X = 12;
	 public static final int Y = 12;

	private Box[][] box;
	private Snake snake;
	private AppleBox apple;
	
	public Map() {
		this.box = new Box[X][Y];
		this.apple = null;

		this.snake = new Snake(this);
		
		for(int i = 0; i < X ; i++) {
			for(int k = 0; k < Y ; k++) {
				if(i == 0 || i == X-1 || k == 0 || k == Y-1) {
					this.box[i][k] = new EmptyBox(MapElem.Wall, i, k);
					
				}
				else {
					this.box[i][k] = new EmptyBox(MapElem.Empty, i , k);
				}
			}
		}
		
	}
	
	public void updateStateHead(Direction dir) {
		
		this.snake.setDirection(dir);	
		
	}
	
	public boolean checkAppleCollision() {
		
		
		return true;
		
	}
	
	public boolean checkVictory() {
		
		return true;
	}
	
	public boolean checkDefeat() {
		
		return true;
	}
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public boolean setApple() {
		
		Random rand = new Random();
		
		int appleX = 0;
		int appleY = 0;

		Boolean nonTrovato = false;
		
		do {
			
			appleX = rand.nextInt(1, Map.X-1);
			appleY = rand.nextInt(1, Map.Y-1);	
			
			if(((appleX != 0)&&(appleX != 11))&&((appleY != 0)&&(appleY != 11))) {
				nonTrovato = false;
			}if(getBox(appleX, appleY) instanceof SnakeBox)
				nonTrovato = true;

			
		}while(nonTrovato);
		
		this.apple = new AppleBox(Food.Apple, appleX, appleY);
		setBox(apple, appleX, appleY);	
		
		return true;
	}
	
	
	public int getXapple() {
		
		return apple.getXcoordinate();
	}
	
	public int getYapple() {
		
		return apple.getYcoordinate();
	}
	
	
	
	public void changeBoxType() {
		
		
	}
	
	public void reset() {
		
		
	}
	
	public void addSnakeBody() {
		
		if(snake.getLength() == 0) {					// inizializziamo
			
			
			//qui inizializziamo la testa
			Random rand = new Random();
			
			int randX = rand.nextInt(1, Map.X-1);
			int randY = rand.nextInt(1, Map.Y-1);
			
			SnakeBox heead = new SnakeBox(SnakeBody.Head, randX, randY, null);
			
			this.setBox(heead, randX, randY);
			snake.setHead(heead);
			
			snake.setLength(snake.getLength() + 1);
			
			
			//ora inizializziamo il primo pezzo di corpo

			int firstPieceX = 0;
			int firstPieceY = 0;

			int randA = rand.nextInt(1, 101);

			Boolean nonTrovato = true;
			
			do {
				if((randA <= 25) && (( (EmptyBox) box[randX+1][randY]).getEnum() == MapElem.Empty )) {
					firstPieceX = randX + 1;
					firstPieceY = randY;
					nonTrovato = false;
				}else if((randA>25)&&(randA<=50) && (( (EmptyBox) box[randX-1][randY]).getEnum() == MapElem.Empty )){
					firstPieceX = randX - 1;
					firstPieceY = randY;
					nonTrovato = false;
				}else if((randA>50)&&(randA<=75) && (( (EmptyBox) box[randX][randY+1]).getEnum() == MapElem.Empty )){
					firstPieceX = randX;
					firstPieceY = randY + 1;
					nonTrovato = false;
				}else if((randA>75)&&(randA<=100) && (( (EmptyBox) box[randX+1][randY-1]).getEnum() == MapElem.Empty )){
					firstPieceX = randX;
					firstPieceY = randY - 1;
					nonTrovato = false;
				}
				
				randA = rand.nextInt(1, 101);
				
			}while(nonTrovato);
			
			SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.Body, firstPieceX, firstPieceY, heead);
			setBox(firstBodyPiece, firstPieceX, firstPieceY);
			snake.addPiece(firstBodyPiece);
			
			
			
			// e ora inizializziamo il secondo pezzo di corpo
			
		/**	
			int secondPieceX = 0;
			int secondPieceY = 0;
			Boolean nonTrovato2 = true;
			
			do {
				randA = rand.nextInt(1, 101);
				
				if((randA <= 25)) {
					secondPieceX = firstPieceX + 1;
					secondPieceY = firstPieceY;

				}else if((randA>25)&&(randA<=50)){
					secondPieceX = firstPieceX - 1;
					secondPieceY = firstPieceY;

				}else if((randA>50)&&(randA<=75)){
					secondPieceX = firstPieceX;
					secondPieceY = firstPieceY + 1;

				}else if((randA>75)&&(randA<=100)){
					secondPieceX = firstPieceX;
					secondPieceY = firstPieceY - 1;
				}
				
				if((secondPieceX == randX)&&( secondPieceY == randY))
					nonTrovato2 = true;	
				else if(((secondPieceX != 0)&&(secondPieceX != 11))&&((secondPieceY != 0)&&(secondPieceY != 11)))
					nonTrovato2 = false;
	
				
			}while(nonTrovato2);
			
			
			SnakeBox secondBodyPiece = new SnakeBox(SnakeBody.Tail, secondPieceX, secondPieceY, firstBodyPiece);
			setBox(secondBodyPiece, secondPieceX, secondPieceY);
			snake.addPiece(secondBodyPiece);
			
			
			snake.setDirection(Direction.Straight);
			
			*/
			
			
			
		}else // vuol dire che ha mangiato una mela
			
		{
			
			
			
		}
		
		
		
	}
	

	public void gameLoop() {
		
		
		/** 
		 * prima cosa: quando il serpente si muove, cambiare tutte le coordinate
		 * seconda cosa: aggiornare il tipo di box verso cui e da cui lo snake si muove
		 * terzo: ristampare il tutto aggiornato 
		 */
		
		
		ArrayList<Direction> dir2 = new ArrayList();
		dir2.add(Direction.Right);
		dir2.add(Direction.Straight);
		dir2.add(Direction.Left);
		Random rand = new Random();
		
		
		
		while(snake.move(dir2.get(rand.nextInt(3))) == true) {
			
			resetSnakeBoxes();
			insertSnakeBoxes();
			GameMain.visualize(this);
			
			
		}
		
		

		//dobbiamo ancora trattare la collisione con la mela
		
	}
	
	public void resetSnakeBoxes() {
		
		for(int i = 1; i < X-1; i++) {
			for (int k = 1; k < Y-1; k++) {
				
				if(box[i][k] instanceof SnakeBox) {
					box[i][k] = new EmptyBox(MapElem.Empty, i, k);
					
				}
				
			}
			
		}
		
	}
	
	public void insertSnakeBoxes() {
		
		for(int i = 0; i < snake.getBody().size(); i++) {
			
			box[snake.getBodyPiece(i).getXcoordinate()][snake.getBodyPiece(i).getYcoordinate()] = snake.getBodyPiece(i);
			
		}
	}
	
	public void vis(int X, int Y) {
		
		if(getBox(X, Y) instanceof EmptyBox)
			System.out.println("true");
		
		
		System.out.println(((EmptyBox) box[X][Y]).getElem());
		
	}

	
	
	
	
	
	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public static int getX() {
		return X;
	}

	public static int getY() {
		return Y;
	}
	
	public Box getBox(int X, int Y) {
		return this.box[X][Y];
	}

	public void setBox(Box box, int X, int Y) {
		this.box[X][Y] = box;
	}
	
	
	
}
