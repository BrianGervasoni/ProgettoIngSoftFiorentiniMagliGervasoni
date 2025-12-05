package progettoAI.snakeAI.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import model.ActionRegister;

public abstract class AI {
	private ArrayList<Layer> layers;
	private TypeGradientUpdate mode;
	
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
			for(int i=1; i<lenLayer.length-1;i++) {//create layer with the corresponding weights and bias matrix dimension
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
		this.layers = (ArrayList<Layer>) layer.clone();	
	}
	
	public TypeGradientUpdate getMode() {
		return mode;
	}

	public void setMode(TypeGradientUpdate mode) {
		this.mode = mode;
	}

	/**
	 * return activation of the last layer in base of a date state, it dons't save the activation of all layers
	 * @param input
	 * @return activation of the last layer
	 */
	public double[] forwarding(double[] input) {
		return feedForwarding(input,0,false);
	}
	
	/**
	 * recursively goes through all the layers until the last one is activated, 
	 * if saveActivation is set to true it also saves all the intermediate activation layers, used in the backPropagation
	 * @param input
	 * @param i
	 * @param saveActivation
	 * @return
	 */
	private double[] feedForwarding(double[] input,int i,boolean saveActivation) {
		if(i<layers.size()) {//if we are at the last layer, return result
			return input;
		}
		//continues forwarding on all layers
		if(saveActivation) {
			return this.feedForwarding(layers.get(i).backForwarding(new ArrayRealVector(input)).toArray(),i+1,saveActivation);//in this case it's also saving the intermediates activation
		}else {
			return this.feedForwarding(layers.get(i).forwarding(new ArrayRealVector(input)).toArray(),i+1,saveActivation);//in this case it's not saving the intermediates activation
		}
		
	}
	
	public void initBackPropagation() {
		layers.forEach(e ->{
			e.initBackPropagation();
		});
		layers.get(layers.size()-1).setDerivateFromLossToActivation(this.derivateLoss());
	}
	
	public void tmpOptimize() {
		layers.forEach(e ->{
			e.tmpOptimization(this.getMode());
		});
	}
	
	public void optimize() {
		layers.forEach(e ->{
			e.optimization();
		});
	}
	
	public void backPropagation(ActionRegister r) {//TODO
		
	}
	public abstract RealVector lossCalculation();
	public abstract RealVector derivateLoss();
	
}
