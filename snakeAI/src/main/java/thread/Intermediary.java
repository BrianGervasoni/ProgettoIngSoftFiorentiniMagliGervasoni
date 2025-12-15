package thread;

import boxes.Direction;
import boxes.SnakeBody;
import boxes.SnakeBox;
import model.ActionRegister;
import gioco.snakeAI.*;

public class Intermediary implements Functions{
	
	ActionRegister[] actionRegister;
	float[] rewards;
	
	public Intermediary() {
		
	}
	
	/** 
	 * array in the first 61 position will have FOOD, for the next 61 position WALLS and the last 61 SNAKE
	 * 
	 * @param map
	 * @return normalization of the distance of the snake's head and the elements
	 */
	public double[] mapConversion(Map map, int outputLenght) { 
		//outputLenght it's given by threadAgent.getModel.getAiActor.getLenght (it's the length of the array output 61 *3 ))
		int startingDegree, rephase, n, nNonDivisibilePer3 = 0, delta = 0;	
		
		if(outputLenght%3 != 0) {
			
			nNonDivisibilePer3 = 3 * (int)(outputLenght/3);
			delta = outputLenght - nNonDivisibilePer3;
			n = nNonDivisibilePer3/3;
			
		}else {
			n = outputLenght/3;
		}
		
		rephase = 180/n;
		
		String dir;
		int[] rays;
		double[] food = inizializeArray(n), walls = inizializeArray(n), snake = inizializeArray(n), arrayMerged = inizializeArray(n*3), result = inizializeArray(n*3 +  delta);
		
		for(int i=0; i<map.X; i++) { //i get the length of the rows
			for(int j=0; j<map.Y; j++) { //i get the length of the columns
				
				if((map.getBox(i, j).equals(SnakeBody.HEAD))) {
					
					SnakeBox snakeHead = (SnakeBox) map.getBox(i, j);
					SnakeBox snakeFirstBodyBox = map.getSnake().getBodyPiece(1);
					
					//i calculate the direction of the snake
					if(snakeHead.getXcoordinate() - snakeFirstBodyBox.getXcoordinate() < 0) {
						//head left and body right
						dir = "left";
						startingDegree = 90;
						rays = rays(startingDegree, rephase, n);
						
						setValuesArrays(map, snakeHead, dir, rephase, food, walls, snake, rays);
						
						
					} else if(snakeHead.getXcoordinate() - snakeFirstBodyBox.getXcoordinate() > 0) {
						//head right and body left
						dir = "right";
						startingDegree = -90;
						rays = rays(startingDegree, rephase, n);
						
						setValuesArrays(map, snakeHead, dir, rephase, food, walls, snake, rays);
						
					} else if(snakeHead.getYcoordinate()- snakeFirstBodyBox.getYcoordinate() < 0) {
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
		
		if(nNonDivisibilePer3 != 0) {
			
			arrayMerged = mergeArrays(food, walls, snake);
			
			double[] array = new double[delta];
			for(int i=0; i<array.length; i++) {
				array[i] = 0;
			}
			
			result = merge(arrayMerged,array);
			
		}else {
			
			result = mergeArrays(food, walls, snake);
			
		}
		
		return normalizeArray(result);
		
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
