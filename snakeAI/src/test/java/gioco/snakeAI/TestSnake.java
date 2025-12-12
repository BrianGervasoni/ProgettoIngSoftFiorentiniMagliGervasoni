package gioco.snakeAI;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import boxes.*;


class TestSnake {

	@Test
	void testLength() {

		Snake ss = new Snake(new Map());
		
		ss.addLength();
		ss.addLength();
		ss.addLength();
		
		assertEquals(3, ss.getLength());
		
		ss.setLength(999);
		assertEquals(999, ss.getLength());
	}
	
	@Test
	void testReset() {
		
		Snake ss = new Snake(new Map());
		ss.addLength();
		ss.addLength();
		ss.addLength();
		ss.addPiece(new SnakeBox(SnakeBody.BODY, 7 ,3, null));
		ss.addPiece(new SnakeBox(SnakeBody.HEAD, 1 ,2, null));
		
		ss.reset();
		
		assertEquals(0, ss.getLength());
		assertEquals(0, ss.getBody().size());
		
	}
	

}
