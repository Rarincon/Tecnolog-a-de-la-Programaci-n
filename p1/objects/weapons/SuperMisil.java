package tp.p1.objects.weapons;

import tp.p1.board.Game;
import tp.p1.objects.GameObject;
import tp.p1.objects.ships.UCMShip;

public class SuperMisil extends Weapon{

	private final static int life = 1;
	private final static int damage=2;
	private final static String misil = "8";
	
	public SuperMisil(Game game, UCMShip propietario) {
		super(game, propietario, life);
	}

	public void move() {
		y--;
		if(this.isOut())
			this.live=0;
	}
	
	public void onDelete() {
		
	}

	public String toString() {
		return misil;
	}

	public boolean performAttack(GameObject other) {
		if(this.getX()==other.getX() && this.getY()==other.getY() && other.receiveSuperMissileAttack(damage)){
			live--;
			return true;
		}
		else return false;
	}
	
	public boolean receiveBombAttack(int damage) {
		getDamage(damage);
		onDelete();
		return true;
	}
	
	public String stringify() {
		String D;
		D="X;";
		D+=super.stringify();
		return D;
	}
}
