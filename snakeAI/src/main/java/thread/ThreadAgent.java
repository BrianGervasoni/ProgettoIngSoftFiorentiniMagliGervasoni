package thread;

import enumSnake.Snake;
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
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public double[] mapConversion(Map map) {
		
		for(int i=0; i<map.getMap().length; i++) { //i get the length of the rows
			for(int j=0; j<map.getMap()[0].length; j++) { //i get the length of the columns
				if((map.getMap()[i][j] instanceof SnakeBox) && ((Snake) map.getMap()[i][j].getElementType() == Snake.Head)) {
					/*
					 * una volta ottenuta la testa e le sue coordinate calcola il fascio di rette 
					 * che parte da li e si scontra con gli altri elementi della mappa
					 * formula fascio di rette y - y0 = m(x - x0)
					 * il fascio sarà composto da rette che vanno da -90° a +90° in senso orario
					 * numero di rette è 60, ogni retta piazzata con una fase che differisce di 3°
					 */
				}
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
