package thread;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import boxes.AppleBox;
import boxes.Food;
import boxes.SnakeBody;
import boxes.SnakeBox;
import gioco.snakeAI.Map;
import gioco.snakeAI.Snake;

class ThreadAgentTest {

	@Test
	void calculateReward() {

		Map map = new Map();
		Snake snake = new Snake();
		
		SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.BODY, 4, 3);
		map.setBox(firstBodyPiece,3, 4);
		snake.addPiece(firstBodyPiece);
		
		SnakeBox head = new SnakeBox(SnakeBody.HEAD, 4, 4);
		map.setBox(head, 4, 4);
		snake.setHead(head);
		
		map.setSnake(snake);
		
		AppleBox apple = new AppleBox(Food.APPLE, 2, 5);
		map.setBox(apple, 2, 5);	
	}

}
