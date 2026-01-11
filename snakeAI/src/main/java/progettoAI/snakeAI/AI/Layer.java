package progettoAI.snakeAI.AI;

import org.nd4j.linalg.api.memory.MemoryWorkspace;
import org.nd4j.linalg.api.memory.conf.WorkspaceConfiguration;
import org.nd4j.linalg.api.memory.enums.AllocationPolicy;
import org.nd4j.linalg.api.memory.enums.LearningPolicy;
import org.nd4j.linalg.api.memory.enums.SpillPolicy;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.activations.*;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.BooleanIndexing;
import org.nd4j.linalg.indexing.conditions.Conditions;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.nd4j.common.primitives.Pair;


import progettoAI.snakeAI.tools.Tools;
import progettoAI.snakeAI.hyperparameters.*;
import errorHandler.ArithmeticException;


public abstract class Layer {
	/*private RealVector bias;
	private RealVector tmpBias;
	private RealVector preActivation;
	private RealVector activation;
	private RealVector derivateFromLossToBias;
	private RealMatrix weights;
	private RealMatrix tmpWeights;
	private RealMatrix derivateFromLossToWeights;
	private RealVector derivateFromLossToActivation;*/
	private INDArray bias;
	private INDArray weights;
	
	private transient INDArray tmpBias;
	private transient INDArray tmpWeights;
	
	private transient INDArray backLayerActivation_cache;
	private transient INDArray preActivation_cache;
	
	private transient INDArray cumulativeDLdW;
	private transient INDArray cumulativeDLdB;
	
	private IActivation activation;

	/**
	 * W [output X input]
	 * @param bias [numberOfNodeInThisLayer]
	 * @param weights [numberOfNodeInThisLayer, numberOfNodeInTheBackLayer]
	 */
	public Layer(double[] bias , double[][] weights) {
		/*this.bias = MatrixUtils.createRealVector(bias);
		this.weights = MatrixUtils.createRealMatrix(weights);*/
		this.bias = Nd4j.create(bias).reshape(bias.length,1);//NX1
		this.weights = Nd4j.create(weights);//NXK
		this.cumulativeDLdW = Nd4j.zerosLike(this.weights);
        this.cumulativeDLdW = Nd4j.zerosLike(this.bias);
	}
	
	/**
	 * create layer with the specificate number of node and random value for bias and weights from -10 to 10
	 * W [output X input]
	 * @param lenLayer
	 * @param lenNextLayer
	 */
	public Layer(int lenLayer, int lenBackLayer) {
		double [] tmpBias = new double[lenLayer];
		double[][] tmpWeights = new double[lenLayer][lenBackLayer];
		for(int i=0;i<lenLayer;i++) {
			tmpBias[i] = Tools.pickRandom(-0.1, 0.1);
			for(int j=0;j<lenBackLayer;j++) {
				tmpWeights[i][j] = Tools.pickRandom(-0.1, 0.1);
			}
		}
		
		this.bias = Nd4j.create(tmpBias).reshape(tmpBias.length,1);//NX1
		this.weights = Nd4j.create(tmpWeights);//NXK
		this.cumulativeDLdW = Nd4j.zerosLike(this.weights);
        this.cumulativeDLdW = Nd4j.zerosLike(this.bias);
	}
	
	

	public INDArray getBias() {
		return bias;
	}

	public void setBias(INDArray bias) {
		this.bias = bias.detach();
	}

	public INDArray getWeights() {
		return weights;
	}

	public void setWeights(INDArray weights) {
		this.weights = weights.detach();
	}
	
	
	
	public INDArray getTmpBias() {
		return tmpBias;
	}

	public void setTmpBias(INDArray tmpBias) {
		this.tmpBias = tmpBias.detach();
	}

	public INDArray getTmpWeights() {
		return tmpWeights;
	}

	public void setTmpWeights(INDArray tmpWeights) {
		this.tmpWeights = tmpWeights.detach();
	}

	public INDArray getBackLayerActivation_cache() {
		return backLayerActivation_cache;
	}

	public void setBackLayerActivation_cache(INDArray backLayerActivation_cache) {
		this.backLayerActivation_cache = backLayerActivation_cache.detach();
	}

	public INDArray getPreActivation_cache() {
		return preActivation_cache;
	}

