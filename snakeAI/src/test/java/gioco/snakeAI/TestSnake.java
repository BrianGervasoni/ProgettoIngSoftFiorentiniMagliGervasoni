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
	
	
	@Test
	void testMove() {
		
		Map mm = new Map();
		
		for(int i = 0; i < Map.X ; i++) {
			for(int k = 0; k < Map.Y ; k++) {
				if(i == 0 || i == Map.X-1 || k == 0 || k == Map.Y-1) {
					this.box[i][k] = new EmptyBox(MapElem.WALL, i, k);
					
				}
				else {
					this.box[i][k] = new EmptyBox(MapElem.EMPTY, i , k);
				}
			}
		}
		
		
		
	}
	

}
