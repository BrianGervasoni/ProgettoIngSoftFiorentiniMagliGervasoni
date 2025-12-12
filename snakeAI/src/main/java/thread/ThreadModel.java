package thread;
import model.Model;

public class ThreadModel extends Thread{

	Model model;
	String dirFile; 
	boolean ready; //DA AGGIUNGERE A UML
	
	public ThreadModel(String dir) {
		this.dirFile = dir;
	}
	
	@Override
	public void run() {
		
	}
	
	public void update() {
		
	}

	public void save() {
		
	}
	
	public void load() {
		
	}
	
	public int forwading(double[] input) {
		
		return 0;
		
	}
	
	public double backPropagation() {
		
		return 0;
		
	}
}


