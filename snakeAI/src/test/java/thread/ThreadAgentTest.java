package thread;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import progettoAI.snakeAI.boxes.AppleBox;
import progettoAI.snakeAI.boxes.EmptyBox;
import progettoAI.snakeAI.boxes.Food;
import progettoAI.snakeAI.boxes.MapElem;
import progettoAI.snakeAI.boxes.SnakeBody;
import progettoAI.snakeAI.boxes.SnakeBox;
import progettoAI.snakeAI.game.Map;
import progettoAI.snakeAI.game.Snake;
import progettoAI.snakeAI.model.Model;
import progettoAI.snakeAI.thread.ThreadAgent;

class ThreadAgentTest {

	@Test
	void calculateReward() {

		Model model = new Model();
		ThreadAgent tA= new ThreadAgent(model,null);
		
		Map map = new Map();
		Snake snake = new Snake();
		
		//SnakeBox firstBodyPiece = new SnakeBox(SnakeBody.BODY, 4, 3);
		//map.setBox(firstBodyPiece,3, 4);
		//snake.addPiece(firstBodyPiece);
		
		snake.getBodyPiece(1).setXcoordinate(3); //TODO NON FUNZIONANO I SET
		snake.getBodyPiece(1).setYcoordinate(4);
		
		//SnakeBox head = new SnakeBox(SnakeBody.HEAD, 4, 4);
		//map.setBox(head, 4, 4);
		//snake.setHead(head);
		snake.getBodyPiece(0).setXcoordinate(4);
		snake.getBodyPiece(0).setYcoordinate(4);
		map.setSnake(snake);
		map.resetSnakeBoxes();
		map.insertSnakeBoxes();
		
		map.allPlaceApples().forEach(e->{
			map.setBox(new EmptyBox(MapElem.EMPTY, e.getXcoordinate(), e.getYcoordinate()), e.getXcoordinate(), e.getYcoordinate());
		});
		
		map.forceSetApple(2, 5);
		
		tA.getGame().setMap(map);
		
		//AppleBox apple = new AppleBox(Food.APPLE, 2, 5);
		//map.setBox(apple, 2, 5);	
		double reward = tA.calculateReward();
		
		System.out.println("REWARD : " + reward); //DI DEFAULT DA +4, IN QUESTO CASO NON HA NE IL +50 DI MANGIATO MELA E NE IL -50 DI ESSERE MORTO
		//VIENE QUINDI SOLO AGGIUNTO IL +variable CHE INDICA QUANTO è DISTANTE DALLA MELA NORMALIZZATO IN UN RANGE TRA (-5 E 5)
		System.out.println(); 
		for(int i=0; i<map.X; i++) {
			for(int j=0; j<map.Y; j++) {
				System.out.println(map.getBox(i, j).getElementType() + " " + i + " " + j);
			}
		}
	}
}
