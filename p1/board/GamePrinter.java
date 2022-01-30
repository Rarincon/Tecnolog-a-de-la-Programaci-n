package tp.p1.board;

import tp.p1.util.MyStringUtils;

public abstract class GamePrinter {
	
	protected static Game game; //Mirar si da error ponerlo static
	
	public void setGame(Game game) {
		this.game=game;
	}	
}