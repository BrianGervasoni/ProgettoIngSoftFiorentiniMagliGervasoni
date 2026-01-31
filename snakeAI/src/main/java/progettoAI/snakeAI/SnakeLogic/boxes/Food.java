package progettoAI.snakeAI.SnakeLogic.boxes;

public enum Food implements BoxType{

	
	APPLE("A "){ //la mela obiettivo del serpente
		
		
	};
	
private String food;
	
	/**
	 * costruttore dell'enumerazione
	 * @param str stringa in ingersso con sui si raprresenta l'elemento dell'enumerazione
	 */
	Food(String str) {
		this.food = str;
	}
	
	/**
	 * metodo che ritorna la stringa dell'elemento dell'enumerazione
	 * @return la stringa dell'enumerazione
	 */
	public String getFood() {
		
		return food;
	}
	
}
