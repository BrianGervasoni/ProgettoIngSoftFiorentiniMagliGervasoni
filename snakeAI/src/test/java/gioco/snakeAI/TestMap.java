package gioco.snakeAI;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import boxes.AppleBox;
import boxes.Direction;
import boxes.Food;
import boxes.SnakeBody;
import boxes.SnakeBox;

class TestMap {

	
	@Test
	void testMakeSnakeMoveOnlyMovement() {
		
		Map mm = new Map();
		SnakeBox heead = new SnakeBox(SnakeBody.HEAD, 5, 5);
		mm.setBox(heead, 5, 5);
		mm.getSnake().setHead(heead);
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.TAIL, 4, 5);
		mm.setBox(firstBodyPiece, 4, 5);
		mm.getSnake().addPiece(firstBodyPiece);
		AppleBox apple1 = new AppleBox(Food.APPLE, 10, 10);
		mm.testOnlySetApple(apple1);
		
		
		//quello da cui si parte
		Map mm2 = new Map();
		SnakeBox heead2 = new SnakeBox(SnakeBody.HEAD, 2, 5);
		mm2.setBox(heead2, 2, 5);
		mm2.getSnake().setHead(heead2);
		SnakeBox firstBodyPiece2 = new SnakeBox(SnakeBody.TAIL, 1, 5);
		mm2.setBox(firstBodyPiece2, 1, 5);
		mm2.getSnake().addPiece(firstBodyPiece2);
		AppleBox apple2 = new AppleBox(Food.APPLE, 10, 10);
		mm2.testOnlySetApple(apple2);
		
		
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		
		List<SnakeBox> body1 = mm.getSnake().getBody();
		List<SnakeBox> body2 = mm2.getSnake().getBody();
		
		
		int check = 0;
		
		for(int i = 0; i < body1.size(); i++) {
			
			if((body1.get(i).getXcoordinate()==body2.get(i).getXcoordinate())&&(body1.get(i).getYcoordinate()==body2.get(i).getYcoordinate())&&(body1.get(i).getElementType().equals(body2.get(i).getElementType())))
				check = 0;
			else {
				check = 1;
				break;
			}		
		}

