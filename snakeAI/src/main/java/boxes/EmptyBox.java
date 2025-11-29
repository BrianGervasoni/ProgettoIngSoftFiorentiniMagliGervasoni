package boxes;

public class EmptyBox extends Box implements Visualize{

	public MapElem element;
	
	public EmptyBox(MapElem element, int Xcoordinate, int Ycoordinate) {
		super(Xcoordinate, Xcoordinate);
		this.element = element;
		
		
	}

	@Override
	public void visual() {
		System.out.print(element.getC());
		
	}
	
	public String getElem() {
		return this.element.getC();
	}
	
}