	public void setPreActivation_cache(INDArray preActivation_cache) {
		this.preActivation_cache = preActivation_cache.detach();
	}

	public INDArray getCumulativeDLdW() {
		return cumulativeDLdW;
	}

	public void setCumulativeDLdW(INDArray cumulativeDLdW) {
		this.cumulativeDLdW = cumulativeDLdW.detach();
	}

	public INDArray getCumulativeDLdB() {
		return cumulativeDLdB;
	}

	public void setCumulativeDLdB(INDArray cumulativeDLdB) {
		this.cumulativeDLdB = cumulativeDLdB.detach();
	}

	public IActivation getActivation() {
		return activation;
	}

	public void setActivation(IActivation activation) {
		this.activation = activation;
	}

	/**
	 * calculate the activation function of this layer from the previus layer activation
	 * @param backLayerActivation: INDArray with the back layer activation value
	 * @param saveActivation: specificate if the layer save the intermediary values, usend during backPropagation
	 * @return INDArray with this layer activation value
	 * @throws ArithmeticException 
	 */
	public INDArray forwarding(INDArray backLayerActivation) throws ArithmeticException {
		//((NXK) * (KX1)) + (NX1) = (NX1) but the activation function need (1XN) so we do the transpose
		try {
			return this.getActivation().getActivation(this.getWeights().mmul(backLayerActivation).add(this.getBias()).transpose(), false).transpose();//sigma(W*A+B)
		}catch(Exception e) {
			e.printStackTrace();
			throw new ArithmeticException(e.getMessage(),e.getCause());
		}
		
	}
	
	/**
	 * Performs Forward Propagation for an input minibatch
	 * @param backLayerActivation: The input minibatch [backLayerActivationSize, BatchSize]
	 * @return activation of this layer for the entire minibatch
	 * @throws ArithmeticException 
	 */
	public INDArray forwardPass(INDArray backLayerActivation) throws ArithmeticException {
		try {
			this.setBackLayerActivation_cache(backLayerActivation);//KXM
			// 1. Calcolo Pre-Attivazione Lineare (NXM)
	        INDArray z = this.getWeights().mmul(backLayerActivation).add(this.getBias());

	        // 2. LAYER NORMALIZATION STEP
	        // Calcoliamo media e varianza lungo la dimensione delle feature (dim 0) per ogni esempio (colonna)
	        INDArray mean = z.mean(0); // Media per ogni esempio nel batch
	        INDArray var = z.var(0);   // Varianza per ogni esempio nel batch
	        double epsilon = 1e-8;

	        // Normalizzazione: (z - mean) / sqrt(var + eps)
	        INDArray zCentered = z.subRowVector(mean);
	        INDArray stdDev = Transforms.sqrt(var.add(epsilon));
	        INDArray zNorm = zCentered.divRowVector(stdDev);
		
	        //((NXK) * (KXM)) + (NX1) = (NXM) use broadcasting for the bias
	        this.setPreActivation_cache(zNorm); 
			
			//this.setPreActivation_cache(this.getWeights().mmul(backLayerActivation).add(this.getBias()));//W*A+B
			
			//the activation need (MXN) so we do the transpose. Duplicate the array because we don't want it to change
			return this.getActivation().getActivation(this.getPreActivation_cache().transpose().dup(), true).transpose();
		}catch(Exception e) {
			throw new ArithmeticException(e.getMessage(),e.getCause());
		}
		
	}
	
