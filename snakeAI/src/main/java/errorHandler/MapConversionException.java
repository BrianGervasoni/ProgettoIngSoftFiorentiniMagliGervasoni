package errorHandler;

public class MapConversionException extends Exception{

	public MapConversionException(String message) {
		super(message);
		
	}
	
	/**
	 * metodo che ritorna la stringa dell'errore verificatosi durante l'esecuzione
	 */
	@Override
	public String toString() {
		
		return "Si è verificato un errore durante la conversione della mappa \nNuovo Tentativo in corso";
	}
}
