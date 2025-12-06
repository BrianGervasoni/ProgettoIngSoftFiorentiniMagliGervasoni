package snakeGame;

import java.util.Random;

import enumSnake.Snake;

public class Map {
	static int r = 10; //row
	static int c = 10; //column
	Box[][] Map;
	
	public Map() {
		int max = 10;
		int min = 0;
		int x_snake_head = (int) (Math.random() * (max - min + 1) + min);
		int y_snake = (int) (Math.random() * (max - min + 1) + min);
		int x_corpo_snake = x_snake_head + 1;
		int x_apple = 0;
		int y_apple = 0;
		
		do {
			x_apple = (int) (Math.random() * (max - min + 1) + min);
		} while(x_apple == x_snake_head);
		
		do {
			y_apple = (int) (Math.random() * (max - min + 1) + min);
		} while(y_apple == y_snake);
		
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(i == x_snake_head && j == y_snake){
					
					this.Map[i][j] = new SnakeBox(i,j);
					this.Map[i][j].setElementType(Snake.Head);
					
				}else if(i == x_corpo_snake && j == y_snake) {
					
					this.Map[i][j] = new SnakeBox(i,j);
					this.Map[i][j].setElementType(Snake.Body);
					
				}else if(i == x_apple && j == y_apple){
					
					this.Map[i][j] = new AppleBox(i,j);
					
				}else {
					
					this.Map[i][j] = new EmptyBox(i,j);
					
				}
			}
		}
	}

	public Box[][] getMap() {
		return Map;
	}

	public void setMap(Box[][] map) {
		Map = map;
	}
	
}
