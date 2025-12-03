package thread;

import java.util.ArrayList;
import java.util.List;

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
	public boolean objectCollide(int k, int h, Map map, SnakeBox snakeHead, String dir) {
		
		int deltaX = map.getMap()[k][h].getX() - snakeHead.getX();
		int deltaY = map.getMap()[k][h].getY() - snakeHead.getY();
				
		//the angle between the head and the object, it's in rad and it's range its [- pi, +pi]
		double alpha = Math.atan2(deltaY, deltaX);
		
		//conversion [-180; 180] to the usual convention [0; 360] in rad
		if(alpha < 0) {
			alpha = alpha + 2 * Math.PI;
		}
				
		double alphaDegree = Math.toDegrees(alpha); //transform in degree
				
		if(dir == "up" && (alphaDegree<=180 && alphaDegree>=0)) {
			return true;
		}else if(dir == "down" && (alphaDegree>=-180 && alphaDegree<=0)) {
			return true;
		}else if(dir == "left" && (alphaDegree>=90 && alphaDegree<=-90)) {
			return true;
		}else if(dir == "right" && (alphaDegree>=-90 && alphaDegree<=90)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param box
	 * @param snakeHead
	 * @param startDegree
	 * @param endDegree
	 * @return the distance between an object and the head of the snake
	 */
	public double calculateDistance(Box box, SnakeBox snakeHead) {
		
		double x = Math.abs(snakeHead.getX() - box.getX());
		double y = Math.abs(snakeHead.getY() - box.getY());
		double distance = Math.sqrt((x*x)+(y*y));
		
		return distance;
	}
	
	/**
	 * l'array avrà nella prima parte i valori distanza dal muro, nella parte centrale la mela, nella parte finale il corpo
	 * 
	 * @param map
	 * @return normalization of the distance of the snake's head and the elements
	 */
	public List<Double> mapConversion(Map map) {
		
		List<Double> risultati = new ArrayList<>();
		
		for(int i=0; i<map.getMap().length; i++) { //i get the length of the rows
			for(int j=0; j<map.getMap()[0].length; j++) { //i get the length of the columns
				
				if((map.getMap()[i][j] instanceof SnakeBox) && ((Snake) map.getMap()[i][j].getElementType() == Snake.Head)) {
					
					SnakeBox snakeHead = (SnakeBox) map.getMap()[i][j];
					SnakeBox snakeFirstBodyBox = snakeHead.getNext(1);

					String dir;
					
					//i calculate the direction of the snake
					if(snakeHead.getX() - snakeFirstBodyBox.getX() < 0) {
						//head left and body right
						
						for(int k=0; k<map.getMap().length; k++) {
							for(int h=0; h<map.getMap()[0].length; h++) {
								
								dir = "left";
								if (objectCollide(k, h, map, snakeHead, dir) == true) {
									
									risultati.add(calculateDistance(map.getMap()[k][h], snakeHead));
									//SISTEMA PERCHE NELLA PRIMA PARTE CI SIAMO MURI POI MELA POI CORPO
								}
								
							}
						}
						
					} else if(snakeHead.getX() - snakeFirstBodyBox.getX() > 0) {
						//head right and body left
						
					} else if(snakeHead.getY() - snakeFirstBodyBox.getY() < 0) {
						//head down and body up
						
					} else {
						//head up and body down
						
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
