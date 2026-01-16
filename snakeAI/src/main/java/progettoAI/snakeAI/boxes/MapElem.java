package progettoAI.snakeAI.boxes;

public enum MapElem implements BoxType{

	EMPTY ("  "){ //casella vuota
		
		
	}, 
	
	WALL ("|"){//muro della mappa
		
		
	};
	
private String pe;
	
	/**
	 * costruttore dell'enumerazione
	 * @param str stringa in ingersso con sui si raprresenta l'elemento dell'enumerazione
	 */
	MapElem(String pe){
		this.pe = pe;
	}
	
	/**
	 * metodo che ritorna la stringa dell'elemento dell'enumerazione
	 * @return la stringa dell'enumerazione
	 */
	public String getC() {
		return this.pe;
	}
	
	
}
