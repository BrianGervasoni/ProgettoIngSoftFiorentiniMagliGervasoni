package progettoAI.snakeAI;

import java.util.ArrayList;

public class Tools {
	public static double[] getArrayActivationFromNodes(ArrayList<Node> nodes) {
		ArrayList<Double> activation =  new ArrayList<Double>();
		for(Node node : nodes) {
			activation.add(node.getActivation());
		}
		return activation.stream()
        .mapToDouble(Double::doubleValue)
        .toArray();
	}
}
