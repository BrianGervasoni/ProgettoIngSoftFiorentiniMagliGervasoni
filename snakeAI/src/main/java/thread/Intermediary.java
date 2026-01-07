package thread;

import java.util.ArrayList;

import boxes.Direction;
import boxes.SnakeBody;
import boxes.SnakeBox;
import model.ActionRegister;
import gioco.snakeAI.*;

public class Intermediary implements Functions{
	
	ArrayList<ActionRegister> actionRegister = new ArrayList<ActionRegister>();
	
	public Intermediary() {
		
	}
	
	/** 
	 * array in the first 61 position will have FOOD, for the next 61 position WALLS and the last 61 SNAKE
	 * 
	 * @param map
	 * @return normalization of the distance of the snake's head and the elements
	 */
	public double[] mapConversion(Map map, int inputLenght) { 
		//outputLenght it's given by threadAgent.getModel.getAiActor.getLenght (it's the length of the array output 61 *3 ))
		int startingDegree, rephase, n, nNonDivisibilePer3 = 0, delta = 0;	
		if(inputLenght%3 != 0) {
			
			nNonDivisibilePer3 = 3 * (int)(inputLenght/3);
			delta = inputLenght - nNonDivisibilePer3;
			
		}
		
		n = (int)inputLenght/3;
		
		rephase = (int) Math.round(180.0/n);
		
		Ray[] rays;
		double[] food = inizializeArray(n), walls = inizializeArray(n), snake = inizializeArray(n), arrayMerged = inizializeArray(n*3), result = inizializeArray(n*3 +  delta);
					
					SnakeBox snakeHead = map.getSnake().getBodyPiece(0);
					
					SnakeBox snakeFirstBodyBox = map.getSnake().getBodyPiece(1);
					
					//i calculate the direction of the snake
					if(snakeHead.getYcoordinate() - snakeFirstBodyBox.getYcoordinate() < 0) {
						//head left and body right
						startingDegree = 90;
						
					} else if(snakeHead.getYcoordinate() - snakeFirstBodyBox.getYcoordinate() > 0) {
						//head right and body left
						startingDegree = -90;
						
					} else if(snakeHead.getXcoordinate()- snakeFirstBodyBox.getXcoordinate() > 0) {
						//head down and body up
						startingDegree = 180;
						
					} else {
						//head up and body down
						startingDegree = 0;
						
					}
					
					rays = rays(startingDegree, rephase, n, snakeHead.getXcoordinate(), snakeHead.getYcoordinate());
					setValuesArrays(map, food, walls, snake, rays);
					
					
					
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
		
		return normalizeArray(result,map);
		
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
	public Direction moveSelection(double[] Output) {
		
		double valore = Math.random(); //value from 0 to 1
		double min = 0;
		
		for(int i=0; i<Output.length; i++) {
			
			if(valore > min && valore <= Output[i]) {
				return moveConversion(i);
			}
			
			min = Output[i];
		}
		
		return null;
		
	}
	/**
	 * for the last actionRegister added, set its reward value
	 * @param r
	 */
	public void addActionReward(double r) {
		this.selectLastActionRegister().setReward(r);
	}
	
	public void addActionRegister(ActionRegister actionRegister) {
		if(actionRegister != null) {
			this.actionRegister.add(actionRegister);
		}else {
			throw new IllegalArgumentException();
		}
	}
	
	public ActionRegister selectLastActionRegister() {
		return this.actionRegister.get(this.actionRegister.size() - 1);
	}
	
	public void reset() {
		this.actionRegister = new ArrayList<ActionRegister>();
	}
}
