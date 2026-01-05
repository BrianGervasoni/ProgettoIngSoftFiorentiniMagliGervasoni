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
	 * copy the vector that rapresents a row of a matrix nRow time
	 * @param v
	 * @param nRow
	 * @return RealMatrix
	 */
	public static RealMatrix createRowMatrixFromVector(RealVector v, int nRow) {
		if(nRow == 1) {
			return MatrixUtils.createRowRealMatrix(v.toArray());
		}
		double [][] tmp = new double[nRow][v.getDimension()];
		for(int i=0;i<nRow;i++) {
			tmp[i] = v.toArray();
		}
		return MatrixUtils.createRealMatrix(tmp);
	}
	
	public static RealMatrix createColumnMatrixFromVector(RealVector v, int nCol) {
		if(nCol == 1) {
			return MatrixUtils.createColumnRealMatrix(v.toArray());
		}
		double [][] tmp = new double[v.getDimension()][nCol];
		for(int i=0;i<nCol;i++) {
			tmp[0][i] = v.getEntry(i);
		}
		return MatrixUtils.createRealMatrix(tmp);
	}
	
	/**
	 * perform the outerProduct (u * v)
	 * @param u (M X 1)
	 * @param v (1 X N)
	 * @return RealMatrix
	 */
	public static RealMatrix outerProduct(RealVector u, RealVector v) {
        // Step 1: Convert u to a column matrix (M x 1)
        RealMatrix colMatrix = MatrixUtils.createColumnRealMatrix(u.toArray());

        // Step 2: Convert v to a row matrix (1 x N)
        double[][] vData = new double[1][v.getDimension()];
        vData[0] = v.toArray();
        RealMatrix rowMatrix = new Array2DRowRealMatrix(vData);

        // Step 3: Multiply the two matrices (M x 1) * (1 x N) = (M x N)
        return colMatrix.multiply(rowMatrix);
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
	        if (existingMatrix == null) {
	            // If it is the first row, return it directly as the initial array
	            return newCol.reshape(newCol.length(),1);
	        } else {
	            // Concatenate along the 1-axis (column axis)
	            return Nd4j.concat(1, existingMatrix, newCol);
	        }
	    }
}
