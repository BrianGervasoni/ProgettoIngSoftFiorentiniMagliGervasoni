package thread;

import enumSnake.Snake;
import model.ActionRegister;
import snakeGame.Direction;
import snakeGame.Map;
import snakeGame.SnakeBox;

public class Intermediary implements Functions{
	
	ActionRegister[] actionRegister;
	float[] rewards;
	
	public Intermediary() {
		
	}
	
	/** DEVO NORMALIZZARE RISULTATO
	 * array in the first 61 position will have FOOD, for the next 61 position WALLS and the last 61 SNAKE
	 * 
	 * @param map
	 * @return normalization of the distance of the snake's head and the elements
	 */
	public double[] mapConversion(Map map) {
		
		int startingDegree, rephase = 3, n = 61;	
		String dir;
		int[] rays;
		double[] food = inizializeArray(n), walls = inizializeArray(n), snake = inizializeArray(n);
		
		for(int i=0; i<map.getMap().length; i++) { //i get the length of the rows
			for(int j=0; j<map.getMap()[0].length; j++) { //i get the length of the columns
				
				if((map.getMap()[i][j] instanceof SnakeBox) && ((Snake) map.getMap()[i][j].getElementType() == Snake.Head)) {
					
					SnakeBox snakeHead = (SnakeBox) map.getMap()[i][j];
					SnakeBox snakeFirstBodyBox = snakeHead.getNext(1);
					
					//i calculate the direction of the snake
					if(snakeHead.getX() - snakeFirstBodyBox.getX() < 0) {
						//head left and body right
						dir = "left";
						startingDegree = 90;
						rays = rays(startingDegree, rephase, n);
						
						setValuesArrays(map, snakeHead, dir, rephase, food, walls, snake, rays);
						
						
					} else if(snakeHead.getX() - snakeFirstBodyBox.getX() > 0) {
						//head right and body left
						dir = "right";
						startingDegree = -90;
						rays = rays(startingDegree, rephase, n);
						
						setValuesArrays(map, snakeHead, dir, rephase, food, walls, snake, rays);
						
					} else if(snakeHead.getY() - snakeFirstBodyBox.getY() < 0) {
						//head down and body up
						dir = "down";
						startingDegree = 180;
						rays = rays(startingDegree, rephase, n);
						
						setValuesArrays(map, snakeHead, dir, rephase, food, walls, snake, rays);
						
					} else {
						//head up and body down
						dir = "up";
						startingDegree = 0;
						rays = rays(startingDegree, rephase, n);
						
						setValuesArrays(map, snakeHead, dir, rephase, food, walls, snake, rays);
						
					}
				}
			}
		}
		
		return mergeArrays(food, walls, snake);
	}
	
	private Direction moveConversion(int index) {
		
		return null;
		
	}
	
	public Direction moveSelection(double[] input) {
		
		return null;
		
	}
	
	public void addActionReward(float r) {
		
	}
	
	public void finishEpisode() {
		
	}
	
	private void calculateVTarget() {
		
	}
}
