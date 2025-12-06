package boxes;

public class EmptyBox extends Box implements Visualize{

	//Una EmptyBox può essere vuota o contenere una parete del campo 
	private MapElem element;
	
	/**
	 * Questo è il costruttore della classe EmptyBox
	 * 
	 * @param element riceve in ingresso l'enumerazione MapElem
	 * @param Xcoordinate riceve in ingresso la coordinata X della Box
	 * @param Ycoordinate riceve in ingresso la coordinata Y della Box
	 */
	public EmptyBox(MapElem element, int Xcoordinate, int Ycoordinate) {
		super(Xcoordinate, Xcoordinate);
		this.element = element;
		
		
	}

	/**
	 * Metodo che stampa l'enumerazione contenuta nella casella che chiama il metodo
	 * Metodo ereditato dalla interface Visualize
	 */
	@Override
	public void visual() {
		System.out.print(element.getC());
		
	}
	
	
	/**
	 * Metodo usato per recuperare l'enumerazione della EmptyBox in forma di Stringa
	 * 
	 * @return l'enumerazione rappresentata come Stringa
	 */
	public String getElem() {
		return this.element.getC();
	}
	
	
	/**
	 * Metodo usato per recuperare l'enumerazione della EmptyBox
	 * 
	 * @return l'enumerazione
	 */
	public MapElem getEnum() {
		return element;
	}
	
	
}
