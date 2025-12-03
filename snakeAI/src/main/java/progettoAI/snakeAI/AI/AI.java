package progettoAI.snakeAI.AI;

import java.util.ArrayList;

public class AI {
	private ArrayList<Layer> layer;
	
	public AI(ArrayList<Layer> layer) {
		this.layer = (ArrayList<Layer>) layer.clone();
	}
}
