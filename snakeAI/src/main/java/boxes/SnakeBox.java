package boxes;

public class SnakeBox extends Box implements Visualize{

	private SnakeBody element;
	
	private SnakeBox next;
	
	public SnakeBox(SnakeBody piece, int Xcoordinate, int Ycoordinate, SnakeBox next) {
		super(Xcoordinate, Ycoordinate);
		this.element = piece;
		this.next=next;
	}
	
	

	@Override
	
	public void visual() {
		
		
		int nextX = 0;
		int nextY = 0;
		
		if(next != null) {
			nextX = next.getXcoordinate();
			nextY = next.getYcoordinate();
		}
			
		
		if((super.getXcoordinate() - nextX) != 0)
			System.out.print(element.getC());
		else
			System.out.print(element.getD());
	}
	
	
	
}
