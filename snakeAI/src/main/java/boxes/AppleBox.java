package boxes;

public class AppleBox extends Box implements Visualize {
	
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
	 */
	@Override
	public void visual() {
		
		System.out.print(element.getFood());
	}
	
}
