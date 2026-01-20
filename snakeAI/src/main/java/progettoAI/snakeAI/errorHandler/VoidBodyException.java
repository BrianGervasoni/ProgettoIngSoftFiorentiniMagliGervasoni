package progettoAI.snakeAI.errorHandler;

public class VoidBodyException extends Exception {
	
	public VoidBodyException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	/**
	 * metodo che ritorna la stringa dell'errore verificatosi durante la verifica della lunghezza dello snake
	 */
	@Override
	public String toString() {
		
		return "Lo snake ha lunghezza nulla";
	}
}
