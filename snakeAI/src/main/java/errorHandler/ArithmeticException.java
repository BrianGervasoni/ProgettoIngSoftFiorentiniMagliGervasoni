package errorHandler;

public class ArithmeticException extends Exception{
	
	public ArithmeticException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	/**
	 * metodo che ritorna la stringa dell'errore verificatosi durante l'esecuzione
	 */
	@Override
	public String toString() {
		
		return "Si è verificato un errore durante la sessione di calcolo dell'AI";
	}
}
