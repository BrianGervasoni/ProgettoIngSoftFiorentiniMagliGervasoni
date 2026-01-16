package gioco.boxes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import progettoAI.snakeAI.boxes.*;

public class TestAppleBox {

	@Test
	public void testSetGetElementType() {
		AppleBox apple = new AppleBox(Food.APPLE, 5, 5);
		
		apple.setElementType(Food.APPLE);
		assertEquals(Food.APPLE, apple.getElementType());
	}
	
	@Test
	public void testEquals() {
		AppleBox apple = new AppleBox(Food.APPLE, 5, 5);
		assertEquals(true, apple.equals(Food.APPLE));
	}
	
	
	
	
		
	
}
