package gioco.gamemain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import boxes.*;

public class TestSnakeBox {

	
	@Test
	public void testSetGetEnum() {
		
		
		Box sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		Box sn2 = new SnakeBox(SnakeBody.HEAD, 5, 6, (SnakeBox) sn);
		
		sn.setElementType(SnakeBody.BODY);
		assertEquals(SnakeBody.BODY, sn.getElementType());
	}
	
	@Test
	public void testRightCoordinates() {
	
		Box sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		
	
	
	}
}
