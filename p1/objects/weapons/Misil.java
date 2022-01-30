package tp.p1.objects.weapons;

import tp.p1.board.Game;
import tp.p1.objects.GameObject;
import tp.p1.objects.ships.UCMShip;

public class Misil extends Weapon{
	
	private final static int life = 1;
	private final static int damage=1;
	private final static String misil = "oo";
	
	public Misil(Game game, UCMShip propietario) {
		super(game,propietario, life);
	}

	public void move() {
		y--;
		if(this.isOut())
			this.live=0;
	}

	public String toString() {
		return misil;
	}
	
	//Original
	public boolean performAttack(GameObject other) {
		if(this.getX()==other.getX() && this.getY()==other.getY()&& other.receiveMissileAttack(damage)) {
			live--;
			//onDelete();
			return true;
		}
		else return false;
	}
	
	/*
	public boolean performAttack(GameObject other) {
		if(this.getX()==other.getX() && this.getY()==other.getY()){
			if(other.receiveMissileAttack(damage)) {
			live--;
			//onDelete();
			return true;
			}
			else return false;
		}
		else return false;
	}*/
	
	public boolean receiveBombAttack(int damage) {
		getDamage(damage);
		onDelete();
		return true;
	}
	
	public String stringify() {
		String D;
		D="M;";
		D+=super.stringify();
		return D;
	}
}
