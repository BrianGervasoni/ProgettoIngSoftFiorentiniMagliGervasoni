package progettoAI.snakeAI.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AI {
	private ArrayList<Layer> layer;
	
	public AI(Layer[] layer) {
		this.layer = Arrays.stream(layer).collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * if only the length of the layers is indicated, by default the structure is composed of ReLu up to the penultimate layer, the last one uses SoftMax
	 * @param lenLayer
	 */
	public AI(int[] lenLayer) {
		try {
			layer = new ArrayList<Layer>(lenLayer.length);
			if(layer.size() > 0) {
				layer.add(new LayerReLu(lenLayer[0],0));
			}
			for(int i=0; i<lenLayer.length-2;i++) {
				layer.add(new LayerReLu(lenLayer[i],lenLayer[i-1]));
			}
			layer.add(new LayerSoftMax(lenLayer[lenLayer.length-1],lenLayer[lenLayer.length-2]));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
