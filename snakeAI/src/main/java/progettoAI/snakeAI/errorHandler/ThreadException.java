package progettoAI.snakeAI.errorHandler;

public class ThreadException extends Exception{
	
	public ThreadException(String message, Throwable cause) {
        super(message, cause);
    }
	
	/**
	 * metodo che ritorna la stringa dell'errore verificatosi durante l'esecuzione
	 */
	@Override
	public String toString() {
		
		return "Si è verificato un errore nella gestione dei thread";
	}

}
