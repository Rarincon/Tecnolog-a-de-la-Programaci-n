package tp.p1.objects.weapons;

import tp.p1.board.Game;
import tp.p1.objects.GameObject;
import tp.p1.objects.ships.Ship;

public abstract class Weapon extends GameObject {
	private Ship propietario;
	
	public Weapon(Game game, Ship propietario, int live) {
		super(game, propietario.getX(), propietario.getY(), live);
		this.propietario= propietario;
	}
	
	public Weapon(Game game, int x, int y, int live) {
		super(game, x, y, live);
	}
	
	public void onDelete() {
		if(!isAlive()) 
			propietario.setWeapon();
	}
	
	public String stringify() {
		String total;
		total= this.getX()+","+this.getY();
		return total;
	}
}
