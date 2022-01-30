package tp.p1.objects.ships;

import tp.p1.board.Game;
import tp.p1.objects.weapons.Misil;
import tp.p1.objects.weapons.Shockwave;
import tp.p1.objects.weapons.SuperMisil;

public class UCMShip extends Ship{

	private final static int X = 3;
	private final static int Y = 8;
	private final static int life = 3;
	private static int points=0;
	private final static String name = "UCMShip";
	private static String shortname = "^__^";
	private final static String destroy = "!xx!";
	private static boolean Shoot_Misil=false;
	private static int Super_Misil=0;
	

	public UCMShip(Game game, int x, int y) {
		super(game, x, y, life);
	}
	
	public void move() {

	}
	
	public boolean move(int cant) {
		int aux=this.x+cant;
		if(aux<0) {
			return false;
		}
		else if(aux>this.game.DIM_X-1) {
			return false;
		}
		else {
			this.x=x+cant;
			return true;
		}
	}
	
	public void onDelete() {
		if(!this.isAlive())
			this.shortname=this.destroy;
	}

	public String toString() {
		return shortname;
	}
	
	public static void init() {
		points=0;
		Shoot_Misil=false;
		Super_Misil=0;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void receivePoints(int points) {
		this.points+=points;
	}
	
	public boolean shootMisil() {
		if(!this.Shoot_Misil) {
			this.Shoot_Misil=true;
			Misil misil =new Misil(game,this);
			this.game.addObject(misil);
			return true;
		}
		else return false;
	}

	public boolean buySuper() {
		if(points>=20) {
			points=points-20;
			Super_Misil++;
			return true;
		}
		else return false;	
	}
	
	public int getSuperMisil() {
		return this.Super_Misil; 
	}
	
	public boolean shootSuperMisil() {
		if(Super_Misil>0) {
			Super_Misil--;
			SuperMisil supermisil =new SuperMisil(game,this);
			this.game.addObject(supermisil);
			return true;
		}
		else return false;
	}
	
	public boolean receiveBombAttack(int damage) {
		getDamage(damage);
		return true;
	}	
	
	public void setWeapon() {
		this.Shoot_Misil= false;
	}
	
	public String stringify() {
		String D;
		D="P;";
		D+=super.stringify();
		D+=";"+this.points+";"+game.getShockwave()+";"+this.Super_Misil;
		return D;
	}
}