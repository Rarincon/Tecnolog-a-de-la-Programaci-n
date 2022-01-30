package tp.p1.objects.weapons;

import tp.p1.board.Game;
import tp.p1.objects.GameObject;
import tp.p1.objects.ships.DestroyerShip;

public class Proyectil extends Weapon{
	
	private final static int life = 1;
	private final static int damage=1;
	private final static String misil = ".";
	
	public Proyectil(Game game, DestroyerShip propietario) {
		super(game, propietario, life);
	}
	
	public boolean performAttack(GameObject other) {
		if(this.getX()==other.getX() && this.getY()==other.getY() && other.receiveBombAttack(damage)) {
			live--;
			return true;
		}
		else return false;
	}

	public boolean receiveMissileAttack(int damage) {
		this.getDamage(damage);
		onDelete();
		return true;
	}
	
	public boolean receiveSuperMissileAttack(int damage) {
		this.getDamage(damage);
		onDelete();
		return true;
	}

	public void move() {
		this.y++;
		if(this.isOut())
			this.live=0;
	}

	public String toString() {
		return misil;
	}
	
	public String stringify() {
		String D;
		D="B;";
		D+=super.stringify();
		return D;
	}
}
