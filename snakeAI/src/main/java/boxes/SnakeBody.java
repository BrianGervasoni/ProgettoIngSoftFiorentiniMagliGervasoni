package boxes;

public enum SnakeBody implements BoxType{

	HEAD("O "){ //testa del serpente
		
		
	},
	
	BODY("█ "){//corpo del serpente
		
		
	},
	
	TAIL("█ "){//coda del serpente
		
		
	};
	
	private String verticalBody;
		
	/**
	 * costruttore dell'enumerazione
	 * @param verticalBody stringa in ingersso con sui si raprresenta l'elemento dell'enumerazione
	 * @param horizontalBody stringa in ingersso con sui si raprresenta l'elemento dell'enumerazione
	 */
	SnakeBody(String verticalBody){
		this.verticalBody = verticalBody;
	}

	/**
	 * metodo che ritorna la stringa dell'elemento dell'enumerazione
	 * @return la stringa dell'enumerazione
	 */	
	public String getC() {
		return this.verticalBody;
	}
	
}
