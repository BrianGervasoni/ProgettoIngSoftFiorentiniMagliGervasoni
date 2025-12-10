package ai;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;

import model.ActionRegister;
import progettoAI.snakeAI.AI.*;

public class AITest {

	public AITest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	void testBackProp() {//minibatch = 1, node=2, inputNode=3
		double[] b = new double[] {1,2};
		double[][] w = new double[2][3];
		for(int i=0; i< w.length;i++) {
			for(int j=0; j<w[0].length;j++) {//create matrix = [0,1,2],[1,2,3]
				w[i][j] = i+j;
			}
		}
		
		ActionRegister r = new ActionRegister();
		r.state = new double [] {1,0,1};
		LayerSoftMax softMax = new LayerSoftMax(b,w);
		AI ai = new AIActor(new LayerSoftMax[] {softMax},TypeGradientUpdate.DESCEND);
		r.actionsProb = ai.forwarding(r.state);
		r.indexAction = r.actionsProb.length-1;
		r.advantage = 2;
		r.reward = 4;
		r.vEstimated = 1;
		r.vTarget = 3;
		
		ai.initBackPropagation();
		ai.backPropagation(new ActionRegister[] {r});
		
		ai.optimization();
		
		double[] trueBias = new double[] {1.0081260267289072,1.9918739732710928};
		double[][] trueWeights = new double[][] {
			{ 0.0081260267289072,1.0000,2.0081260267289074},
			{0.9918739732710928,2.0000,2.991873973271093},
		};
		
		assertArrayEquals(trueBias,ai.getLayer().get(0).getBias().toDoubleVector());
		assertArrayEquals(trueWeights,ai.getLayer().get(0).getWeights().toDoubleMatrix());
	}

}
