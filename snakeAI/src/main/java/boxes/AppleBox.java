package boxes;

public class AppleBox extends Box {

	private Food element;
	
	/**
	 * 
	 * Costruttore di AppleBox. Eredita da Box, e semplicemente richiama il costruttore padre.
	 * 
	 * @param Xcoordinate
	 * @param Ycoordinate
	 * 
	 * 
	 */
	public AppleBox(Food element, int Xcoordinate, int Ycoordinate) {
		
		super(Xcoordinate, Ycoordinate);
		this.element = element;
	}


	public void visual() {
		
		
		System.out.print(element.getFood());
	}
	
}
