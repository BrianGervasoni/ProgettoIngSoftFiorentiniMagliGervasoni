package boxes;

public class AppleBox extends Box implements Visualize, Equals {
	
	//una AppleBox può contenere o no la mela
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

	/**
	 * Metodo che stampa l'enumerazione contenuta nella casella che chiama il metodo
	 * Metodo ereditato dalla interface Visualize
	 * @return 
	 */
	@Override
	public void visual() {
		
		// element.getFood();
	}

	@Override
	public BoxType getElementType() {
		
		return this.element;
		
	}

	@Override
	public void setElementType(BoxType boxType) {
		
		this.element = (Food) boxType;
	}
	
	@Override
	public boolean equals(Object object) {
		if(this.element == object) {
			return true;
		}
		return false;
	}
	
	
}
