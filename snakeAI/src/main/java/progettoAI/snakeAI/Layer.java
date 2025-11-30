package progettoAI.snakeAI;

import java.util.ArrayList;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

public abstract class Layer {
	private ArrayList<Node> nodes;
	private RealMatrix derivateActivation;
	private RealMatrix weights;
	private RealMatrix derivateWeights;
	
	public Layer(ArrayList<Node> nodes , double[][] weights) {
		this.nodes = nodes;
		this.weights = MatrixUtils.createRealMatrix(weights);
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public RealMatrix getDerivateActivation() {
		return derivateActivation;
	}

	public void setDerivateActivation(RealMatrix derivateActivation) {
		this.derivateActivation = derivateActivation;
	}

	public RealMatrix getWeights() {
		return weights;
	}
	
	public double getWeight(int x, int y) {
		return weights.getEntry(x, y);
	}

	public void setWeights(RealMatrix weights) {
		this.weights = weights;
	}
	public void setWeight(int x, int y, double val) {
		this.weights.setEntry(y, x, val);
	}

	public RealMatrix getDerivateWeights() {
		return derivateWeights;
	}

	public void setDerivateWeights(RealMatrix derivateWeights) {
		this.derivateWeights = derivateWeights;
	}
	
	
}
