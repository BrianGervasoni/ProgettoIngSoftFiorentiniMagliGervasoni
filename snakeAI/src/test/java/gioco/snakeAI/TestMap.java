package gioco.snakeAI;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import boxes.AppleBox;
import boxes.Direction;
import boxes.EmptyBox;
import boxes.MapElem;
import boxes.SnakeBody;
import boxes.SnakeBox;

class TestMap {

	
	@Test
	void testMakeSnakeMoveOnlyMovement() {
		
		Map mm = new Map();
		mm.getSnake().getBodyPiece(0).setXcoordinate(5);
		mm.getSnake().getBodyPiece(0).setYcoordinate(5);
		
		mm.getSnake().getBodyPiece(1).setXcoordinate(4);
		mm.getSnake().getBodyPiece(1).setYcoordinate(5);

		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();
		
		Boolean valid = mm.forceSetApple(10, 10);
		
/*		System.out.println("x" + mm.getSnake().getBodyPiece(0).getXcoordinate());
		System.out.println("y" + mm.getSnake().getBodyPiece(0).getYcoordinate());
		System.out.println("x" + mm.getSnake().getBodyPiece(1).getXcoordinate());
		System.out.println("y" + mm.getSnake().getBodyPiece(1).getYcoordinate());*/
		
		
		
		
		assertEquals(valid, true);
		
		//quello da cui si parte
		Map mm2 = new Map();
		
		mm2.getSnake().getBodyPiece(0).setXcoordinate(2);
		mm2.getSnake().getBodyPiece(0).setYcoordinate(5);
		mm2.getSnake().getBodyPiece(1).setXcoordinate(1);
		mm2.getSnake().getBodyPiece(1).setYcoordinate(5);
		
		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();

		
/*		System.out.println("x" + mm2.getSnake().getBodyPiece(0).getXcoordinate());
		System.out.println("y" + mm2.getSnake().getBodyPiece(0).getYcoordinate());
		System.out.println("x" + mm2.getSnake().getBodyPiece(1).getXcoordinate());
		System.out.println("y" + mm2.getSnake().getBodyPiece(1).getYcoordinate());*/
		
		Boolean valid2 = mm2.forceSetApple(10, 10);
		
		assertEquals(valid2, true);
		
		
		
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		
		List<SnakeBox> body1 = mm.getSnake().getBody();
		List<SnakeBox> body2 = mm2.getSnake().getBody();
		
		
		
		
		int check = 0;
		
		for(int i = 0; i < body1.size(); i++) {
			
			if((body1.get(i).getXcoordinate()==body2.get(i).getXcoordinate())&&(body1.get(i).getYcoordinate()==body2.get(i).getYcoordinate())&&(body1.get(i).getElementType()==body2.get(i).getElementType()))
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
		
		mm.getSnake().getBodyPiece(0).setXcoordinate(5);
		mm.getSnake().getBodyPiece(0).setYcoordinate(5);

		
		mm.getSnake().getBodyPiece(1).setXcoordinate(4);
		mm.getSnake().getBodyPiece(1).setYcoordinate(5);
		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();

		Boolean valid = mm.forceSetApple(6, 5);
		
		assertEquals(valid, true);
		
		mm.getSnake().move(Direction.STRAIGHT);
		assertEquals(true, mm.checkAppleCollision());
		
	}
	
	
	@Test
	void testMakeSnakeMoveWithEaten() {
		
		Map mm = new Map();
		mm.getSnake().getBodyPiece(0).setXcoordinate(9);
		mm.getSnake().getBodyPiece(0).setYcoordinate(5);
		
		mm.getSnake().getBodyPiece(1).setXcoordinate(8);
		mm.getSnake().getBodyPiece(1).setYcoordinate(5);
		mm.getSnake().getBodyPiece(1).setElementType(SnakeBody.BODY);

		SnakeBox secondBodyPiece = new SnakeBox(SnakeBody.TAIL, 7, 5);
		mm.getSnake().addPiece(secondBodyPiece);
		
		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();
		
		for(int i = 0; i < mm.getSnake().getBody().size(); i++)
			System.out.println(mm.getSnake().getBodyPiece(i).getElementType());
		
		
		
		Boolean valid = mm.forceSetApple(10, 10);
		assertEquals(valid, true);
		
		
		//quello da cui si parte
		Map mm2 = new Map();

		mm2.getSnake().getBodyPiece(0).setXcoordinate(2);
		mm2.getSnake().getBodyPiece(0).setYcoordinate(5);
		
		mm2.getSnake().getBodyPiece(1).setXcoordinate(1);
		mm2.getSnake().getBodyPiece(1).setYcoordinate(5);
		
		mm2.resetSnakeBoxes();
		mm2.insertSnakeBoxes();
		

		assertEquals(true, mm2.forceSetApple(6, 5));

		
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
		mm.getSnake().getBodyPiece(0).setXcoordinate(2);
		mm.getSnake().getBodyPiece(0).setYcoordinate(1);
		
		mm.getSnake().getBodyPiece(1).setXcoordinate(1);
		mm.getSnake().getBodyPiece(1).setYcoordinate(1);
		
		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();
		
		assertEquals(true, mm.forceSetApple(10, 10));
		
		
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
	void testCheckDefeatWallCollision() {
		
		
		Map mm = new Map();
		mm.getSnake().getBodyPiece(0).setXcoordinate(3);
		mm.getSnake().getBodyPiece(0).setYcoordinate(1);
		
		mm.getSnake().getBodyPiece(1).setXcoordinate(3);
		mm.getSnake().getBodyPiece(1).setYcoordinate(2);
		
		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();
		
		assertEquals(true, mm.forceSetApple(10, 10));
		
		mm.makeSnakeMove(Direction.STRAIGHT);
		
		assertEquals(true, mm.checkDefeat());

		
	}
	
	
	@Test
	void testCheckDefeatAutoCollision() {
		
		
		Map mm = new Map();
		
		mm.getSnake().getBodyPiece(0).setXcoordinate(5);
		mm.getSnake().getBodyPiece(0).setYcoordinate(1);
		
		mm.getSnake().getBodyPiece(1).setXcoordinate(4);
		mm.getSnake().getBodyPiece(1).setYcoordinate(1);
		
		
		SnakeBox bodyPiece2 = new SnakeBox(SnakeBody.BODY, 3, 1);
		mm.getSnake().addPiece(bodyPiece2);
		
		SnakeBox bodyPiece3 = new SnakeBox(SnakeBody.BODY, 2, 1);
		mm.getSnake().addPiece(bodyPiece3);
		
		SnakeBox bodyPiece4 = new SnakeBox(SnakeBody.TAIL, 1, 1);
		mm.getSnake().addPiece(bodyPiece4);
		

		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();
		
		assertEquals(true, mm.forceSetApple(10, 10));
		
		mm.makeSnakeMove(Direction.LEFT);
		
		assertEquals(false, mm.checkDefeat());
		
		mm.makeSnakeMove(Direction.LEFT);
		
		assertEquals(false, mm.checkDefeat());
		
		mm.makeSnakeMove(Direction.LEFT);
		
		assertEquals(true, mm.checkDefeat());

	}
	
	
	@Test
	void testCheckValidCoordinates() {
		
		Map mm = new Map();
		SnakeBox heead = new SnakeBox(SnakeBody.HEAD, 3, 1);
		mm.getSnake().getBodyPiece(0).setXcoordinate(3);
		mm.getSnake().getBodyPiece(0).setYcoordinate(1);
		
		
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.TAIL, 3, 2);
		mm.getSnake().getBodyPiece(1).setXcoordinate(3);
		mm.getSnake().getBodyPiece(1).setYcoordinate(2);
		

		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();
		
		assertEquals(true, mm.forceSetApple(10, 10));
		assertEquals(false, mm.forceSetApple(3,  1));
		
		
		
	}
	
	
	
	
	@Test
	void testCheckVictoryWithMapFilled() {
		
		Map mm = new Map();
		
		for(int i = 1; i < Map.X-1; i++) {
			for(int j = 1; j < Map.Y-1; j++) {
				
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
	
	
	@Test
	void testCheckVictoryAfterEatingApple() {
		Map mm = new Map();
		
		mm.getSnake().getBody().remove(0);
		mm.getSnake().getBody().remove(0);
		
		for(int i = 1; i < Map.X-1; i++) {
			for(int j = 1; j < Map.Y-1; j++) {
				
				SnakeBox bb = new SnakeBox(SnakeBody.BODY, i, j);
				mm.getSnake().addPiece(bb);
			}
		}
		
		//Qui viene rimosso il pezzo di corpo in 1 1
		mm.getSnake().getBody().remove(0);
		
		mm.resetSnakeBoxes();
		mm.insertSnakeBoxes();
		
		assertEquals(true, mm.forceSetApple(1, 1));
	
		
		mm.getSnake().getBodyPiece(0).setBodyType(SnakeBody.HEAD);
		mm.getSnake().getBodyPiece(mm.getSnakeLength()-1).setBodyType(SnakeBody.TAIL);

		mm.makeSnakeMove(Direction.STRAIGHT);
		
		assertEquals(true, mm.checkVictory());
		
		
		
	}
	
	
	

}
