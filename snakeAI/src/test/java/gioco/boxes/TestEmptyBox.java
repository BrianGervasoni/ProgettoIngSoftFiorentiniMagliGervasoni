package gioco.boxes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


import boxes.*;
import gioco.snakeAI.Map;
import gioco.snakeAI.Snake;


public class TestEmptyBox {

	
	@Test
	public void testConstructor() {
		
		
		EmptyBox sn = new EmptyBox(MapElem.WALL, 5, 5);
		EmptyBox sn2 = new EmptyBox(MapElem.EMPTY, 5, 6);
		
		sn.setElementType(MapElem.WALL);
		assertEquals(MapElem.WALL, sn.getElementType());
		
		sn.setElementType(MapElem.EMPTY);
		assertEquals(MapElem.EMPTY, sn.getElementType());
	
	
	}
	
	@Test
	public void testXCoordinates() {
	
		EmptyBox sn = new EmptyBox(MapElem.WALL, 5, 7);
		assertEquals(5, sn.getXcoordinate());
		
	}
	
	
	@Test
	public void testYCoordinates() {
	
		EmptyBox sn = new EmptyBox(MapElem.WALL, 3, 7);
		assertEquals(7, sn.getYcoordinate());
		
	}
	
	
	@Test
	public void testEquals(){
			
		EmptyBox sn = new EmptyBox(MapElem.WALL, 5, 5);
		assertEquals(true, sn.equals(MapElem.WALL));
			
	}
	
}
