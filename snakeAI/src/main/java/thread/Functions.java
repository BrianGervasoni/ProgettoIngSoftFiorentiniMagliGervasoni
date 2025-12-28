package thread;

import boxes.*;
import gioco.snakeAI.Map;

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
		
		int deltaX = box.getXcoordinate() - snakeHead.getXcoordinate();
		int deltaY = box.getYcoordinate() - snakeHead.getYcoordinate();
				
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
	 * @param box1
	 * @param startDegree
	 * @param endDegree
	 * @return the distance between an object and the head of the snake
	 */
	public default double calculateDistance(Box box, Box box1) {
		
		double x = Math.abs(box1.getXcoordinate() - box.getXcoordinate());
		double y = Math.abs(box1.getYcoordinate() - box.getYcoordinate());
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
		
		if(box.getXcoordinate() != snakeHead.getXcoordinate() && box.getYcoordinate() != snakeHead.getYcoordinate()) {
			
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
		
		//System.out.println("index : "+ index + " ray :" + ray);
		
		if(box.getElementType() == Food.APPLE) {
			
			if(index!=-1) {
				food[index] = distance;
				System.out.println("HELOOOOOOOOO " + index);
			}
			
		}else if(box.getElementType() == MapElem.WALL) {
			
			if(index!=-1) {
				walls[index] = distance;
			}
			
		}else if(box.getElementType() == SnakeBody.BODY || box.getElementType() == SnakeBody.TAIL) {
			
			if(index!=-1) {
				snake[index] = distance;
			}
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
		for(int k=0; k<map.X; k++) {
			for(int h=0; h<map.Y; h++) {
				
				int ray = calculateRay(map.getBox(k, h), snakeHead, dir, rephase);
				
				if(ray != 361) {
					
					double distance = calculateDistance(map.getBox(k, h), snakeHead);
					distanceAssignedToRay(map.getBox(k, h), distance, food, walls, snake, rays, ray);
					
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
	
	public default double[] merge(double[] a1, double[] a2) {
		
		double[] array = inizializeArray(a1.length + a2.length);
		int index = 0;
		
		System.arraycopy(a1, 0, array, index, a1.length);
		index = index + a1.length;
		
		System.arraycopy(a2, 0, array, index, a2.length);
		index = index + a2.length;
		
		return array;
	}
	
	/**
	 * 
	 * @param array
	 * @return linear normalization, set the value of array[i] at its new linear normalized value (a value in this interval [0;1])
	 */
	public default double[] normalizeArray(double[] array) {
		
		double min = array[0];
		double max = array[0];
		double xNormalizzato;
		
		//i get minimal value and maximal value
		for(int i = 0; i<array.length; i++) {
			
			if(array[i]<min) {
				min = array[i];
			}
			
			if(array[i]>max) {
				max = array[i];
			}
	
		}
		
		for(int i = 0; i<array.length; i++) {

			xNormalizzato = (array[i] - min)/(max - min);
			array[i] = xNormalizzato;
			
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param value of the distance
	 * @param best = 0 (the best option is that the apple is in the same spot as the head)
	 * @param worst = the diagonal of the map (the worst is that the apple is in the opposite position of the head)
	 * @return the normalization of the range (diagonal ; 0) into the range (-5 ; 5)
	 */
	public default double normalizeRewardDistanceHeadApple(double value, double best, double worst) {
		
        double newWorst = -5.0;
        double newBest = 5.0;
        
        return (((value - worst) * (newBest - newWorst) / (best - worst)) + newWorst);
    }
}
