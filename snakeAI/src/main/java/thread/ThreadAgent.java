package thread;

import java.util.ArrayList;
import java.util.List;

import enumSnake.BoxType;
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
	 * @param snakeHead
	 * @param snakeFirstBodyBox
	 * @param startingDegree
	 * @param rePhasing = 3 , every straight line is rephrase for a value of 3 degree
	 * @param n = 60 , number of straight lines
	 * 
	 * @return angle of the various 60 rays that represent the view of the head of the snake
	 */
	private int[] rays(int startingDegree, int rePhasing, int n) {
		
		int[] rays = new int[n]; //m of the i-th straight line i = 1...n
		int alpha = startingDegree;
		int rePhase = 0;
		
		for(int i=0; i<n; i++) {
			alpha = startingDegree + rePhase;
			
			if(alpha < 0) { //when the snake it's in the range >270 and <90 i've used the convention [-180; 180] so i have to switch back to [0; 360]
				rays[i] = alpha + 360;
			}else {
				rays[i] = alpha;
			}
			
			rePhase = rePhase + rePhasing; 
		}
		
		return rays;
		
	}
	
	
	/**
	 * 
	 * @param box
	 * @param map
	 * @param snakeHead
	 * @param startDegree
	 * @param endDegree
	 * @return if the object collides with one of the ray of the head it will return the angle of the ray 
	 * else it will return 361
	 */
	private double calculateAngle(Box box, Map map, SnakeBox snakeHead, String dir) {
		
		int deltaX = box.getX() - snakeHead.getX();
		int deltaY = box.getY() - snakeHead.getY();
				
		//the angle between the head and the object, it's in radiant and it is in the range [- pi; +pi]
		double alpha = Math.atan2(deltaY, deltaX);
				
		double alphaDegree = Math.toDegrees(alpha); //transform from radiant to degree
		
		//convert from [-180; 180] to [0; 360]
		if(alphaDegree<0) {
			alphaDegree = alphaDegree + 360;
		}
				
		if(dir == "up" && (alphaDegree<=180 && alphaDegree>=0)) {
			return alphaDegree;
		}else if(dir == "down" && (alphaDegree>=180 && alphaDegree<360)) {
			return alphaDegree;
		}else if(dir == "left" && (alphaDegree>=90 && alphaDegree<=270)) {
			return alphaDegree;
		}else if(dir == "right" && (alphaDegree>=270 || alphaDegree<=90)) {
			return alphaDegree;
		}
		
		return 361; //i choose 361, because it is not in the range [0; 360]
	}
	
	/**
	 * 
	 * @param box
	 * @param snakeHead
	 * @param startDegree
	 * @param endDegree
	 * @return the distance between an object and the head of the snake
	 */
	private double calculateDistance(Box box, SnakeBox snakeHead) {
		
		double x = Math.abs(snakeHead.getX() - box.getX());
		double y = Math.abs(snakeHead.getY() - box.getY());
		double distance = Math.sqrt((x*x)+(y*y));
		
		return distance;
	}
	
	/**
	 * 
	 * @param rays (60 rays for food, 60 rays for walls or 60 rays for snake's body)
	 * @param boxtype (food, walls or snake)
	 * @param map
	 * @param snakeFirstBodyBox
	 * @param box
	 * @param snakeHead
	 * @param dir
	 * @param rephase = 3 degree
	 */
	private double calculateRay(double[] rays, BoxType boxtype, Map map, Box box, SnakeBox snakeHead, String dir, int rephase) {
		
		
		return 361;
	}
	
	/**
	 * array in the first 60 position will have FOOD, for the next 60 position WALLS and the last 60 SNAKE
	 * 
	 * @param map
	 * @return normalization of the distance of the snake's head and the elements
	 */
	public double[] mapConversion(Map map) {
		
		double[] food;
		double[] walls;
		double[] snake;
						
		
		for(int i=0; i<map.getMap().length; i++) { //i get the length of the rows
			for(int j=0; j<map.getMap()[0].length; j++) { //i get the length of the columns
				
				if((map.getMap()[i][j] instanceof SnakeBox) && ((Snake) map.getMap()[i][j].getElementType() == Snake.Head)) {
					
					SnakeBox snakeHead = (SnakeBox) map.getMap()[i][j];
					SnakeBox snakeFirstBodyBox = snakeHead.getNext(1);

					String dir;
					int startingDegree;
					int[] rays;
					int rephase = 3;
					int n = 60;
					
					//i calculate the direction of the snake
					if(snakeHead.getX() - snakeFirstBodyBox.getX() < 0) {
						//head left and body right
						dir = "left";
						startingDegree = 90;
						rays = rays(startingDegree, rephase, n);
						
						double distance;
						int closest, ray;
						
						for(int k=0; k<map.getMap().length; k++) {
							
							for(int h=0; h<map.getMap()[0].length; h++) {
								
								if(k != i && h != j) {
									
									double alpha = calculateAngle(map.getMap()[k][h], map, snakeHead, dir);
									
									if (alpha != 361) {
										
										//CALCOLA DISTANZA VEDE CHE TIPO è L'OGGETTO E LO AGGIUNGE A RAGGI MURO O RAGGI MELA O RAGGI CORPO
										distance = calculateDistance(map.getMap()[k][j], snakeHead);
										closest = (int) Math.round(alpha / rephase); 
										ray = closest * rephase;
										/*
										 * COSI  PER ES. CON VISTA DA [0; 180] HO OGGETTO A (3, -5) E QUINDI ANGOLO 
										 * CON ARCTAN(-5/3) +180, HO UN ANGOLO DI TIPO 151,36374671
										 * DIVIDO PER 3 E OTTENGO 50,3333 E ARROTONDO PER AVERE 50, RIMOLTIPLICANDO *3 HO IL RAGGIO CHE SAREBBE 150!
										 */
										
									}
								}
								
								
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
