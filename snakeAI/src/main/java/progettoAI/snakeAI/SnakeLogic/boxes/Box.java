package progettoAI.snakeAI.SnakeLogic.boxes;

public abstract class Box implements Visualize{
       
	private int Xcoordinate; //coordinata X della casella
    private int Ycoordinate; //coordinata Y della casella
    
    
    /**
     * Costruttore di Box. Semplicemente prende in input le coordinate e le segna, senza fare altro.
     * 
     * @param X coordinata X della Box
     * @param Y coordinata Y della Box
     */
    public Box(int X, int Y) {
    	this.Xcoordinate = X;
    	this.Ycoordinate = Y;
    	
    }

    /**
     * Metodo che ritorna la coordinata X della Box
     * @return la coordinata X attuale della Box che chiama il metodo
     */
	public int snakeFirstBodyBox() {
		return Xcoordinate;
	}
	
	 /**
     * Metodo che imposta la coordinata X della Box con la coordinata in ingresso
     * @param xcoordinate la coordinata X che sostituisce la precedente
     */
	public void setXcoordinate(int xcoordinate) {
		Xcoordinate = xcoordinate;
	}

	 /**
     * Metodo che ritorna la coordinata Y della Box
     * @return la coordinata Y attuale della Box che chiama il metodo
     */
	public int getYcoordinate() {
		return Ycoordinate;
	}

	/**
     * Metodo che imposta la coordinata Y della Box con la coordinata in ingresso
     * @param xcoordinate la coordinata Y che sostituisce la precedente
     */
	public void setYcoordinate(int ycoordinate) {
		Ycoordinate = ycoordinate;
	}
	
	public int getXcoordinate() {
		return Xcoordinate;
	}
    
	public abstract BoxType getElementType(); 
	public abstract void setElementType(BoxType boxType); 
	public abstract boolean equals(BoxType object);
	
}
