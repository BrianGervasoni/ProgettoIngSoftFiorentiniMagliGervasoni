package progettoAI.snakeAI.AI.thread;

public class Statistic {
	public double meanReward = 0;
	public double meanLength = 0;
	public int episode = 0;
	public double meanLossEntropy = 0;
	public double meanDominance = 0;
	public double meanLossActor = 0;
	public double meanLossCritic = 0;
	public double sma;
	
	public Statistic() {
		
	}
	
	public Statistic(double meanReward,double meanLength,int episode,double meanLossEntropy,double meanDominance,double meanLossActor,double meanLossCritic,double sma) {
		this.meanReward = meanReward;
		this.meanLength = meanLength;
		this.episode = episode;
		this.meanLossEntropy = meanLossEntropy;
		this.meanDominance = meanDominance;
		this.meanLossActor = meanLossActor;
		this.meanLossCritic = meanLossCritic;
		this.sma = sma;
	}
	
	@Override
	public String toString() {
		return  "<html>"	+
				
				"episode: " 		+ this.episode 			+ " | " + "smaReward: " 		+ this.sma 				+ "<br>" + 
				"meanReward: " 		+ this.meanReward 		+ " | " +" meanLength: "		+ this.meanLength		+ "<br>" +
				"meanDominance: " 	+ this.meanDominance 	+ " | " + "meanLossEntropy: " 	+ this.meanLossEntropy 	+ "<br>" +
				"meanLossActor: " 	+ this.meanLossActor 	+ " | " + "meanLossCritic: " 	+ this.meanLossCritic	+
				
				"</html>";
				
	}
}
