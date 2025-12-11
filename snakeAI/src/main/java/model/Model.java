package model;

import java.util.concurrent.CompletableFuture;

import progettoAI.snakeAI.AI.AIActor;
import progettoAI.snakeAI.AI.AICritic;
import progettoAI.snakeAI.hyperparameters.Hyperparameters;

public class Model {
	private AICritic critic;
	private AIActor actor;
	private PPOMemory memory;
	
	public Model() {
		memory = new PPOMemory();
	}
	
	public Model(AICritic critic, AIActor actor) {
		memory = new PPOMemory();
		this.critic = critic;
		this.actor = actor;
	}
	
	public AICritic getCritic() {
		return critic;
	}

	public void setCritic(AICritic critic) {
		this.critic = critic;
	}

	public AIActor getActor() {
		return actor;
	}

	public void setActor(AIActor actor) {
		this.actor = actor;
	}

	public PPOMemory getMemory() {
		return memory;
	}

	public void setMemory(PPOMemory memory) {
		this.memory = memory;
	}

	/**
	 * perform the forwarding for the critic and actor
	 * @param input
	 * @return
	 */
	public ActionRegister forwarding(double[] input) {
		CompletableFuture<double[]> procCritic = forwardingCritic(input);
		CompletableFuture<double[]> procActor = forwardingActor(input);
		
		double[] resCritic = procCritic.join();
		double[] resActor = procActor.join();
		
		ActionRegister r = new ActionRegister();
		r.actionsProb = resActor;
		r.vTarget = resCritic[0];
		r.state = input;
		return r;
	}
	
	private CompletableFuture<double[]> forwardingCritic(double[] input){
		return CompletableFuture.supplyAsync(()->{
			return  critic.forwarding(input);
		});
	}
	
	private CompletableFuture<double[]> forwardingActor(double[] input){
		return CompletableFuture.supplyAsync(()->{
			return  actor.forwarding(input);
		});
	}
	
	/**
	 * initialize the memory for the backPorpagation
	 */
	public void initBackPropagation() {
		memory.prepareData();
	}
	
	/**
	 * perform the backPropagation for the critic and actor
	 */
	public void backPropagation() {
		CompletableFuture<Void> procCritic = backPropCritic();
		CompletableFuture<Void> procActor = backPropActor();
		
		procCritic.join();
		procActor.join();
	}
	
	private  CompletableFuture<Void> backPropCritic(){
		return CompletableFuture.runAsync(()->{
			for(int i=0; i<Hyperparameters.epoche; i++) {
				critic.backPropagation(memory.getMiniBatch());
			}
		});
	}
	
	private  CompletableFuture<Void> backPropActor(){
		return CompletableFuture.runAsync(()->{
			for(int i=0; i<Hyperparameters.epoche; i++) {
				actor.backPropagation(memory.getMiniBatch());
			}
		});
	}
	
	/**
	 * perform the optimization of the parameters for critic and actor
	 */
	public void optimization() {
		critic.optimization();
		actor.optimization();
	}
	
}
