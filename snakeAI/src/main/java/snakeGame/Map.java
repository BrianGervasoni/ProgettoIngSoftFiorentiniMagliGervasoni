package snakeGame;

import java.util.Random;

public class Map {
	static int r; //row
	static int c; //column
	Box[][] Map;
	
	public Map() {
		int max = 10;
		int min = 0;
		int x_snake = (int) (Math.random() * (max - min + 1) + min);
		int y_snake = (int) (Math.random() * (max - min + 1) + min);
		int x_apple = 0;
		int y_apple = 0;
		
		do {
			x_apple = (int) (Math.random() * (max - min + 1) + min);
		} while(x_apple == x_snake);
		
		do {
			y_apple = (int) (Math.random() * (max - min + 1) + min);
		} while(y_apple == y_snake);
		
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(i == x_snake && j == y_snake){
					
					this.Map[i][j] = new SnakeBox();
					
				}else if(i == x_apple && j == y_apple){
					
					this.Map[i][j] = new AppleBox();
					
				}else {
					
					this.Map[i][j] = new EmptyBox();
					
				}
			}
		}
	}
}
