package tp.p1.objects.ships;

import tp.p1.board.Game;
import tp.p1.interfaces.IExecuteRandomActions;

public class Ovni extends EnemyShip{

	private static int X = 9;
	private final static int Y = 0;
	private static int life = 1;
	private final static int puntos=25;
	private final static String name = "Ovni";
	private final static String shortname = "O";
	
	private boolean print=false;
	
	public Ovni(Game game) {
		super(game, X, Y, life);
	}

	public void computerAction() {
		if(!print && IExecuteRandomActions.canGenerateRandomOvni(game)) {
			print=true;
			x=9;
			y=0;
		}	
	}

	public void onDelete() {
		
	}

	public void move() {
		if(this.x==0) {
			this.x=9;
			print=false;
		}
		else if(print)
			this.x--;
	}

	public String toString() {
		if(print)
			return shortname+ "["+this.getLive()+"]";
		else
			return " ";
	}	
	
	public boolean receiveMissileAttack(int damage) {
		this.getDamage(damage);
		return true;
	}
	
	public void getDamage (int damage) {
		game.receivePoints(puntos);
		game.setShockwave();
		print=false;
	}
	
	public String stringify() {
		String D;
		D="O;"+ this.getX()+";"+this.getY()+";";
		if(this.print)
			D+=this.getLive();
		else
			D+="0";
		//D+=super.stringify();
		return D;
	}
}