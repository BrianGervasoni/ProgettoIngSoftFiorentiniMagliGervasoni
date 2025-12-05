package thread;

import enumSnake.Food;
import enumSnake.MapElem;
import enumSnake.Snake;
import model.*;
import snakeGame.*;

public class ThreadAgent extends Thread implements Functions{

	Model model;
	ActionRegister[] actionRegister;
	GameMain game;
	
	public ThreadAgent(Model model) {
		
	}
	
	public void run() {
		
	}
	
	public int moveSelection(double[] input) {
		
		return 0;
		
	}
	
	public void finish() {
		
	}
	
	/**
	 * array in the first 60 position will have FOOD, for the next 60 position WALLS and the last 60 SNAKE
	 * 
	 * @param map
	 * @return normalization of the distance of the snake's head and the elements
	 */
	public double[] mapConversion(Map map) {
		
		int indexFood = 0, indexWall = 0, indexSnake = 0, startingDegree, rephase = 3, n = 60, ray;	
		String dir;
		int[] rays;
		double[] food = inizializeArray(n), walls = inizializeArray(n), snake = inizializeArray(n);
		double distance;
		
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
						
						for(int k=0; k<map.getMap().length; k++) {
							for(int h=0; h<map.getMap()[0].length; h++) {
								
								distance = calculateDistance(map.getMap()[k][h], snakeHead);
								
								ray = calculateRay(map.getMap()[k][h], snakeHead, dir, rephase);
								
								indexFood = foundRayPosition(rays,ray);
								
								distanceAssignedToRay(map.getMap()[k][h], distance, food, indexFood);
								
							}
						}
						
					} else if(snakeHead.getX() - snakeFirstBodyBox.getX() > 0) {
						//head right and body left
						dir = "right";
						startingDegree = -90;
						
						rays = rays(startingDegree, rephase, n);
						
					} else if(snakeHead.getY() - snakeFirstBodyBox.getY() < 0) {
						//head down and body up
						dir = "down";
						startingDegree = 180;
						
						rays = rays(startingDegree, rephase, n);
						
					} else {
						//head up and body down
						dir = "up";
						startingDegree = 0;
						
						rays = rays(startingDegree, rephase, n);
						
					}
				}
			}
		}
		
		return null;
	}
	
	public Direction moveConversion(int index) {
		
		return null;
		
	}
	
	public float calculateReward(Map map) {
		
		return 0;
		
	}
	
	public void move(Direction direction) {
		
	}
	
	public void resetActionRegister() {
		
	}
	
	public void sendActions (ActionRegister actionRegister) {
		
	}
	
}