	/**
	 * calculate derivates from loss to parameters, add the cumulative derivates for the next stochastic calculus
	 * @param dLdA
	 * @param mode
	 * @return dLdA
	 * @throws ArithmeticException 
	 */
	public INDArray derivateCalculus(INDArray dLdA,TypeGradientUpdate mode,int minibatchSize) throws ArithmeticException {
		 try {
			  if (dLdA.isNaN().any()) {
		        System.err.println("INSTABILITA RILEVATA: alcune derivate sono NaN.");
			 }
			Pair<INDArray, INDArray> gradientPair = this.activation.backprop(this.getPreActivation_cache().transpose(), dLdA.transpose());
			INDArray dLdZnorm = gradientPair.getFirst().transpose(); // (NXM)
				
			// 2. BACKPROP DELLA LAYER NORM (Semplificato)
			// In un'implementazione completa, qui dovresti trasformare dLdZnorm in dLdZ_lineare
			// considerando la derivata della media e varianza. 
			// Per semplicità e stabilità PPO, molti framework scalano dLdZnorm per la stdDev.
			INDArray dLdZ = dLdZnorm.divRowVector(Transforms.sqrt(this.getPreActivation_cache().var(0).add(1e-8)));
			 
			 //(NXM) * (MXK) = (NXK)
			INDArray dLdW = dLdZ.mmul(this.getBackLayerActivation_cache().transpose());
			 
			this.tmpOptimization(dLdW,dLdZ.sum(1).reshape(dLdZ.rows(),1),mode,minibatchSize);//si prende solo una riga per il dLdB dal dLdZ (NX1)
			 // (KXN) * (NXM) = (KXM) 
			return this.getWeights().transpose().mmul(dLdZ);
		 }catch(Exception e) {
				throw new ArithmeticException(e.getMessage(),e.getCause());
			}
		
	}
	
	/**
	 * perform a step in the backPropagation phase
	 * @param dLdA
	 * @param mode
	 * @param minibatchSize
	 * @return dLdA
	 * @throws ArithmeticException 
	 */
	public INDArray backPropagation(INDArray dLdA,TypeGradientUpdate mode,int minibatchSize) throws ArithmeticException {
		return this.derivateCalculus(dLdA, mode,minibatchSize);
	}
	
	/**
	 * initialize the copy parameters to the true value of the parameters
	 */
	public void initBackProp() {
		this.setTmpWeights(this.getWeights());
		this.setTmpBias(this.getBias());
	}
	
	/**
	 * optimize the parameters without changing the true value
	 * @param dLdW
	 * @param dLdB
	 * @param mode
	 * @throws ArithmeticException 
	 */
	public void tmpOptimization(INDArray dLdW,INDArray dLdB,TypeGradientUpdate mode,int minibatchSize) throws ArithmeticException {
		try {
	        // 1. CONTROLLO PREVENTIVO: Se i gradienti in ingresso sono già NaN, non aggiornare
	        if (dLdW.isNaN().any() || dLdB.isNaN().any()) {
	            System.err.println("ATTENZIONE: Gradienti NaN ricevuti. Salto questo step di ottimizzazione.");
	            return; 
	        }

	        // 2. GRADIENT CLIPPING (Norm-based o Global)
	        double maxGradNorm = 0.5;
	        double gradNormW = dLdW.norm2Number().doubleValue();
	        double gradNormB = dLdB.norm2Number().doubleValue();

	        if (gradNormW > maxGradNorm) {
	            dLdW.muli(maxGradNorm / (gradNormW + 1e-8));
	        }
	        if (gradNormB > maxGradNorm) {
	            dLdB.muli(maxGradNorm / (gradNormB + 1e-8));
	        }

	        // 3. CALCOLO LEARNING RATE
	        double lrW = Hyperparameters.alphaW / (double) minibatchSize;
	        double lrB = Hyperparameters.alphaB / (double) minibatchSize;

	        // 4. AGGIORNAMENTO
	        switch(mode) {
	            case ASCEND:
	                this.getTmpWeights().addi(dLdW.mul(lrW));
	                this.getTmpBias().addi(dLdB.mul(lrB));
	                break;
	            case DESCEND:
	                this.getTmpWeights().subi(dLdW.mul(lrW));
	                this.getTmpBias().subi(dLdB.mul(lrB));
	                break;
	        }

	        // 5. POST-CHECK DI SICUREZZA
	        if (this.getTmpBias().isNaN().any()) {
	            throw new Exception("Bias esplosi (NaN). Riduci il Learning Rate!");
	        }
	        
	        if (this.getTmpWeights().isNaN().any()) {
	            throw new Exception("Pesi esplosi (NaN) dopo l'ottimizzazione. Riduci il Learning Rate!");
	        }

	    } catch(Exception e) {
	        throw new ArithmeticException("Errore in ottimizzazione: " + e.getMessage(), e.getCause());
	    }
	}
	
	/**
	 * change the parameters to the optimizated one
	 */
	public void optimization() {
		this.setWeights(this.getTmpWeights());
		this.setBias(this.getTmpBias());
	}
}
