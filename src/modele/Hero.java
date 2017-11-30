package modele;

import java.util.ArrayList;

public class Hero {
	
	private ArrayList<Limb> limbs;
	
	private int posX;
	private int posY;

	public Hero() {
		limbs = new ArrayList<Limb>();
		posX = 0;
		posY = 0;
	}
	
	public String toString() {
		return Integer.toString(getLimbNumber());
	}
	
	public int getLimbNumber() {
		return limbs.size();
	}
	
	public void move(int x, int y) {
		posX += x;
		posY += y;
	}
	
	public void addLimb(Limb limb) {
		limbs.add(limb);
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
}
