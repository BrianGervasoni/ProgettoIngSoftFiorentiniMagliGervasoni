package boxes;

public enum MapElem {

	Empty (" "){
		
		
	}, 
	
	Wall ("|"){
		
		
	};
	
	private String pe;
	
	MapElem(String pe){
		this.pe = pe;
	}
	
	public String getC() {
		return this.pe;
	}
}
