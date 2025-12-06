package thread;

import enumSnake.Food;
import enumSnake.MapElem;
import enumSnake.Snake;
import snakeGame.Box;
import snakeGame.Map;
import snakeGame.SnakeBox;

public interface Functions {
	
	/**
	 * 
	 * @param array
	 * @param n
	 */
	public default double[] inizializeArray(int n) {
		
		double[] array = new double[n];
		
		for(int i = 0; i<array.length; i++) {
			array[i] = -1;
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param snakeHead
	 * @param snakeFirstBodyBox
	 * @param startingDegree
	 * @param rePhasing = 3 , every straight line is rephrase for a value of 3 degree
	 * @param n = 61 , number of straight lines
	 * 
	 * @return angle of the various 61 rays that represent the view of the head of the snake
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
	 * @param rays (61 rays for food, 61 rays for walls or 61 rays for snake's body)
	 * @param boxtype (food, walls or snake)
	 * @param map
	 * @param snakeFirstBodyBox
	 * @param box
	 * @param snakeHead
	 * @param dir
	 * @param rephase = 3 degree
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
	
	/**
	 * 
	 * @param rays
	 * @param ray
	 * @return the index of the ray from the 61 rays (of food or walls or snake)
	 */
	public default int foundRayPosition(int[] rays, int ray) {
		
		for(int i=0; i<rays.length; i++) {
			
			if(rays[i] == ray) {
				return i;
			}
			
		}
		
		return -1;
	}
	
	/**
	 * 
	 * @param box
	 * @param distance
	 * @param food
	 * @param walls
	 * @param snake
	 * @param indexFood
	 * @param indexWall
	 * @param indexSnake
	 * @return to every distance calculate between the object and the head of the snake we assign a ray
	 */
	public default void distanceAssignedToRay(Box box,  double distance, double[] food, double[] walls, double[] snake, int[] rays, int ray) {
		
		int index = foundRayPosition(rays, ray);
		
		if(box.getElementType() == Food.Apple) {
			
			food[index] = distance;
			
		}else if(box.getElementType() == MapElem.Wall) {
			
			walls[index] = distance;
			
		}else if(box.getElementType() == Snake.Body || box.getElementType() == Snake.Tail) {
			
			snake[index] = distance;
			
		}
	}
	
	/**
	 * 
	 * @param map
	 * @param snakeHead
	 * @param dir
	 * @param rephase
	 * @param distance
	 * @param food
	 * @param walls
	 * @param snake
	 * @param rays
	 * @param ray
	 * @return change the value on the array of rays (food, walls and snake) and set the array[index] = distance , for every ray that is involved
	 */
	public default void setValuesArrays(Map map, SnakeBox snakeHead, String dir, int rephase, double[] food, double[] walls, double[] snake, int[] rays) {
		for(int k=0; k<map.getMap().length; k++) {
			for(int h=0; h<map.getMap()[0].length; h++) {
				
				int ray = calculateRay(map.getMap()[k][h], snakeHead, dir, rephase);
				
				if(ray != 361) {
					
					double distance = calculateDistance(map.getMap()[k][h], snakeHead);
					distanceAssignedToRay(map.getMap()[k][h], distance, food, walls, snake, rays, ray);
					
				}
				
			}
		}
	}
	
	/**
	 * 
	 * @param a1
	 * @param a2
	 * @param a3
	 * @return the fusion of the array inputs
	 */
	public default double[] mergeArrays(double[] a1, double[] a2, double[] a3) {
		
		double[] array = inizializeArray(a1.length + a2.length + a3.length);
		int index = 0;
		
		System.arraycopy(a1, 0, array, index, a1.length);
		index = index + a1.length;
		
		System.arraycopy(a2, 0, array, index, a2.length);
		index = index + a2.length;
		
		System.arraycopy(a3, 0, array, index, a3.length);
		index = index + a3.length;
		
		return array;
	}
}
