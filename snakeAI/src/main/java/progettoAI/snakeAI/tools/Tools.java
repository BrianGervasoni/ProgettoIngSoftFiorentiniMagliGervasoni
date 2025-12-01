package progettoAI.snakeAI.tools;

import java.util.ArrayList;

import java.util.Random;

public class Tools {
	
	public static double pickRandom(int min,int max) {
		Random rand = new Random();
		return min * rand.nextDouble() * (max - min);
	}
}
