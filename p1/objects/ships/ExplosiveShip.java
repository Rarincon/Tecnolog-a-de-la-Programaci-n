package tp.p1.objects.ships;

import tp.p1.board.Game;
import tp.p1.objects.GameObject;

public class ExplosiveShip extends AlienShip {
	

	private final static int puntos=5;
	private final static int damage=1;
	private final static String name = "Explosive";
	private final static String shortname = "E";
	
	public ExplosiveShip(Game game, int x, int y, int life) {
		super(game, x, y, life);
	}
	
	public void onDelete() {
		game.receivePoints(puntos);
		RemainingAliens--;
		if(ContDown>0)
			ContDown--;
		game.checkattacks(this);
	}
	
	public void move() {
		super.move();
	}
	
	public String toString() {
		return shortname+"["+this.getLive()+"]";
	}
	
	public boolean performAttackExplosive(GameObject other) { 

			if(((this.getX()==(other.getX()+1))||(this.getX()==(other.getX()-1))
					||(this.getX()==other.getX())) && ((this.getY()==(other.getY()-1))
							|| (this.getY()==(other.getY()-1))
							||(this.getY()==other.getY())) && other.receiveExplosiveAttack(damage)){
				return true;
			}
			else return false;

	}
	
	public String stringify() {
		String D;
		D="E;";
		D+=super.stringify()+";"+game.Cycles_move()+";"+super.dir();
		return D;
	}
	
	
}