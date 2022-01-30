package tp.p1.objects.ships;

import tp.p1.board.Game;

public abstract class AlienShip extends EnemyShip {

	
	protected static int RemainingAliens=0;
	protected static boolean landed= false;
	protected static boolean left=true;
	protected static boolean border=false;
	protected static boolean down=false;
	protected static int ContDown=0;
	protected static int ContMove=0;
	
	public AlienShip(Game game, int x, int y, int live) {
		super(game, x, y, live);
		this.RemainingAliens++; 
	}
	
	public static boolean allDead() {
		return RemainingAliens<=0;
	}
	
	public static boolean haveLanded() {
		return landed;
	}

	public static int getCount() {
		return RemainingAliens;
	}
	
	
	public boolean receiveShockWaveAttack(int damage) {
		this.getDamage(damage);
		return true;
	}
	
	public static void init() {
		RemainingAliens=0;
		left=true;
		border=false;
		down=false;
		ContDown=0;
		ContMove=0;
	}
	
	public void move() {
		if(ContMove==0 && ContDown!=0) {
			moveDown();
		}
		else if(this.game.Move()) {
			down=false;
			if(left)
				this.x--;
			else
				this.x++;
			ContMove++;
		}
		if(x==0 || x==game.DIM_X-1) {
			if(!border && !down) {
				border=true;
				ContDown=RemainingAliens;
				//ContMove++;
			}
		}
		if(ContMove==RemainingAliens)
			ContMove=0;
		if(y==game.DIM_Y-1)
			landed=true;
	}
	
	public void moveDown() {
		y++;
		ContDown--;
		if(ContDown==0) {
			border=false;
			down=true;
			left=!left;
		}
	}
	
	public String dir() {
		String dir;
		if(ContDown>0)
			dir="down";
		else if(left)
			dir="left";
		else
			dir="right";
		return dir;
		
	}
}
