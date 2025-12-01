package progettoAI.snakeAI.tools;

import java.util.ArrayList;

import java.util.Random;

import org.apache.commons.math3.linear.*;

public final class Tools {
	
	public static double pickRandom(int min,int max) {
		Random rand = new Random();
		return min * rand.nextDouble() * (max - min);
	}
	
	/**
	 * copy the vector that rappresent a row of a matrix nRow time
	 * @param v
	 * @param nRow
	 * @return
	 */
	public static RealMatrix createMatrixFromVector(RealVector v, int nRow) {
		double [][] tmp = new double[nRow][v.getDimension()];
		for(int i=0;i<nRow;i++) {
			tmp[i] = v.toArray();
		}
		return MatrixUtils.createRealMatrix(tmp);
	}
}
