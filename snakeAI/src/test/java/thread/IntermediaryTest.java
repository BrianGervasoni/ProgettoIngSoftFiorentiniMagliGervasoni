package thread;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import progettoAI.snakeAI.boxes.*;
import progettoAI.snakeAI.gioco.Map;
import progettoAI.snakeAI.gioco.Snake;
import progettoAI.snakeAI.thread.Intermediary;

class IntermediaryTest {

	int n = 5, x = 4, y = 5, rephase = 3;
	int[] raysTest = new int[n];
	Intermediary intermediary = new Intermediary();
	Box apple = new AppleBox(Food.APPLE,x,y);
	SnakeBox head= new SnakeBox(SnakeBody.HEAD,0,0);
	String dir;
	
	/*
	//VERIFICATO in caso alpha appartiene a [0; 360]
	@Test
	void rays1() {
		
		int[] rays = new int[n];
		int degree = 180;
		
		rays = intermediary.rays(degree, rephase, n);
		
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
			
		rays = intermediary.rays(degree, rephase, n);
			
		raysTest[0] = 180;
		raysTest[1] = 183;
		raysTest[2] = 186;
		raysTest[3] = 189;
		raysTest[4] = 192;
			
		assertArrayEquals(raysTest, rays);
	}
	
	//VERIFICATO CHE NEL ES. HEAD (0,0) VEDE APPLE (4,5) SE DIR è UP O RIGHT , SE è LEFT O DOWN NON LO VEDE
	@Test
	void calculateAngle() {
		dir = "right";
		double angle = intermediary.calculateAngle(apple, head, dir);
		double angleTest = Math.toDegrees(Math.atan2(4, 5));
		
		assertEquals(angleTest, angle);
	}
	
	
	@Test
	void calculateRay() {
		
		dir = "right";
		int rayTest = 39;
		int ray = intermediary.calculateRay(apple, head, dir, rephase);
		
		assertEquals(rayTest, ray);
	}
	
	@Test
	void foundRayPosition() {
		
		int degree = 0;
		int[] rays = intermediary.rays(degree, rephase, n);
		int ray = 6;
		
		int index = intermediary.foundRayPosition(rays, ray);
		int indexTest = 2;
		
		assertEquals(indexTest, index);
	}

	@Test 
	void distanceAssignedToRay(){
		
		int degree = 0;
		Box apple1 = new AppleBox(Food.APPLE,2,0);
		double distance = intermediary.calculateDistance(apple1, head); 
		double[] food = intermediary.inizializeArray(n), walls = intermediary.inizializeArray(n), snake = intermediary.inizializeArray(n);
		int[] rays = intermediary.rays(degree, rephase, n); 
		int ray = 0;
		
		intermediary.distanceAssignedToRay(apple1, distance, food, walls, snake, rays, ray);
	
		double[] foodTest = intermediary.inizializeArray(n), wallsTest = intermediary.inizializeArray(n), snakeTest = intermediary.inizializeArray(n);
		
		foodTest[0] = 2;
		foodTest[1] = -1;
		foodTest[2] = -1;
		foodTest[3] = -1;
		foodTest[4] = -1;
		
		assertArrayEquals(foodTest, food);
		assertArrayEquals(wallsTest, walls);
		assertArrayEquals(snakeTest, snake);
		
		//TESTO ANCHE IL MERGEARRAY :
		double[] mergeArray = intermediary.mergeArrays(food, walls, snake);
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
	
	@Test 
	void normalizeArray(){
		
Map map = new Map();
		
		Snake snake = new Snake();
		
		double[] arrayTest = new double[4];
		arrayTest[0] = 5;
		arrayTest[1] = 10;
		arrayTest[2] = 0;
		arrayTest[3] =  Math.sqrt(map.X*map.X + map.Y*map.Y);
		
		double[] array = new double[4];
		array[0] = 0.2946278254943948;
		array[1] = 0.5892556509887896;
		array[2] = 0.0;
		array[3] = 1.0;
		
		arrayTest = intermediary.normalizeArray(arrayTest,map);
		
		assertArrayEquals(array, arrayTest);
	}
	*/
	
	@Test
	void mapConversion() {
		
		Map map = new Map();
		Snake snake = new Snake();
		
		/*SnakeBox head = new SnakeBox(SnakeBody.HEAD, 4, 4);
		map.setBox(head, 4, 4);
		snake.setHead(head);
		System.out.println("head : " + snake.getBodyPiece(0).getXcoordinate() + "; " + snake.getBodyPiece(0).getYcoordinate());
		
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.BODY, 4, 3);
		map.setBox(firstBodyPiece,4, 3);
		snake.addPiece(firstBodyPiece);
		System.out.println("primo corpo : " + snake.getBodyPiece(1).getXcoordinate() + "; " +snake.getBodyPiece(1).getYcoordinate());*/
		
		snake.getBodyPiece(1).setXcoordinate(4);
		snake.getBodyPiece(1).setYcoordinate(3);
		
		//SnakeBox head = new SnakeBox(SnakeBody.HEAD, 4, 4);
		//map.setBox(head, 4, 4);
		//snake.setHead(head);
		snake.getBodyPiece(0).setXcoordinate(4);
		snake.getBodyPiece(0).setYcoordinate(4);
		map.setSnake(snake);
		map.resetSnakeBoxes();
		map.insertSnakeBoxes();
		
		
		
		/*AppleBox apple = new AppleBox(Food.APPLE, 2, 5);
		map.setBox(apple, 2, 5);	*/
		
		map.allPlaceApples().forEach(e->{
			map.setBox(new EmptyBox(MapElem.EMPTY, e.getXcoordinate(), e.getYcoordinate()), e.getXcoordinate(), e.getYcoordinate());
		});
		
		map.forceSetApple(2, 5);
		
		int inputLenght = 10*3; //ho fatto vari test ma sembra NON funzionare con 9*3 mi da i raggi = NaN, mentre 10*3 e 61*3 non da problemi
		
		double[] result;
		
		Intermediary intermediary = new Intermediary();
		result = intermediary.mapConversion(map,inputLenght);
		for(int i = 0; i<result.length; i++) {
			int k = i+1;
			System.out.println(" " + result[i] + " " + k);
		}
		
		System.out.println();
		
		for(int i = 0; i < map.X ; i++) {
			for(int k = 0; k < map.Y ; k++) {
				System.out.println(map.getBox(i, k).getElementType().toString() + " " + i + " " + k);
			}
			System.out.println();
		}
		
	}
}
