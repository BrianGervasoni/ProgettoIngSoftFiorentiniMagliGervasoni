package gioco.boxes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import boxes.Box;
import boxes.SnakeBody;
import boxes.SnakeBox;
import gioco.snakeAI.Map;
import gioco.snakeAI.Snake;


public class TestSnakeBox {

	
	@Test
	public void testConstructor() {
		
		
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		Box sn2 = new SnakeBox(SnakeBody.HEAD, 5, 6, (SnakeBox) sn);
		
		sn.setElementType(SnakeBody.BODY);
		assertEquals(SnakeBody.BODY, sn.getElementType());
		
		sn.setBodyType(SnakeBody.TAIL);
		assertEquals(SnakeBody.TAIL, sn.getElementType());
	
	
	}
	
	@Test
	public void testRightCoordinates() {
	
		Box sn = new SnakeBox(SnakeBody.HEAD, 5, 7, null);
		assertEquals(5, sn.getXcoordinate());
		assertEquals(7, sn.getYcoordinate());
	
	}
	
	@Test
	public void testNext() {
		
		Map mm = new Map();
		Snake sssss = new Snake(mm);
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		SnakeBox sn2 = new SnakeBox(SnakeBody.BODY, 5, 6, null);
	
		sn2.setNext(sn);
		
		sssss.addPiece(sn);
		sssss.addPiece(sn2);
		
		System.out.println(sn);
		System.out.println(sn2.getNext());
		
		assertEquals(sn.getElementType(), sn2.getNext().getElementType());

	}
	
	@Test
	public void testEquals(){
			
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		assertEquals(true, sn.equals(SnakeBody.HEAD));
		
			
	}
	
}
