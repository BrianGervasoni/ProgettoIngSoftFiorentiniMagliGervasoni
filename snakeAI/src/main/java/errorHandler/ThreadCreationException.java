package errorHandler;

public class ThreadCreationException extends Exception{
	
	public ThreadCreationException(String message) {
		super(message);
		
	}
	
	public ThreadCreationException(String message, Throwable cause) {
        super(message, cause);
    }
	
	/**
	 * metodo che ritorna la stringa dell'errore verificatosi durante l'esecuzione
	 */
	@Override
	public String toString() {
		
		return "Si è verificato un errore nella creazione del thread";
	}

}
