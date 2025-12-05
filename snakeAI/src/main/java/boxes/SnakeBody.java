package boxes;

public enum SnakeBody {

	Head("O ", ""){ //tesat del serpente
		
		
	},
	
	Body("| " , "--"){//corpo del serpente
		
		
	},
	
	Tail("| " , "--"){//coda del serpente
		
		
	};
	
	private String verticalBody;
	private String horizontalBody;
		
	/**
	 * costruttore dell'enumerazione
	 * @param verticalBody stringa in ingersso con sui si raprresenta l'elemento dell'enumerazione
	 * @param horizontalBody stringa in ingersso con sui si raprresenta l'elemento dell'enumerazione
	 */
	SnakeBody(String verticalBody, String horizontalBody){
		this.verticalBody = verticalBody;
		this.horizontalBody = horizontalBody;
	}

	/**
	 * metodo che ritorna la stringa dell'elemento dell'enumerazione
	 * @return la stringa dell'enumerazione
	 */	
	public String getC() {
		return this.verticalBody;
	}
	
	/**
	 * metodo che ritorna la stringa dell'elemento dell'enumerazione
	 * @return la stringa dell'enumerazione
	 */
	public String getD() {
		return this.horizontalBody;
	}
	
	
}
