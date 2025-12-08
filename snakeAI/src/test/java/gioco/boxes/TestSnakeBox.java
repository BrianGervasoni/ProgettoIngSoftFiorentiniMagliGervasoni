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
		
		Map mm = null;
		Snake sssss = new Snake(mm);
		Box sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		SnakeBox sn2 = new SnakeBox(SnakeBody.HEAD, 5, 6, null);
	
		sn2.setNext((SnakeBox) sn);
		
		sssss.addPiece((SnakeBox)sn);
		sssss.addPiece((SnakeBox)sn2);
		
		assertEquals(sn2, sssss.getBodyPiece(1));
	
	
	
	}
	
	
	@Test
	public void testEquals(){
			
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		assertEquals(SnakeBody.HEAD, sn.equals());
		
			
	}
	
}
