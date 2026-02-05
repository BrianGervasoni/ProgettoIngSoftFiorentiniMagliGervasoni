package progettoAI.snakeAI.AI.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import progettoAI.snakeAI.AI.model.ActionRegister;
import progettoAI.snakeAI.SnakeLogic.boxes.Direction;
import progettoAI.snakeAI.SnakeLogic.boxes.SnakeBody;
import progettoAI.snakeAI.SnakeLogic.boxes.SnakeBox;
import progettoAI.snakeAI.SnakeLogic.game.*;

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
		if((inputLenght-4)%3 != 0) {
			
			nNonDivisibilePer3 = 3 * (int)((inputLenght-4)/3);
			delta = (inputLenght-4) - nNonDivisibilePer3;
			
		}
		
		n = (int)(inputLenght-4)/3;
		
		
		Ray[] rays;
		double[] food = initializeArray(n), walls = initializeArray(n), snake = initializeArray(n), arrayMerged = initializeArray(n*3), result = initializeArray(n*3 +  delta);
					
		SnakeBox snakeHead = map.getSnake().getBodyPiece(0);
		
		SnakeBox snakeFirstBodyBox = map.getSnake().getBodyPiece(1);
		
		//i calculate the direction of the snake
		if (snakeHead.getYcoordinate() - snakeFirstBodyBox.getYcoordinate() > 0) {
		    // head RIGHT, body LEFT
		    startingDegree = 0; // DESTRA
		} 
		else if (snakeHead.getYcoordinate() - snakeFirstBodyBox.getYcoordinate() < 0) {
		    // head LEFT, body RIGHT
		    startingDegree = 180;  // SINISTRA
		} 
		else if (snakeHead.getXcoordinate() - snakeFirstBodyBox.getXcoordinate() > 0) {
		    // head DOWN, body UP
		    startingDegree = 270; // GIÙ
		} 
		else {
		    // head UP, body DOWN
		    startingDegree = 90;   // SU
		}
		
		double [] directionVector = degreesToVector(startingDegree).toDoubleVector();
		double headingSin = directionVector[0];
		double headingCos = directionVector[1];
		double dx = map.getXapple() - snakeHead.getXcoordinate();
		double dy = map.getYapple() - snakeHead.getYcoordinate();

		// rotate in base alla direzione della testa (headingSin, headingCos)
		double appleRelX = dx * headingCos + dy * headingSin;
		double appleRelY = -dx * headingSin + dy * headingCos;
		
		rays = rays(startingDegree, 180 , n, snakeHead.getXcoordinate(), snakeHead.getYcoordinate());
		setValuesArrays(map, food, walls, snake, rays);
		
		food = normalizeRay(food,map,0.0);
		walls = normalizeRay(walls,map,0.0);
		snake = normalizeRay(snake,map,0.0);
		
		/*// pericolo = ostacolo più vicino in quella direzione
		double dangerForward = Math.max(walls[walls.length/2], snake[snake.length/2]);
		double dangerLeft    = Math.max(walls[walls.length-1],   snake[snake.length-1]);
		double dangerRight   = Math.max(walls[0],  snake[0]);*/
					
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
		double[] finalState = new double[result.length + 4];
		System.arraycopy(result, 0, finalState, 0, result.length);
		finalState[result.length] = headingSin;
		finalState[result.length + 1] = headingCos;
		finalState[result.length + 2] = appleRelX /(double) (map.X-2) ;
		finalState[result.length + 3] = appleRelY / (double) (map.Y-2) ;

		return finalState;
	}
	
	/**
	 * 
	 * @param index with a dim = 3 (0,1,2)
	 * 0 = left
	 * 1 = straight 
	 * 2 = right
	 * @return the corresponding direction
	 */
	public Direction moveConversion(int index) {
		
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
	public int moveSelectionTraining(double[] output) {
		double valore = Math.random(); //value from 0 to 1
		double min = 0;
		
		
		/*if(Math.random() < 0.1) {
			return (int) (Math.random() * output.length);
		}*/
		
		for(int i=0; i<output.length; i++) {
			
			min += output[i];
			
			if(valore <= min) {
				return i;
			}	
		}
		
		return output.length-1;
	}
	
	/**
	 * select action whit higher probability
	 * @param output
	 * @return
	 */
	public int moveSelectionBest(double[] output) {
	    int bestAction = 0;
	    for(int i = 1; i < output.length; i++) {
	        if(output[i] > output[bestAction]) bestAction = i;
	    }
	    return bestAction;
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
