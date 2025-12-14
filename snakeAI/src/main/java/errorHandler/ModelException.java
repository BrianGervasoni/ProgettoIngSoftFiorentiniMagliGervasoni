package errorHandler;

public class ModelException extends Exception{
	
	public ModelException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	/**
	 * metodo che ritorna la stringa dell'errore verificatosi durante l'esecuzione
	 */
	@Override
	public String toString() {
		
		return "Si è verificato un errore durante il salvataggio del modello";
	}
}
