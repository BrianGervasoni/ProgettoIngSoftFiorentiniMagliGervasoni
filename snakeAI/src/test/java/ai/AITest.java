package ai;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.AI.*;
import progettoAI.snakeAI.errorHandler.ArithmeticException;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;
import progettoAI.snakeAI.model.ActionRegister;

public class AITest {

	@Test
	void testBackProp() {
		try {
			double[] b = new double[] {1,2};
			double[][] w = new double[2][3];
			for(int i=0; i< w.length;i++) {//minibatch = 1, node=2, inputNode=3
				for(int j=0; j<w[0].length;j++) {//create matrix = [0,1,2],[1,2,3]
					w[i][j] = i+j;
				}
			}
			
			double[] b2 = new double[] {1};
			double[][] w2 = new double[1][3];
			for(int i=0; i< w.length;i++) {
				for(int j=0; j<w[0].length;j++) {//create matrix = [0,1,2]
					w[i][j] = i+j;
				}
			}
			
			ActionRegister r = new ActionRegister();
			r.state = new double [] {1,0,1};
			LayerSoftMax softMax = new LayerSoftMax(b,w);
			LayerIdentityFunction id = new LayerIdentityFunction(b2,w2);
			AI ai = new AIActor(new LayerSoftMax[] {softMax},TypeGradientUpdate.DESCEND);
			AI crit = new AICritic(new LayerIdentityFunction[] {id},TypeGradientUpdate.ASCEND);
			r.actionsProb = ai.forwarding(r.state);
			r.indexAction = r.actionsProb.length-1;
			r.advantage = 2;
			r.reward = 4;
			r.vEstimated = crit.forwarding(r.state)[0];
			r.vTarget = 3;
			
			ai.initBackPropagation();
			crit.initBackPropagation();
			ai.backPropagation(new ActionRegister[] {r},0);
			crit.backPropagation(new ActionRegister[] {r},0);
			
			ai.optimization();
			crit.optimization();
			
			double[] trueBias = new double[] {1.0081260267289072,1.9918739732710928};
			double[][] trueWeights = new double[][] {
				{ 0.0081260267289072,1.0000,2.0081260267289074},
				{0.9918739732710928,2.0000,2.991873973271093},
			};
			
			double[] trueBiasCrit = new double [] {-0.19999999999999996};
			double[][] trueWeightsCrit = new double [][] {
					{-1.2,0.0,-1.2}
			};
			
			assertArrayEquals(trueBiasCrit,crit.getLayer().get(0).getBias().toDoubleVector());
			assertArrayEquals(trueWeightsCrit,crit.getLayer().get(0).getWeights().toDoubleMatrix());
			assertArrayEquals(trueBias,ai.getLayer().get(0).getBias().toDoubleVector());
			assertArrayEquals(trueWeights,ai.getLayer().get(0).getWeights().toDoubleMatrix());
		}catch(ArithmeticException e) {
			e.printStackTrace();
		}
		
	}

}
