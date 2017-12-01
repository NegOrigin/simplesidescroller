package modele;

import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class Hero {
	
	private ArrayList<Limb> limbs;
	
	private int posY;

	public Hero() {
		limbs = new ArrayList<Limb>();
		posY = 0;
	}
	
	public String toString() {
		return Integer.toString(getLimbNumber());
	}
	
	public int getLimbNumber() {
		return limbs.size();
	}
	
	// Donne une impultion verticale au héros pour un temps dépendant de la valeur donnée power
	public void jump(int power) {
		if(posY == 0) {
			posY++;
			
			Task<Void> sleeper = new Task<Void>() {
	            protected Void call() throws Exception {
	                try {
	                    Thread.sleep(1000/8*(power+1));
	                } catch (InterruptedException e) {}
	                return null;
	            }
	        };
	        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            public void handle(WorkerStateEvent event) {
	            	posY--;
	            }
	        });
	        new Thread(sleeper).start();
		}
	}
	
	public void addLimb(Limb limb) {
		limbs.add(limb);
	}

	public int getPosY() {
		return posY;
	}
	
}
