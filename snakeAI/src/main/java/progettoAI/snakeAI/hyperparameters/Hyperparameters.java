package progettoAI.snakeAI.hyperparameters;

import org.nd4j.shade.jackson.annotation.JsonProperty;

public final class Hyperparameters {
	public static double alphaW = 0.3;
	public static double alphaB = 0.3;
	public static int epoche = 10;
	public static double minibacthSize = 0.8;
	public static double discount = 0.99;
	public static double lambda = 0.95; 
	public static int timeStep = 30;
	public static double motivation = 0.3;
	public static double entropyContribution = 0.5;
	
	 @JsonProperty("alphaW")
	public static double getAlphaW() {
		return alphaW;
	}
	 @JsonProperty("alphaW")
	public static void setAlphaW(double alphaW) {
		Hyperparameters.alphaW = alphaW;
	}
	 @JsonProperty("alphaB")
	public static double getAlphaB() {
		return alphaB;
	}
	 @JsonProperty("alphaB")
	public static void setAlphaB(double alphaB) {
		Hyperparameters.alphaB = alphaB;
	}
	 @JsonProperty("epoche")
	public static int getEpoche() {
		return epoche;
	}
	 @JsonProperty("epoche")
	public static void setEpoche(int epoche) {
		Hyperparameters.epoche = epoche;
	}
	 @JsonProperty("minibacthSize")
	public static double getMinibacthSize() {
		return minibacthSize;
	}
	 @JsonProperty("minibacthSize")
	public static void setMinibacthSize(double minibacthSize) {
		Hyperparameters.minibacthSize = minibacthSize;
	}
	 @JsonProperty("discount")
	public static double getDiscount() {
		return discount;
	}
	 @JsonProperty("discount")
	public static void setDiscount(double discount) {
		Hyperparameters.discount = discount;
	}
	 @JsonProperty("lambdae")
	public static double getLambda() {
		return lambda;
	}
	 @JsonProperty("lambda")
	public static void setLambda(double lambda) {
		Hyperparameters.lambda = lambda;
	}
	 @JsonProperty("timeStep")
	public static int getTimeStep() {
		return timeStep;
	}
	 @JsonProperty("timeStep")
	public static void setTimeStep(int timeStep) {
		Hyperparameters.timeStep = timeStep;
	}
	 @JsonProperty("motivation")
	public static double getMotivation() {
		return motivation;
	}
	 @JsonProperty("motivation")
	public static void setMotivation(double motivation) {
		Hyperparameters.motivation = motivation;
	}
	 @JsonProperty("entropyContribution")
	public static double getEntropyContribution() {
		return entropyContribution;
	}
	 @JsonProperty("entropyContribution")
	public static void setEntropyContribution(double entropyContribution) {
		Hyperparameters.entropyContribution = entropyContribution;
	}
	
	
}
