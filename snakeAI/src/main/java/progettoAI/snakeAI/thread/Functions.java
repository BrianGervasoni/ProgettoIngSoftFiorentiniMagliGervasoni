package progettoAI.snakeAI.thread;

import java.util.stream.DoubleStream;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.boxes.*;
import progettoAI.snakeAI.game.Map;

public interface Functions {
	
	/**
	 * 
	 * @param array
	 * @param n
	 */
	public default double[] initializeArray(int n) {
		
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
		
		public int originX() {
			return (int) this.origin.getDouble(0);
		}
		
		public int originY() {
			return (int) this.origin.getDouble(1);
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
	
	public default double calculateRayDistance(Box box, Ray ray) {
		double[] min =new double[] {box.getXcoordinate() - 0.5, box.getYcoordinate() - 0.5};
        double[] max = new double[] {box.getXcoordinate() + 0.5, box.getYcoordinate() + 0.5};
        double[] direction = new double[] {ray.directionX(), ray.directionY()};
        double[] origin = new double[] {ray.originX(), ray.originY()};
        double tNear = Double.NEGATIVE_INFINITY;
        double tFar = Double.POSITIVE_INFINITY;

        for (int i = 0; i < 2; i++) {
            if (direction[i] != 0.0) {
                double t1 = (min[i] - origin[i]) / direction[i];
                double t2 = (max[i] - origin[i]) / direction[i];

                // t1 deve essere l'entrata e t2 l'uscita su questo asse
                double tEntry = Math.min(t1, t2);
                double tExit = Math.max(t1, t2);

                // Restringiamo l'intervallo globale
                tNear = Math.max(tNear, tEntry);
                tFar = Math.min(tFar, tExit);
            } else {
                // Se il raggio è parallelo e fuori dai limiti dell'asse, nessuna collisione
                if (origin[i] < min[i] || origin[i] > max[i]) return -1;
            }
        }

        // CONDIZIONI CRITICHE:
        // 1. tFar >= tNear: Il raggio attraversa effettivamente la box
        // 2. tFar > 0: La box non deve essere completamente dietro il raggio
        // 3. tNear > 0: Il punto di entrata deve essere davanti (questo rimuove le collisioni che coincidono con l'origine)
        
        if( tFar >= tNear && tFar > 0 && tNear > 0 ) {
        	return Math.abs(tFar);
        }else{
        	return -1;
        }
	}
	
	/**
	 * 
	 * @param box
	 * @param ray
	 * @return
	 */
	public default boolean checkCollision(Box box, Ray ray) {
		
        if(calculateRayDistance(box,ray) != -1) {
        	return true;
        }else {
        	return false;
        }
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
			//System.out.println("origin a:"+rays[k].originX()+","+rays[k].originY()+")");
			for(int i=0; i<map.X; i++) {
				for(int j=0; j<map.Y; j++) {
					
					if(checkCollision(map.getBox(i, j),rays[k])) {
						//System.out.println("raggio("+k+")"+"collisione con:"+map.getBox(i,j).getElementType()+" a coordinate("+i+","+j+")");
						
						if(map.getBox(i, j).getElementType().equals(MapElem.WALL)) {
							walls[k] = calculateRayDistance(map.getBox(i, j),rays[k]);
						}
						if(map.getBox(i, j).getElementType().equals(SnakeBody.BODY) || map.getBox(i, j).getElementType().equals(SnakeBody.TAIL)) {
							snake[k] = calculateRayDistance(map.getBox(i, j),rays[k]);
						}
						if(map.getBox(i, j).getElementType().equals(Food.APPLE)) {
							food[k] = calculateRayDistance(map.getBox(i, j),rays[k]);
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
	public default double[] normalizeRay(double[] array, Map map) {
		
		double min = 0;
		double max = Math.sqrt(map.X*map.X + map.Y*map.Y);
		double xNormalizzato;
		
		//set non found rays to max distance
		for(int i = 0; i<array.length; i++) {
			
			if(array[i]<=min) {
				array[i] = max;
			}
		}
		
		for(int i = 0; i<array.length; i++) {

			xNormalizzato = 1-(array[i]/max);
			array[i] = xNormalizzato;
			
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param value of the distance
	 * @param best = min value
	 * @param worst = max value
	 * @return the normalization of the range (best ; worst) into the range (-1 ; 1)
	 */
	public default double normalizeRewardDistanceHeadApple(double value, double best, double worst) {
		
        double newWorst = -1;
        double newBest = 1;
        
        return (((value - worst) * (newBest - newWorst) / (best - worst)) + newWorst);
    }
}
