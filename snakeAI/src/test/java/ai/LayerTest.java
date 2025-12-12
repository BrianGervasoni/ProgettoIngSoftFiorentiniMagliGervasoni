package ai;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.AI.*;
import progettoAI.snakeAI.tools.Tools;

public class LayerTest {

	public LayerTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	void testForwarding() {//node=2, inputNode=3
		double[] b = new double[] {1,2};
		double[][] w = new double[2][3];
		for(int i=0; i< w.length;i++) {
			for(int j=0; j<w[0].length;j++) {//create matrix = [0,1,2],[1,2,3]
				w[i][j] = i+j;
			}
		}
		
		double[] backLayerActivation = new double [] {5,6,7};
		double[] trueResultIdent = new double [] {21,40};
		double[] trueResultReLu = new double [] {21,40};
		double[] trueResultSoftMax = new double[] {5.60279640614594E-9,0.9999999943972036};
		
		Layer identity = new LayerIdentityFunction(b,w);
		Layer relu = new LayerReLu(b,w);
		Layer softMax = new LayerSoftMax(b,w);
		double[] resultIde = identity.forwarding(Nd4j.create(backLayerActivation).reshape(backLayerActivation.length,1)).toDoubleVector();
		double[] resultReLu = relu.forwarding(Nd4j.create(backLayerActivation).reshape(backLayerActivation.length,1)).toDoubleVector();
		double[] resultSoftMax = softMax.forwarding(Nd4j.create(backLayerActivation).reshape(backLayerActivation.length,1)).toDoubleVector();
		
		assertArrayEquals(trueResultIdent,resultIde);
		assertArrayEquals(trueResultReLu,resultReLu);
		assertArrayEquals(trueResultSoftMax,resultSoftMax);
	}
	
	@Test
	void testForwardPass() {//minibatch = 2, node=2, inputNode=3
		double[] b = new double[] {1,2};
		double[][] w = new double[2][3];
		for(int i=0; i< w.length;i++) {
			for(int j=0; j<w[0].length;j++) {//[0,1,2],[1,2,3]
				w[i][j] = i+j;
			}
		}
		
		double[][] backLayerActivation = {
				{5.0,6.0,7.0},
				{1.0,2.0,3.0}
		};
				
		double[][] trueResultIdent = {
				{21.0,9.0},
				{40.0,16.0}
		};
		
		
		double[][] trueResultReLu= {
				{21.0,9.0},
				{40.0,16.0}
		};
		
		double[][] trueResultSoftMax = {
				{5.60279640614594E-9,9.110511944006454E-4},
				{0.9999999943972036, 0.9990889488055994 }
		};
		
		Layer identity = new LayerIdentityFunction(b,w);
		Layer relu = new LayerReLu(b,w);
		Layer softMax = new LayerSoftMax(b,w);
		double[][] resultIde = identity.forwardPass(Nd4j.create(backLayerActivation).transpose()).toDoubleMatrix();
		double[][] resultReLu = relu.forwardPass(Nd4j.create(backLayerActivation).transpose()).toDoubleMatrix();
		double[][] resultSoftMax = softMax.forwardPass(Nd4j.create(backLayerActivation).transpose()).toDoubleMatrix();
		
		assertArrayEquals(trueResultIdent,resultIde);
		assertArrayEquals(trueResultReLu,resultReLu);
		assertArrayEquals(trueResultSoftMax,resultSoftMax);
		
		assertArrayEquals(trueResultIdent, identity.getPreActivation_cache().toDoubleMatrix());
		assertArrayEquals(trueResultIdent, relu.getPreActivation_cache().toDoubleMatrix());
		assertArrayEquals(trueResultIdent, softMax.getPreActivation_cache().toDoubleMatrix());
	}
	
	@Test
	void testBackPropagation() {//minibatch = 2, node=2, inputNode=3
		double[] b = new double[] {1,2};
		double[][] w = new double[2][3];
		for(int i=0; i< w.length;i++) {
			for(int j=0; j<w[0].length;j++) {//[0,1,2],[1,2,3]
				w[i][j] = i+j;
			}
		}
		
		double[][] backLayerActivation = {
				{5.0,6.0,7.0},
				{1.0,2.0,3.0}
		};
		
		double[][] loss = {
				{1.0,2.0},
				{1.0,1.0}
		};
		
		double[][] trueResultIdent = {
				{1.0,1.0},
				{3.0,4.0},
				{5.0,7.0}
		};
		
		double[][] trueResultReLu = {
				{1.0,1.0},
				{3.0,4.0},
				{5.0,7.0}
		};
		
		double[][] trueResultSoftMax = {
				{0.0,-9.10221180121784E-4},
				{0.0,   -9.102211801217415E-4},
				{0.0,   -9.10221180121699E-4}
		};
		
		double[] trueTmpBiasIde = new double[] {0.55 ,1.7};
		double[][] trueTmpWeightsIde = {
				{-1.05,-0.5,0.050000000000000044},
				{0.10000000000000009,0.8,1.5}
		};
		
		double[] trueTmpBiasReLu = new double[] {0.55 ,1.7};
		double[][] trueTmpWeightsReLu = {
				{-1.05,-0.5,0.050000000000000044},
				{0.10000000000000009,0.8,1.5}
		};
		
		double[] trueTmpBiasSoftMax = new double[] {0.9998634668229818,2.0001365331770184};
		double[][] trueTmpWeightsSoftMax = {
				{-1.3653317701827397E-4,0.9997269336459634,1.999590400468945},
				{1.0001365331770182,2.0002730663540365,3.000409599531055}
		};
		
		
		Layer identity = new LayerIdentityFunction(b,w);
		Layer relu = new LayerReLu(b,w);
		Layer softMax = new LayerSoftMax(b,w);
		identity.forwardPass(Nd4j.create(backLayerActivation).transpose());
		relu.forwardPass(Nd4j.create(backLayerActivation).transpose());
		softMax.forwardPass(Nd4j.create(backLayerActivation).transpose());
		
		identity.initBackProp();
		relu.initBackProp();
		softMax.initBackProp();
		
		double[][] resultIde = identity.backPropagation(Nd4j.create(loss),TypeGradientUpdate.DESCEND,2).toDoubleMatrix();
		double[][] resultReLu = relu.backPropagation(Nd4j.create(loss),TypeGradientUpdate.DESCEND,2).toDoubleMatrix();
		double[][] resultSoftMax = softMax.backPropagation(Nd4j.create(loss),TypeGradientUpdate.DESCEND,2).toDoubleMatrix();
		
		assertArrayEquals(trueResultIdent,resultIde);
		assertArrayEquals(trueResultReLu,resultReLu);
		assertArrayEquals(trueResultSoftMax,resultSoftMax);
		
		assertArrayEquals(trueTmpBiasIde,identity.getTmpBias().toDoubleVector());
		assertArrayEquals(trueTmpWeightsIde,identity.getTmpWeights().toDoubleMatrix());
		
		assertArrayEquals(trueTmpBiasReLu,relu.getTmpBias().toDoubleVector());
		assertArrayEquals(trueTmpWeightsReLu,relu.getTmpWeights().toDoubleMatrix());
		
		assertArrayEquals(trueTmpBiasSoftMax,softMax.getTmpBias().toDoubleVector());
		assertArrayEquals(trueTmpWeightsSoftMax,softMax.getTmpWeights().toDoubleMatrix());
	}
}
