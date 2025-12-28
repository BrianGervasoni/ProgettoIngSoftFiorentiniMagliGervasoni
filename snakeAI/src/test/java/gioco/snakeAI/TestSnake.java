package gioco.snakeAI;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import boxes.*;


class TestSnake {

	@Test
	void testSetHead() {
		Map map = new Map();
		Snake ss = new Snake();
		
		map.setSnake(ss);
		map.initSnakeBody();
		
		assertEquals(1,1);
		
	}
	
	
	@Test
	void testReset() {
		
		Snake ss = new Snake();
		
		ss.addPiece(new SnakeBox(SnakeBody.BODY, 7 ,3));
		ss.addPiece(new SnakeBox(SnakeBody.HEAD, 1 ,2));
		
		ss.reset();
		
		assertEquals(0, ss.getLenght());
		assertEquals(0, ss.getBody().size());
		
	}
	

	
	@Test
	void testSnakeMove() {
		
		Snake ss = new Snake();
		SnakeBox head = new SnakeBox(SnakeBody.HEAD, 5, 5);
		ss.setHead(head);
		ss.addPiece(new SnakeBox(SnakeBody.TAIL, 4, 5));
		
		
		Snake ss2 = new Snake();
		SnakeBox head2 = new SnakeBox(SnakeBody.HEAD, 2, 5);
		ss2.setHead(head2);
		ss2.addPiece(new SnakeBox(SnakeBody.TAIL, 1, 5));

		
		ss2.move(Direction.STRAIGHT);
		ss2.removeTail();
		
		ss2.move(Direction.STRAIGHT);
		ss2.removeTail();
		
		ss2.move(Direction.STRAIGHT);
		ss2.removeTail();
		
		
		List<SnakeBox> body1 = ss.getBody();
		List<SnakeBox> body2 = ss2.getBody();
		
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
		
		
		
		
		
		
		
		
		
		
		/*
		SnakeBox heead = new SnakeBox(SnakeBody.HEAD, 5, 5);
		mm.setBox(heead, 5, 5);
		mm.getSnake().setHead(heead);
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.BODY, 5, 4);
		mm.setBox(firstBodyPiece, 5, 4);
		mm.getSnake().addPiece(firstBodyPiece);
		AppleBox apple1 = new AppleBox(Food.APPLE, 10, 10);
		mm.testOnlySetApple(apple1);
		
		
		//quello da cui si parte
		Map mm2 = new Map();
		SnakeBox heead2 = new SnakeBox(SnakeBody.HEAD, 5, 2);
		mm2.setBox(heead2, 5, 2);
		mm2.getSnake().setHead(heead2);
		SnakeBox firstBodyPiece2 = new SnakeBox(SnakeBody.BODY, 5, 1);
		mm2.setBox(firstBodyPiece2, 5, 1);
		mm2.getSnake().addPiece(firstBodyPiece2);
		AppleBox apple2 = new AppleBox(Food.APPLE, 10, 10);
		mm2.testOnlySetApple(apple1);
		
		
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		mm2.makeSnakeMove(Direction.STRAIGHT);
		
		assertEquals(true, mm2.equals(mm));
		*/
		
		
		
	}
		
	
	@Test
	void testRemoveTail() {
		
		Snake ss = new Snake();
		SnakeBox head = new SnakeBox(SnakeBody.HEAD, 5, 5);
		ss.setHead(head);
		ss.addPiece(new SnakeBox(SnakeBody.BODY, 4, 5));
		ss.addPiece(new SnakeBox(SnakeBody.TAIL, 3, 5));

		ss.removeTail();

		Boolean test = true;
	
		if((ss.getBodyPiece(1).equals(SnakeBody.TAIL)) && (ss.getBody().size() == 2))
			test = true;
		else
			test = false;

		assertEquals(true, test);
	}
	
	
	
	
	@Test
	void testAddPiece() {
		
		//Map map = new Map();
		SnakeBox sb = new SnakeBox(SnakeBody.HEAD, 7,7);
		Snake sn = new Snake();
		
		sn.addPiece(sb);
		
		assertEquals(1, sn.getBody().size());
	}


}
