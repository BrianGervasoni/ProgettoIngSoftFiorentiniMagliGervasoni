package ai;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;

import progettoAI.snakeAI.AI.*;

public class LayerTest {

	public LayerTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	void testForwarding() {
		double[] b = new double[] {1,2};
		double[][] w = new double[2][3];
		for(int i=0; i< w.length;i++) {
			for(int j=0; j<w[0].length;j++) {
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
}
