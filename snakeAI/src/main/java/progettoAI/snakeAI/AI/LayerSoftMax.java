package progettoAI.snakeAI.AI;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.linear.*;8
import org.apache.commons.math3.util.FastMath;

import progettoAI.snakeAI.tools.Tools;

public class LayerSoftMax extends Layer {

	public LayerSoftMax(double[] bias, double[][] weights) {
		super(bias, weights);
		// TODO Auto-generated constructor stub
	}

	public LayerSoftMax(int lenLayer, int lenBackLayer) {
		super(lenLayer, lenBackLayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RealVector activationCalculus(RealVector preActivation) {
		 if (preActivation == null || preActivation.getDimension() == 0) {
	            return new ArrayRealVector(0);
	        }

	        // 1. (Numerically Stable) Find the maximum value in the vector to prevent exponential overflow
	        double maxVal = preActivation.getMaxValue();
	        
	        // Function to calculate e^(x - maxVal)
	        UnivariateFunction stableExp = new UnivariateFunction() {
	            @Override
	            public double value(double x) {
	                return FastMath.exp(x - maxVal); 
	            }
	        };

	        // 2. Calculate the stabilized exponential for each element in a new vector
	        RealVector expVector = preActivation.map(stableExp);

	        // 3. Calculate the stabilized exponential for each element in a new vector
	        double sumExponentials = expVector.getL1Norm(); // L1Norm è la somma dei valori assoluti, che sono tutti positivi qui

	        // 4. Normalize by dividing each element by the total sum.
	        // mapDivide creates a new result vector, leaving the original unchanged.
	        RealVector softmaxResult = expVector.mapDivide(sumExponentials);

	        return softmaxResult;
	}

	@Override
	public RealMatrix derivateFromActivationToPreActivation() {
		int N = this.getActivation().getDimension();
        // Initialize the NxN Jacobian matrix to zeros
        RealMatrix jacobian = MatrixUtils.createRealMatrix(N, N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double pi = this.getActivation().getEntry(i);
                double pj = this.getActivation().getEntry(j);
                
                if (i == j) {
                    // Diagonal case (i == j): p_i * (1 - p_i)
                    jacobian.setEntry(i, j, pi * (1.0 - pi));
                } else {
                    // Non-diagonal case (i != j): -p_i * p_j
                    jacobian.setEntry(i, j, -pi * pj);
                }
            }
        }
        return MatrixUtils.createRowRealMatrix(this.getDerivateFromLossToActivation().toArray()).multiply(jacobian);
	}

}
