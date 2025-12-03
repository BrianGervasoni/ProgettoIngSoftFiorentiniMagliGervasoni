package progettoAI.snakeAI.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class AI {
	private ArrayList<Layer> layers;
	
	public AI(Layer[] layers) {
		this.layers = Arrays.stream(layers).collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * if only the length of the layers is indicated, by default the structure is composed of ReLu up to the penultimate layer, the last one uses SoftMax
	 * @param lenLayer
	 */
	public AI(int[] lenLayer) {
		try {
			layers = new ArrayList<Layer>(lenLayer.length);
			if(layers.size() > 0) {//the first layer dosn't have a back layer
				layers.add(new LayerReLu(lenLayer[0],0));
			}
			for(int i=0; i<lenLayer.length-2;i++) {//create layer with the corresponding weights and bias matrix dimension
				layers.add(new LayerReLu(lenLayer[i],lenLayer[i-1]));
			}
			layers.add(new LayerSoftMax(lenLayer[lenLayer.length-1],lenLayer[lenLayer.length-2]));//the last layer use softMax
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Layer> getLayer() {
		return layers;
	}

	public void setLayer(ArrayList<Layer> layer) {
		this.layers = layer;
	}
	
	/**
	 * return activation of the last layer in base of a date state
	 * @param input
	 * @return activation of the last layer
	 */
	public double[] feedForwarding(double[] input) {
		return forwarding(input,0);
	}
	
	private double[] forwarding(double[] input,int i) {
		if(i<layers.size()) {//if we are at the last layer, return result
			return input;
		}
		//continues forwarding on all layers
		return this.forwarding(layers.get(i).forwarding(new ArrayRealVector(input)).toArray(),i+1);
	}
	
}
