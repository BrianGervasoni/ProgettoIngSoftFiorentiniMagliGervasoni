package thread;

import java.util.stream.DoubleStream;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import boxes.*;
import gioco.snakeAI.Map;
import progettoAI.snakeAI.tools.Tools;

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
	
	class Ray {
		
		INDArray direction, origin; //indice 0 per le x, indice 1 per le y

		public Ray(INDArray origin, INDArray direction) {
	        this.origin = origin;
	        this.direction = direction;
	    }
		
		public double originX() {
			return this.origin.getDouble(0);
		}
		
		public double originY() {
			return this.origin.getDouble(1);
		}
		
		public double directionX() {
			return this.direction.getDouble(0);
		}
		
		public double directionY() {
			return this.direction.getDouble(1);
		}
	    
	}
	
	public default INDArray degreesToVector(double degrees) {
	    // Converte in radianti
	    double radians = Math.toRadians(degrees);

	    // Calcola le componenti (assumendo 0 gradi = Est, senso antiorario) con attenznione alla conversione con il sistema di coordinate ella mappa
	    double x = -1*Math.sin(radians);
	    double y = Math.cos(radians);

	    return Nd4j.create(new double[] {x,y});
	}
	
	public default Ray[] rays(int startingDegree, int rePhasing, int n, int x, int y) {
		
		int[] angles = new int[n]; 
		Ray[] rays = new Ray[n];
		int alpha = startingDegree;
		int rePhase = 0;
		
		for(int i=0; i<n; i++) {
			
			alpha = startingDegree + rePhase;
			
			if(alpha < 0) { //when the snake it's in the range >270 and <90 i've used the convention [-180; 180] so i have to switch back to [0; 360]
				//System.out.println("alpha : " + alpha);
				angles[i] = alpha + 360;
				//System.out.println("ray : " + rays[i]);
			}else {
				angles[i] = alpha;
				//System.out.println("ray AHHH: " + rays[i]);
			}
			
			rePhase = rePhase + rePhasing; 
			
		}
		
		for(int i=0; i<n; i++) {
			rays[i] = new Ray(Nd4j.create(new double[] {x,y}), degreesToVector(angles[i]));
		}
		
		return rays;
			
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
	
	public default double calculateRayDistance(Box box, Box box1, Ray ray) {
	   double tmin = Double.NEGATIVE_INFINITY;
	   double tmax = Double.POSITIVE_INFINITY;
		
	   double tx1 = ((box.getXcoordinate()-0.5) - ray.originX()) / ray.directionX();
	   double tx2 = ((box.getXcoordinate()+0.5) - ray.originX()) / ray.directionX();
	   tmin = Math.max(tmin, Math.min(tx1, tx2));
	   
	   tx1 = ((box.getXcoordinate()-0.5) - ray.originY()) / ray.originY();
	   tx2 = ((box.getXcoordinate()+0.5) - ray.originY()) / ray.originY();
	   tmin = Math.max(tmin, Math.min(tx1, tx2));
	   return Tools.lengthVector(ray.direction.mul(tmin));
	}
	
	/**
	 * 
	 * @param box
	 * @param ray
	 * @return
	 */
	public default boolean checkCollision(Box box, Ray ray) {
		
		 double tmin = Double.NEGATIVE_INFINITY;
	     double tmax = Double.POSITIVE_INFINITY;
	        
		// Controllo per l'asse X
        if (ray.directionX() != 0.0) {
            double tx1 = ((box.getXcoordinate()-0.5) - ray.originX()) / ray.directionX();
            double tx2 = ((box.getXcoordinate()+0.5) - ray.originX()) / ray.directionX();
            tmin = Math.max(tmin, Math.min(tx1, tx2));
            tmax = Math.min(tmax, Math.max(tx1, tx2));
        }

        // Controllo per l'asse Y
        if (ray.directionY() != 0.0) {
            double tx1 = ((box.getXcoordinate()-0.5) - ray.originY()) / ray.originY();
            double tx2 = ((box.getXcoordinate()+0.5) - ray.originY()) / ray.originY();
            tmin = Math.max(tmin, Math.min(tx1, tx2));
            tmax = Math.min(tmax, Math.max(tx1, tx2));
        }

        // Se tmax < 0, la collisione è alle spalle del raggio
        // Se tmin > tmax, non c'è intersezione
        return tmax >= tmin && tmax >= 0;
	}
	
	/**
	 * 
	 * @param map
	 * @param food
	 * @param walls
	 * @param snake
	 * @param rays
	 * change the value on the array of rays (food, walls and snake) and set the array[index] = distance , for every ray that is involved
	 */
	public default void setValuesArrays(Map map, double[] food, double[] walls, double[] snake, Ray[] rays) {
		
		for(int k = 0; k < rays.length; k++) {
			
			for(int i=0; i<map.X; i++) {
				for(int j=0; j<map.Y; j++) {
					
					if(rays[k].originX() == i && rays[k].originY() == j) {
						
						continue;
					}
					
					if(checkCollision(map.getBox(i, j),rays[k])) {
						System.out.println("raggio("+k+")"+"collisione con:"+map.getBox(i,j).getElementType());
						
						if(map.getBox(i, j).getElementType().equals(MapElem.WALL)) {
							walls[k] = calculateRayDistance(map.getBox(i, j), map.getBox((int)rays[k].originX(), (int)rays[k].originY()),rays[k]);
						}
						if(map.getBox(i, j).getElementType().equals(SnakeBody.BODY) || map.getBox(i, j).getElementType().equals(SnakeBody.TAIL)) {
							snake[k] = calculateRayDistance(map.getBox(i, j), map.getBox((int)rays[k].originX(), (int)rays[k].originY()),rays[k]);
						}
						if(map.getBox(i, j).getElementType().equals(Food.APPLE)) {
							food[k] = calculateRayDistance(map.getBox(i, j), map.getBox((int)rays[k].originX(), (int)rays[k].originY()),rays[k]);
						}
						
					}
					
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
		
		return DoubleStream.concat(DoubleStream.concat(DoubleStream.of(a1), DoubleStream.of(a2)), DoubleStream.of(a3)).toArray();
	}
	
	public default double[] merge(double[] a1, double[] a2) {
		
		return DoubleStream.concat(DoubleStream.of(a1), DoubleStream.of(a2)).toArray();
	}
	
	/**
	 * 
	 * @param array
	 * @return linear normalization, set the value of array[i] at its new linear normalized value (a value in this interval [0;1])
	 */
	public default double[] normalizeArray(double[] array, Map map) {
		
		double min = 0;
		double max = Math.sqrt(map.X*map.X + map.Y*map.Y);
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
