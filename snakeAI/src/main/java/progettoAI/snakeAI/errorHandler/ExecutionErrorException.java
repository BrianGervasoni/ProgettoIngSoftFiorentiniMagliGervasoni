package progettoAI.snakeAI.errorHandler;

public class ExecutionErrorException extends Exception{
	
	public ExecutionErrorException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	/**
	 * metodo che ritorna la stringa dell'errore verificatosi durante l'esecuzione
	 */
	@Override
	public String toString() {
		
		return "Si è verificato un errore durante l'esecuzione del gioco Snake";
	}
	
}
