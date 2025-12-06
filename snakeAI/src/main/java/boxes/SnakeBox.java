package boxes;

public class SnakeBox extends Box implements Visualize{

	//Una SnakeBox può contenere la testa, il corpo o la coda del serpente
	private SnakeBody element;
	
	//variabile usata per indicare il pezzo successivo del corpo (dalla testa alla coda)
	private SnakeBox next;
	
	/**
	 * Questo è il costruttore della classe SnakeBox
	 * 
	 * @param piece riceve in ingresso l'enumerazione SnakeBody
	 * @param Xcoordinate riceve in ingresso la coordinata X della Box
	 * @param Ycoordinate riceve in ingresso la coordinata Y della Box
	 * @param next riceve in ingresso il pezzo seguente del corpo (es: la testa punta a null, il primo pezzo del corpo punta alla testa ecc...)
	 */
	public SnakeBox(SnakeBody piece, int Xcoordinate, int Ycoordinate, SnakeBox next) {
		super(Xcoordinate, Ycoordinate);
		this.element = piece;
		this.next=next;
	}
	
	

	/**
	 * Metodo che stampa l'enumerazione contenuta nella casella che chiama il metodo
	 * Metodo ereditato dalla interface Visualize
	 */
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
	
	/**
	 * Metodo usato per ritornare la parte successiva del serpente
	 * @param next ritorna in uscita il pezzo successivo del serpente
	 */
	public void setNext(SnakeBox next) {
		
		this.next = next;
	}
	
	/**
	 * metodo che altera l'enumerazione Snake Body della Box che chiama il metodo
	 * @param element enumerazione di Snake Body
	 */
	public void setBodyType(SnakeBody element) {
		
		this.element = element;
		
	}
	
	
}
