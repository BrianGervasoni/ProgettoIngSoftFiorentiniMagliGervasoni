package thread;

import model.*;
import snakeGame.*;

public class ThreadAgent extends Thread{

	Model model;
	ActionRegister[] actionRegister;
	GameMain game;
	
	public ThreadAgent(Model model) {
		
	}
	
	public void run() {
		
	}
	
	public int moveSelection(double[] input) {
		
		return 0;
		
	}
	
	public void finish() {
		
	}
	
	public double[] mapConversion(Map map) {
		
		for(int i=0; i<map.getMap().length; i++) { //i get the length of the rows
			for(int j=0; j<map.getMap()[0].length; j++) { //i get the length of the columns
				//if(map.getMap()[i][j] instanceof Snake && (Snake)map.getMap()[i][j])
			}
		}
		
		return null;
	}
	
	public Direction moveConversion(int index) {
		
		return null;
		
	}
	
	public float calculateReward(Map map) {
		
		return 0;
		
	}
	
	public void move(Direction direction) {
		
	}
	
	public void resetActionRegister() {
		
	}
	
	public void sendActions (ActionRegister actionRegister) {
		
	}
	
}
