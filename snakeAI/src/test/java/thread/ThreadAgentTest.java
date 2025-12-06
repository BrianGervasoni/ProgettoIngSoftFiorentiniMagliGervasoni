package thread;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import enumSnake.Food;
import enumSnake.Snake;
import model.Model;
import snakeGame.*;

class ThreadAgentTest {

	int n = 5, x = 4, y = 5, rephase = 3;
	int[] raysTest = new int[n];
	Model model = null;
	ThreadAgent threadAgent = new ThreadAgent(model);
	Box apple = new AppleBox(x,y);
	SnakeBox head= new SnakeBox(0,0);
	String dir;
	
	
	//VERIFICATO in caso alpha appartiene a [0; 360]
	@Test
	void rays1() {
		
		int[] rays = new int[n];
		int degree = 180;
		
		rays = threadAgent.rays(degree, rephase, n);
		
		raysTest[0] = 180;
		raysTest[1] = 183;
		raysTest[2] = 186;
		raysTest[3] = 189;
		raysTest[4] = 192;
		
		assertArrayEquals(raysTest, rays);
	}
	
	//VERIFICATO in caso alpha appartiene a [-180; 180]
	@Test
	void rays2() {
			
		int[] rays = new int[n];
		int degree = -180;
			
		rays = threadAgent.rays(degree, rephase, n);
			
		raysTest[0] = 180;
		raysTest[1] = 183;
		raysTest[2] = 186;
		raysTest[3] = 189;
		raysTest[4] = 192;
			
		assertArrayEquals(raysTest, rays);
	}
	
	//VERIFICATO CHE NEL ES. HEAD (0,0) VEDE APPLE (5,4) SE DIR è UP O RIGHT , SE è LEFT O DOWN NON LO VEDE
	@Test
	void calculateAngle() {
		
		head.setElementType(Snake.Head);
		dir = "right";
		double angle = threadAgent.calculateAngle(apple, head, dir);
		double angleTest = Math.toDegrees(Math.atan2(5, 4));
		
		assertEquals(angleTest, angle);
	}
	
	/**
	 * COSI  PER ES. CON VISTA DA [0; 180] HO OGGETTO A (4, 5) E QUINDI ANGOLO 
	 * CON ARCTAN(5/4)) HO UN ANGOLO DI TIPO 51.34019175
	 * DIVIDO PER 3 E OTTENGO 17.11339725 E ARROTONDO PER AVERE 17, RIMOLTIPLICANDO *3 HO IL RAGGIO CHE SAREBBE 51!	
	 * 
	 * ALTRO ES. CON VISTA DA [0; 180] HO OGGETTO A (-5, 3) E QUINDI ANGOLO 
	 * CON ARCTAN(3 /(-5)) +180, HO UN ANGOLO DI TIPO 149.0362435
	 * DIVIDO PER 3 E OTTENGO 49.67874782 E ARROTONDO PER AVERE 50, RIMOLTIPLICANDO *3 HO IL RAGGIO CHE SAREBBE 150!	
	 */
	@Test
	void calculateRay() {
		
		head.setElementType(Snake.Head);
		dir = "right";
		int rayTest = 51;
		int ray = threadAgent.calculateRay(apple, head, dir, rephase);
		
		assertEquals(rayTest, ray);
	}
	
	@Test
	void foundRayPosition() {
		
		int degree = 0;
		int[] rays = threadAgent.rays(degree, rephase, n);
		int ray = 6;
		
		int index = threadAgent.foundRayPosition(rays, ray);
		int indexTest = 2;
		
		assertEquals(indexTest, index);
	}

	@Test 
	void distanceAssignedToRay(){
		
		int degree = 0;
		Box apple1 = new AppleBox(2,0);
		apple1.setElementType(Food.Apple);
		double distance = threadAgent.calculateDistance(apple1, head); 
		double[] food = threadAgent.inizializeArray(n), walls = threadAgent.inizializeArray(n), snake = threadAgent.inizializeArray(n);
		int[] rays = threadAgent.rays(degree, rephase, n); 
		int ray = 0;
		
		threadAgent.distanceAssignedToRay(apple1, distance, food, walls, snake, rays, ray);
	
		double[] foodTest = threadAgent.inizializeArray(n), wallsTest = threadAgent.inizializeArray(n), snakeTest = threadAgent.inizializeArray(n);
		
		foodTest[0] = 2;
		foodTest[1] = -1;
		foodTest[2] = -1;
		foodTest[3] = -1;
		foodTest[4] = -1;
		
		assertArrayEquals(foodTest, food);
		assertArrayEquals(wallsTest, walls);
		assertArrayEquals(snakeTest, snake);
		
		//TESTO ANCHE IL MERGEARRAY :
		double[] mergeArray = threadAgent.mergeArrays(food, walls, snake);
		double[] mATest = new double[n*3];
		mATest[0] = 2;
		mATest[1] = -1;
		mATest[2] = -1;
		mATest[3] = -1;
		mATest[4] = -1;
		mATest[5] = -1;
		mATest[6] = -1;
		mATest[7] = -1;
		mATest[8] = -1;
		mATest[9] = -1;
		mATest[10] = -1;
		mATest[11] = -1;
		mATest[12] = -1;
		mATest[13] = -1;
		mATest[14] = -1;
	
		assertArrayEquals(mATest, mergeArray);
	}
	
}
