package boxes;

public interface BoxType {

	
	public default Object getReturnType() {
		return (this.getClass());
	}
}
