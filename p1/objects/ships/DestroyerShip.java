package tp.p1.objects.ships;

import tp.p1.board.Game;
import tp.p1.interfaces.IExecuteRandomActions;
import tp.p1.objects.weapons.Proyectil;


public class DestroyerShip extends AlienShip{
	
	private final static int life = 1;
	private final static int puntos=10;
	private boolean shootbomb=false;
	private final static String name = "Destroyer";
	private final static String shortname = "D";
	
	private static int currentSerialNumber;
	
	
	public DestroyerShip(Game game, int x, int y) {
		super(game, x, y, life);
		border=false;
	}

	public void computerAction() {
		if(IExecuteRandomActions.canGenerateRandomBomb(game) && !shootbomb) {
			shootbomb=true;
			Proyectil bomb =new Proyectil(game, this);
			this.game.addObject(bomb);
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
	
	public void setWeapon() {
		this.shootbomb= false;
	}
	
	public String stringify() {
		String D;
		D="D;";
		D+=super.stringify()+";"+game.Cycles_move()+";"+super.dir();
		return D;
	}	
}