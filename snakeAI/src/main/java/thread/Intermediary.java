package thread;

import boxes.Direction;
import boxes.SnakeBody;
import boxes.SnakeBox;
import model.ActionRegister;
import gioco.snakeAI.*;

public class Intermediary implements Functions{
	
	ActionRegister[] actionRegister;
	
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
			
		}
		
		n = (int)outputLenght/3;
		
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
	
	/**
	 * 
	 * @param index with a dim = 3 (0,1,2)
	 * 0 = left
	 * 1 = straight 
	 * 2 = right
	 * @return the corresponding direction
	 */
	private Direction moveConversion(int index) {
		
		switch(index) {
		
		case 0 :
			return Direction.LEFT;
		case 1 :
			return Direction.STRAIGHT;
		case 2 :
			return Direction.RIGHT;
		default :
			return null;
			
		}
		
	}
	
	/**
	 * 
	 * @param input. An array with the probability of a certain action.
	 * For example : an array of 3 (0 = left, 1 = straight, 2 = right) probability of the 3 actions (0.43 , 0.27 , 0.3)
	 * So i will divide the probability in : 
	 * (0 ; 0.43) for the first action
	 * (0.44 ; 0.7) for the second one
	 * (0.71 ; 1) for the third one
	 * I will choose a random value from 0 and 1 and select the corresponding direction, 
	 * example : random value = 0.2 and select the left direction (because 0 < 0.2 < 0.43)
	 * 
	 * More the AI will learn, more it will increase the probability of the correct action 
	 * For example the probability can become : (0.8 , 0.1 , 0.1)
	 */
	public Direction moveSelection(double[] input) {
		
		double valore = Math.random(); //value from 0 to 1
		double min = 0;
		
		for(int i=0; i<input.length; i++) {
			
			if(valore > min && valore <= input[i]) {
				return moveConversion(i);
			}
			
			min = input[i];
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * @param r
	 * @return 
	 */
	public void addActionReward(float r) {
		
	}
	
}
