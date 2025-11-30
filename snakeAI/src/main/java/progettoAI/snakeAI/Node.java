package progettoAI.snakeAI;
import java.util.Random;

public class Node {
	private float bias;
	private float activation;
	
	public Node(float bias) {
		this.bias = bias;
		this.activation = 0;
	}
	
	public Node() {// if the bias is not specificate we pick a random bias form -10 to 10
		Random rand = new Random();
		float range = 10 - (-10);
		this.bias = -10 * rand.nextFloat() * range;
		this.activation = 0;
	}

	public float getBias() {
		return bias;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}

	public float getActivation() {
		return activation;
	}

	public void setActivation(float activation) {
		this.activation = activation;
	}
	
}
