package gioco.snakeAI;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import boxes.*;


class TestSnake {

	@Test
	void testLength() {

		Snake ss = new Snake(new Map());
		
		ss.addLenght();
		ss.addLenght();
		ss.addLenght();
		
		assertEquals(3, ss.getLenght());
		
		ss.setLenght(999);
		assertEquals(999, ss.getLenght());
	}
	
	@Test
	void testReset() {
		
		Snake ss = new Snake(new Map());
		ss.addLenght();
		ss.addLenght();
		ss.addLenght();
		ss.addPiece(new SnakeBox(SnakeBody.BODY, 7 ,3, null));
		ss.addPiece(new SnakeBox(SnakeBody.HEAD, 1 ,2, null));
		
		ss.reset();
		
		assertEquals(0, ss.getLenght());
		assertEquals(0, ss.getBody().size());
		
	}
	

	
	@Test
	void testMove() {
		
		Map mm = new Map();
		
		for(int i = 0; i < Map.X ; i++) {
			for(int k = 0; k < Map.Y ; k++) {
				if(i == 0 || i == Map.X-1 || k == 0 || k == Map.Y-1) {
					mm.setBox(new EmptyBox(MapElem.WALL, i, k), i, k);
					
				}
				else {
					mm.setBox(new EmptyBox(MapElem.EMPTY, i, k), i, k);
				}
				
			}
		}
		
		
		
	}


}
