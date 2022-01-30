package tp.p1.objects.ships;

import tp.p1.board.Game;
import tp.p1.objects.GameObject;

public abstract class Ship extends GameObject{
	
	public Ship(Game game, int x, int y, int live) {
		super(game, x, y, live);
	}
	
}
