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
	
	public static RealMatrix outerProduct(RealVector u, RealVector v) {
        // Passo 1: Converti u in una matrice colonna (M x 1)
        // MatrixUtils.createColumnFieldMatrix crea una matrice con una singola colonna
        RealMatrix colMatrix = MatrixUtils.createColumnRealMatrix(u.toArray());

        // Passo 2: Converti v in una matrice riga (1 x N)
        // Array2DRowRealMatrix(double[][]) dove il primo array è la singola riga
        double[][] vData = new double[1][v.getDimension()];
        vData[0] = v.toArray();
        RealMatrix rowMatrix = new Array2DRowRealMatrix(vData);

        // Passo 3: Moltiplica le due matrici (M x 1) * (1 x N) = (M x N)
        // Il metodo multiply() esegue la moltiplicazione standard tra matrici
        return colMatrix.multiply(rowMatrix);
    }
}
