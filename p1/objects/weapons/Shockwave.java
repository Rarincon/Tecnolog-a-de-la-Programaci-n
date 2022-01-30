package tp.p1.objects.weapons;

import tp.p1.board.Game;
import tp.p1.objects.GameObject;
import tp.p1.objects.ships.AlienShip;

public class Shockwave extends Weapon{
	private final static int life = 1;
	private final static int damage=1;

	public Shockwave(Game game) {
		super(game, 10, 9, life);

	}

	public void onDelete() {
	}

	public void move() {
	}

	public String toString() {
		return null;
	}
	
	public boolean performAttack(GameObject other) {
		if(other.receiveShockWaveAttack(damage)) {
			live--;
		}
		return true;
	}

}
