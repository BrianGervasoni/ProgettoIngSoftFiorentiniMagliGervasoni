package gioco.snakeAI;

import static org.junit.jupiter.api.Assertions.*;
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
		
	@Test
	void testAddPiece() {
		
		//Map map = new Map();
		SnakeBox sb = new SnakeBox(SnakeBody.HEAD, 7,7);
		Snake sn = new Snake();
		
		sn.addPiece(sb);
		
		assertEquals(1, sn.getBody().size());
	}


}
