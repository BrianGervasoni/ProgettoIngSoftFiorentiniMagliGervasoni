package thread;

import snakeGame.Box;
import snakeGame.SnakeBox;

public interface Functions {
	
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
	public default int[] rays(int startingDegree, int rePhasing, int n) {
		
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
	public default double calculateAngle(Box box, SnakeBox snakeHead, String dir) {
		
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
	public default double calculateDistance(Box box, SnakeBox snakeHead) {
		
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
	 * COSI  PER ES. CON VISTA DA [0; 180] HO OGGETTO A (3,-5) E QUINDI ANGOLO 
	 * CON ARCTAN(-5/3) +180, HO UN ANGOLO DI TIPO 151,36374671
	 * DIVIDO PER 3 E OTTENGO 50,3333 E ARROTONDO PER AVERE 50, RIMOLTIPLICANDO *3 HO IL RAGGIO CHE SAREBBE 150!	
	 */
	public default int calculateRay(Box box, SnakeBox snakeHead, String dir, int rephase) {
		
		int closest, ray;
		
		if(box.getX() != snakeHead.getX() && box.getY() != snakeHead.getY()) {
			
			double alpha = calculateAngle(box, snakeHead, dir);
			
			if (alpha != 361) {
				
				closest = (int) Math.round(alpha / rephase); 
				ray = closest * rephase;
				return ray;
			}
		}
		
		return 361;
	}
	
}