		assertEquals(0, check);
		
	}
	
	
	@Test
	void testCheckAppleCollision() {
		
		Map mm = new Map();
		SnakeBox heead = new SnakeBox(SnakeBody.HEAD, 5, 5);
		mm.setBox(heead, 5, 5);
		mm.getSnake().setHead(heead);
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.TAIL, 4, 5);
		mm.setBox(firstBodyPiece, 4, 5);
		mm.getSnake().addPiece(firstBodyPiece);
		AppleBox apple1 = new AppleBox(Food.APPLE, 6, 5);
		mm.testOnlySetApple(apple1);
		
		mm.getSnake().move(Direction.STRAIGHT);
		assertEquals(true, mm.checkAppleCollision());
		
	}
	
	
	@Test
	void testMakeSnakeMoveWithEaten() {
		
		Map mm = new Map();
		SnakeBox heead = new SnakeBox(SnakeBody.HEAD, 9, 5);
		mm.setBox(heead, 9, 5);
		mm.getSnake().setHead(heead);
		
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.BODY, 8, 5);
		mm.setBox(firstBodyPiece, 8, 5);
		mm.getSnake().addPiece(firstBodyPiece);
		
		SnakeBox secondBodyPiece = new SnakeBox(SnakeBody.TAIL, 7, 5);
		mm.setBox(secondBodyPiece, 7, 5);
		mm.getSnake().addPiece(secondBodyPiece);
		
		AppleBox apple1 = new AppleBox(Food.APPLE, 10, 10);
		mm.testOnlySetApple(apple1);
		
		
		//quello da cui si parte
		Map mm2 = new Map();
		SnakeBox heead2 = new SnakeBox(SnakeBody.HEAD, 2, 5);
		mm2.setBox(heead2, 2, 5);
		mm2.getSnake().setHead(heead2);
		SnakeBox firstBodyPiece2 = new SnakeBox(SnakeBody.TAIL, 1, 5);
		mm2.setBox(firstBodyPiece2, 1, 5);
		mm2.getSnake().addPiece(firstBodyPiece2);
		AppleBox apple2 = new AppleBox(Food.APPLE, 6, 5);
		mm2.testOnlySetApple(apple2);
		
		
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		
		List<SnakeBox> body1 = mm.getSnake().getBody();
		List<SnakeBox> body2 = mm2.getSnake().getBody();
		
		
		int check = 0;
		
		for(int i = 0; i < body1.size(); i++) {
			
			if((body1.get(i).getXcoordinate()==body2.get(i).getXcoordinate())&&(body1.get(i).getYcoordinate()==body2.get(i).getYcoordinate())&&(body1.get(i).getElementType().equals(body2.get(i).getElementType())))
				check = 0;
			else {
				check = 1;
				break;
			}		
		}

		assertEquals(0, check);
		
	}
	

	
	
	
	
	@Test
	void testSnakeComplexMovement() {
		
		Map mm = new Map();
		SnakeBox heead = new SnakeBox(SnakeBody.HEAD, 2, 1);
		mm.setBox(heead, 2, 1);
		mm.getSnake().setHead(heead);
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.TAIL, 1, 1);
		mm.setBox(firstBodyPiece, 1, 1);
		mm.getSnake().addPiece(firstBodyPiece);
		AppleBox apple1 = new AppleBox(Food.APPLE, 10, 10);
		mm.testOnlySetApple(apple1);
		
		
		mm.makeSnakeMove(Direction.STRAIGHT);
		
		mm.makeSnakeMove(Direction.LEFT);
		mm.makeSnakeMove(Direction.STRAIGHT);
		mm.makeSnakeMove(Direction.LEFT);
		mm.makeSnakeMove(Direction.STRAIGHT);
		
		mm.makeSnakeMove(Direction.RIGHT);
		mm.makeSnakeMove(Direction.STRAIGHT);
		mm.makeSnakeMove(Direction.RIGHT);
		mm.makeSnakeMove(Direction.STRAIGHT);
		
		mm.makeSnakeMove(Direction.LEFT);
		mm.makeSnakeMove(Direction.STRAIGHT);
		mm.makeSnakeMove(Direction.LEFT);
		mm.makeSnakeMove(Direction.STRAIGHT);
		
		mm.makeSnakeMove(Direction.RIGHT);
		mm.makeSnakeMove(Direction.STRAIGHT);
		mm.makeSnakeMove(Direction.RIGHT);
		mm.makeSnakeMove(Direction.STRAIGHT);
		
		
		Boolean check = true;
		
		if((mm.getSnake().getBodyPiece(0).getXcoordinate()==3)&&(mm.getSnake().getBodyPiece(0).getYcoordinate()==9)){
			check = true;
		}else
			check = false;
		
		assertEquals(true, check);
		
		
		
		if((mm.getSnake().getBodyPiece(1).getXcoordinate()==2)&&(mm.getSnake().getBodyPiece(1).getYcoordinate()==9)){
			check = true;
		}else
			check = false;
		
		assertEquals(true, check);
		
		
		
	}
	
	
	
	@Test
	void testCheckDefeat() {
		
		
		Map mm = new Map();
		SnakeBox heead = new SnakeBox(SnakeBody.HEAD, 3, 1);
		mm.setBox(heead, 3, 1);
		mm.getSnake().setHead(heead);
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.TAIL, 3, 2);
		mm.setBox(firstBodyPiece, 3, 2);
		mm.getSnake().addPiece(firstBodyPiece);
		AppleBox apple1 = new AppleBox(Food.APPLE, 10, 10);
		mm.testOnlySetApple(apple1);
		
		mm.makeSnakeMove(Direction.STRAIGHT);
		
		assertEquals(true, mm.checkDefeat());

		
	}
	
	
	@Test
	void testCheckDefeat2() {
		
		
		Map mm = new Map();
		SnakeBox heead = new SnakeBox(SnakeBody.HEAD, 5, 1);
		mm.setBox(heead, 5, 1);
		mm.getSnake().setHead(heead);
		
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.BODY, 4, 1);
		mm.setBox(firstBodyPiece, 4, 1);
		mm.getSnake().addPiece(firstBodyPiece);
		
		SnakeBox bodyPiece2 = new SnakeBox(SnakeBody.BODY, 3, 1);
		mm.setBox(bodyPiece2, 3, 1);
		mm.getSnake().addPiece(bodyPiece2);
		
		SnakeBox bodyPiece3 = new SnakeBox(SnakeBody.BODY, 2, 1);
		mm.setBox(bodyPiece3, 2, 1);
		mm.getSnake().addPiece(bodyPiece3);
		
		SnakeBox bodyPiece4 = new SnakeBox(SnakeBody.TAIL, 1, 1);
		mm.setBox(bodyPiece4, 1, 1);
		mm.getSnake().addPiece(bodyPiece4);
		
		AppleBox apple1 = new AppleBox(Food.APPLE, 10, 10);
		mm.testOnlySetApple(apple1);
		
		mm.makeSnakeMove(Direction.LEFT);
		mm.makeSnakeMove(Direction.LEFT);
		mm.makeSnakeMove(Direction.LEFT);
		
		
		assertEquals(true, mm.checkDefeat());

		
	}
	
	
	
	
	@Test
	void testCheckVictory() {
		
		Map mm = new Map();
		
		for(int i = 0; i < Map.X; i++) {
			for(int j = 0; j < Map.Y; j++) {
				
				SnakeBox bb = new SnakeBox(SnakeBody.BODY, i, j);
				mm.setBox(bb , i, j);
				mm.getSnake().addPiece(bb);
			}
		}
				
		assertEquals(true, mm.checkVictory());
		
		mm.getSnake().reset();
		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();
		
		assertEquals(false, mm.checkVictory());
		
		
	}
	
	
	
	
	

}
