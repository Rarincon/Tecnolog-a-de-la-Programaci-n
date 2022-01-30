package tp.p1.objects;

import tp.p1.board.Game;
import tp.p1.interfaces.IAttack;
import tp.p1.interfaces.IExecuteRandomActions;

public abstract class GameObject implements IAttack, IExecuteRandomActions {
	protected int x;
	protected int y;
	protected int live;
	protected Game game;
	
	public GameObject(Game game, int x, int y, int live) {
		this.x=x;
		this.y=y;
		this. game = game;
		this.live = live;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean isAlive() {
		return this.live > 0;
	}
	
	public int getLive() {
		return this.live;
	}
	
	public boolean isOnPosition(int x, int y) {
		return this.x==x && this.y==y; 
	}
	
	public void getDamage (int damage) {
		if(damage >= live)live=0;
		else
			live=live-damage;
			
		//live = (damage >= live) ? 0 : live − damage;
	}

	public boolean isOut() {
		return !game.isOnBoard(this.x,this.y);
	}
	
	

	public abstract void onDelete();
	public abstract void move();
	public abstract String toString();
	
	
	public boolean receiveExplosiveAttack(int damage) {
		this.getDamage(damage);
		return true;
	}
	

	
	public String stringify() {
		String total;
		total=this.getX()+","+ this.getY()+";"+this.getLive();
		return total;
	}
}