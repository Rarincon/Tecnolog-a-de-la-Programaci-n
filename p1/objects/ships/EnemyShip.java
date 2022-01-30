package tp.p1.objects.ships;

import tp.p1.board.Game;

public abstract class EnemyShip extends Ship {
	
	public EnemyShip(Game game, int x, int y, int live) {
		super(game, x, y, live);
	}
	
	public boolean receiveMissileAttack(int damage) {
		this.getDamage(damage);
		return true;
	}
	
	public boolean receiveSuperMissileAttack(int damage) {
		this.getDamage(damage);
		return true;
	}
}