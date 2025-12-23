package gioco.boxes;

import boxes.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import gioco.snakeAI.*;

class TestSnake {


	@Test
	void testSetHead() {
		
		Snake ss = null;
		
		ss.getMap().setSnake(ss);
		ss.getMap().initSnakeBody();
		
		assertEquals(1,1);
		
	}

}
