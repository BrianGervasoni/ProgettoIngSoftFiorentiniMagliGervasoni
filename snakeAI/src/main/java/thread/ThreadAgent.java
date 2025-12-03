package thread;

import enumSnake.Snake;
import model.*;
import snakeGame.*;

public class ThreadAgent extends Thread{

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
	 * 
	 * @param k
	 * @param h
	 * @param map
	 * @param snakeHead
	 * @param startDegree
	 * @param endDegree
	 * @return if the object collides with one of the line of the head it will return true, else false
	 */
	public boolean objectCollide(int k, int h, Map map, SnakeBox snakeHead, double startDegree, double endDegree) {
		
		int deltaX = map.getMap()[k][h].getX() - snakeHead.getX();
		int deltaY = map.getMap()[k][h].getY() - snakeHead.getY();
				
		//the angle between the head and the object, it's in rad and it's range its [- pi, +pi]
		double alpha = Math.atan2(deltaY, deltaX);
		
		//conversion [-180; 180] to the usual convention [0; 360] in rad
		if(alpha < 0) {
			alpha = alpha + 2 * Math.PI;
		}
				
		double alphaDegree = Math.toDegrees(alpha); //transform in degree
				
		if(alphaDegree > startDegree && alphaDegree < endDegree) {
			return true;
		}
	
		return false;
	}
	
	
	public double calculateDistance(Box box, SnakeBox snakeHead, double startDegree, double endDegree) {
		
		double x = Math.abs(snakeHead.getX() - box.getX());
		double y = Math.abs(snakeHead.getY() - box.getY());
		double distance = Math.sqrt((x*x)+(y*y));
		
		return distance;
	}
	
	
	/**
	 * 
	 * @param map
	 * @return normalization of the distance of the snake's head and the elements
	 */
	public double[] mapConversion(Map map) {
		
		for(int i=0; i<map.getMap().length; i++) { //i get the length of the rows
			for(int j=0; j<map.getMap()[0].length; j++) { //i get the length of the columns
				
				if((map.getMap()[i][j] instanceof SnakeBox) && ((Snake) map.getMap()[i][j].getElementType() == Snake.Head)) {
					
					SnakeBox snakeHead = (SnakeBox) map.getMap()[i][j];
					SnakeBox snakeFirstBodyBox = snakeHead.getNext(1);

					//i calculate the direction of the snake
					if(snakeHead.getX() - snakeFirstBodyBox.getX() < 0) {
						//head left and body right
						//range from 270° to 90° CLOCKWISE
						for(int k=0; k<map.getMap().length; k++) {
							for(int h=0; h<map.getMap()[0].length; h++) {
								//INSERIRE OBJECT COLLIDE E CALCULATE DISTANCE
							}
						}
						
					} else if(snakeHead.getX() - snakeFirstBodyBox.getX() > 0) {
						//head right and body left
						//range from 90° to 270° CLOCKWISE
						
					} else if(snakeHead.getY() - snakeFirstBodyBox.getY() < 0) {
						//head down and body up
						//range from 360° to 180° CLOCKWISE
						
					} else {
						//head up and body down
						//range from 180° to 0° CLOCKWISE
						
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
