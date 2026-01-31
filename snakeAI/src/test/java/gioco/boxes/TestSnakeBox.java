package gioco.boxes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import progettoAI.snakeAI.SnakeLogic.boxes.Box;
import progettoAI.snakeAI.SnakeLogic.boxes.SnakeBody;
import progettoAI.snakeAI.SnakeLogic.boxes.SnakeBox;
import progettoAI.snakeAI.SnakeLogic.game.Map;
import progettoAI.snakeAI.SnakeLogic.game.Snake;


public class TestSnakeBox {

	
	@Test
	public void testConstructor() {
		
		
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5);
		Box sn2 = new SnakeBox(SnakeBody.HEAD, 5, 6);
		
		sn.setElementType(SnakeBody.BODY);
		assertEquals(SnakeBody.BODY, sn.getElementType());
		
		sn.setBodyType(SnakeBody.TAIL);
		assertEquals(SnakeBody.TAIL, sn.getElementType());
	
	
	}
	
	@Test
	public void testRightCoordinates() {
	
		Box sn = new SnakeBox(SnakeBody.HEAD, 5, 7);
		assertEquals(5, sn.getXcoordinate());
		assertEquals(7, sn.getYcoordinate());
	
	}
	
	@Test
	public void testEquals(){
			
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5);
		assertEquals(true, sn.equals(SnakeBody.HEAD));
		
			
	}
	
}
