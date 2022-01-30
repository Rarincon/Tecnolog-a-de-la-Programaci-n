package tp.p1.objects.ships;

import tp.p1.board.Game;
import tp.p1.interfaces.IExecuteRandomActions;
import tp.p1.objects.weapons.Proyectil;

public class RegularShip extends AlienShip{

	private final static int life = 2;
	private final static int puntos=5;
	private final static String name = "Regular";
	private final static String shortname = "R";
	
	public RegularShip(Game game, int x, int y) {
		super(game, x, y, life);
		border=false;
	}
	
	public void computerAction() {
		if(IExecuteRandomActions.canGenerateRandomExplosiveShip(game)) {
			ExplosiveShip explosive =new ExplosiveShip(game,this.getX(), this.getY(), this.getLive());
			RemainingAliens--;
			this.game.changeObjects(this, explosive);
		}
		
	}

	public void onDelete() {
		game.receivePoints(puntos);
		RemainingAliens--;
		/*if(ContMove>0)
			ContMove--; //Revisar*/
		if(ContDown>0)
			ContDown--;
	}

	public void move() {
		super.move();
	}

	public String toString() {
		return shortname+"["+this.getLive()+"]";
	}
	
	public String stringify() {
		String D;
		D="R;";
		D+= super.stringify()+";"+game.Cycles_move()+";"+super.dir();
		return D;
	}
}