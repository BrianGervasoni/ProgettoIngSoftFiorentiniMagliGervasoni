package thread;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import boxes.AppleBox;
import boxes.Food;
import boxes.SnakeBody;
import boxes.SnakeBox;
import gioco.snakeAI.Map;
import gioco.snakeAI.Snake;
import model.Model;

class ThreadAgentTest {

	@Test
	void calculateReward() {

		Model model = new Model();
		ThreadAgent tA= new ThreadAgent(model);
		
		Map map = new Map();
		Snake snake = new Snake();
		
		//SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.BODY, 4, 3);
		//map.setBox(firstBodyPiece,3, 4);
		//snake.addPiece(firstBodyPiece);
		
		snake.getBodyPiece(1).setXcoordinate(3);
		snake.getBodyPiece(1).setYcoordinate(4);
		
		//SnakeBox head = new SnakeBox(SnakeBody.HEAD, 4, 4);
		//map.setBox(head, 4, 4);
		//snake.setHead(head);
		snake.getBodyPiece(0).setXcoordinate(4);
		snake.getBodyPiece(0).setYcoordinate(4);
		
		map.resetSnakeBoxes();
		map.insertSnakeBoxes();
		
		AppleBox apple = new AppleBox(Food.APPLE, 2, 5);
		map.setBox(apple, 2, 5);	
		double reward = tA.calculateReward(map);
		
		System.out.println(reward); //DI DEFAULT DA +4, IN QUESTO CASO NON HA NE IL +50 DI MANGIATO MELA E NE IL -50 DI ESSERE MORTO
		//VIENE QUINDI SOLO AGGIUNTO IL +variable CHE INDICA QUANTO è DISTANTE DALLA MELA NORMALIZZATO IN UN RANGE TRA (-5 E 5)
	}

}
