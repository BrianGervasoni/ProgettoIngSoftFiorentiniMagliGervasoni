package gioco.boxes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import boxes.Box;
import boxes.SnakeBody;
import boxes.SnakeBox;
import gioco.snakeAI.Map;
import gioco.snakeAI.Snake;


public class TestSnakeBox {

	
	@Test
	public void testConstructor() {
		
		
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		Box sn2 = new SnakeBox(SnakeBody.HEAD, 5, 6, (SnakeBox) sn);
		
		sn.setElementType(SnakeBody.BODY);
		assertEquals(SnakeBody.BODY, sn.getElementType());
		
		sn.setBodyType(SnakeBody.TAIL);
		assertEquals(SnakeBody.TAIL, sn.getElementType());
	
	
	}
	
	@Test
	public void testRightCoordinates() {
	
		Box sn = new SnakeBox(SnakeBody.HEAD, 5, 7, null);
		assertEquals(5, sn.getXcoordinate());
		assertEquals(7, sn.getYcoordinate());
	
	}
	
	@Test
	public void testNext() {
		
		Map mm = new Map();
		Snake sssss = new Snake(mm);
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		SnakeBox sn2 = new SnakeBox(SnakeBody.BODY, 5, 6, null);
	
		sn2.setNext(sn);
		
		sssss.addPiece(sn);
		sssss.addPiece(sn2);
		
		System.out.println(sn);
		System.out.println(sn2.getNext());
		
		assertEquals(sn, sn2.getNext());

	}
	
	//risulta un problema dovuto all'obverride del metodo equals nella classe SnakeBox
	//se cambiato il nome del metodo non crea alcun prblema, ben ti sta Manu 🗿🗿(ti prego di non fucilarmi martedì mattina)
	//abbiamo usato come prova il nome equalz nella classe SnakeBox(si, proprio con la z)🙀
	@Test
	public void testEquals(){
			
		SnakeBox sn = new SnakeBox(SnakeBody.HEAD, 5, 5, null);
		assertEquals(true, sn.equals(SnakeBody.HEAD));
		
			
	}
	
}
