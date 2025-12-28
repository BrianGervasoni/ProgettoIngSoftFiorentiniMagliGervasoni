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
	


}
