package boxes;

/**
 * Hello world!
 */
public abstract class Box implements Visualize{
       
    private int Xcoordinate;
    private int Ycoordinate;
    
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
