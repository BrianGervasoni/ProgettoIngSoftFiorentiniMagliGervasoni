package boxes;

public enum SnakeBody {

	Head("O ", ""){
		
		
	},
	
	Body("| " , "--"){
		
		
	},
	
	Tail("| " , "--"){
		
		
	};
	
	private String verticalBody;
	private String horizontalBody;
		
	
	SnakeBody(String verticalBody, String horizontalBody){
		this.verticalBody = verticalBody;
		this.horizontalBody = horizontalBody;
	}

	
	
	
		
	public String getC() {
		return this.verticalBody;
	}
	
	public String getD() {
		return this.horizontalBody;
	}
	
	
	public String getB() {
		return this.horizontalBody;
	}
	
	
	
	
	
}
