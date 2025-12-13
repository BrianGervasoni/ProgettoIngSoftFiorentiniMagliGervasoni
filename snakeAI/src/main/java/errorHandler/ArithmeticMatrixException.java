package errorHandler;

public class ArithmeticMatrixException extends Exception{

	public ArithmeticMatrixException(String message) {
		super(message);
		
	}
	
	public ArithmeticMatrixException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	/**
	 * metodo che ritorna la stringa dell'errore verificatosi durante l'esecuzione
	 */
	@Override
	public String toString() {
		
		return "Si è verificato un errore durante la sessione di calcolo dell'AI \nNuovo Tentativo in corso";
	}
}
