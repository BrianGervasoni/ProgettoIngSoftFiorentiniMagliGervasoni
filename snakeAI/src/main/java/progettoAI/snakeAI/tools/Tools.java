package progettoAI.snakeAI.tools;


import java.util.Random;

import org.apache.commons.math3.linear.*;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public final class Tools {
	
	public static double lengthVector(INDArray x) {
		return Math.sqrt(Math.pow(x.getDouble(0),2)+Math.pow(x.getDouble(1),2));
	}
	
	public static double pickRandom(double min,double max) {
		Random rand = new Random();
		return  (rand.nextDouble() * (max - min))+min;
	}
	
	/**
	 * perform the clip function
	 * @param x
	 * @param a
	 * @param b
	 * @return double
	 */
	public static double clip(double x, double a, double b) {
		if(x<a)
			return a;
		if(x>b)
			return b;
		return x;
	}
	
	/**
	 * perform the derivate of the clip function
	 * @param x
	 * @param a
	 * @param b
	 * @return double
	 */
	public static double derivateClip(double x, double a, double b) {
		if(x<a)
			return 0;
		if(x>b)
			return 0;
		return 1;
	}
	
	public static INDArray appendCol(INDArray existingMatrix, INDArray newCol) {
		 if(newCol == null)
			 return null;
        if (existingMatrix == null) {
            // If it is the first row, return it directly as the initial array
            return newCol.reshape(newCol.length(),1);
        } else {
            // Concatenate along the 1-axis (column axis)
            return Nd4j.concat(1, existingMatrix, newCol);
        }
    }
}
