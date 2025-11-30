package boxes;

/**
 * Hello world!
 */
public abstract class Box implements Visualize{
       
    private int Xcoordinate;
    private int Ycoordinate;
    
    
    /**
     * Costruttore di Box. Semplicemente prende in input le coordinate e le segna, senza fare altro.
     * 
     * @param X
     * @param Y
     */
    public Box(int X, int Y) {
    	this.Xcoordinate = X;
    	this.Ycoordinate = Y;
    	
    }

	public int getXcoordinate() {
		return Xcoordinate;
	}

	public void setXcoordinate(int xcoordinate) {
		Xcoordinate = xcoordinate;
	}

	public int getYcoordinate() {
		return Ycoordinate;
	}

	public void setYcoordinate(int ycoordinate) {
		Ycoordinate = ycoordinate;
	}
    
    
	
}
