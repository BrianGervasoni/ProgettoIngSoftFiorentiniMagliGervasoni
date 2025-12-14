package errorHandler;

public class LoadSaveModelException extends Exception{

	public LoadSaveModelException(String message) {
		super(message);
		
	}
	
	public LoadSaveModelException(String message, Throwable cause) {
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
