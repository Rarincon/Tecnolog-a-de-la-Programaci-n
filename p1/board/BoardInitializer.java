package tp.p1.board;

import tp.p1.objects.GameObjectBoard;
import tp.p1.objects.ships.Ovni;
import tp.p1.objects.ships.RegularShip;
import tp.p1.objects.ships.UCMShip;
import tp.p1.objects.ships.AlienShip;
import tp.p1.objects.ships.DestroyerShip;

public class BoardInitializer {

	private final static int X_Ini=3;
	private final static int Y_Ini=1;
	
	private Level level;
	private GameObjectBoard board;
	private Game game;
	
	public GameObjectBoard initialize(Game game, Level level) {
		this.level = level; 
		this.game = game; 
		board = new GameObjectBoard(Game.DIM_X, Game.DIM_Y);
		
		AlienShip.init();
		UCMShip.init();
		
		initializeOvni (); 
		initializeRegularAliens (); 
		initializeDestroyerAliens ();
		return board;
	}
	
	private void initializeOvni () { 
		Ovni object = new Ovni(game);
		board.add(object);
	}
	
	private void initializeRegularAliens () { 
		int numR=game.getLevel().getNumRegularAliens();
		int aux= game.getLevel().getNumRegularAliensPerRow();
		
		int x=X_Ini;
		int y=Y_Ini;
		
		for(int i=0;i< numR;i++) {
			RegularShip object = new RegularShip(game,x,y);
			board.add(object);
			x++;
			if((i+1)%aux==0) {
				y++;
				x=X_Ini;
			}
		}
	}
	
	private void initializeDestroyerAliens() {
		int numD=game.getLevel().getNumDestroyerAliens();
		int aux= game.getLevel().getNumDestroyerAliensPerRow();
		int col= game.getLevel().getNumRowsOfRegularAliens();
		
		
		int x=X_Ini;
		int y=Y_Ini+col;
		if(numD==2)
			x++;
		
		for(int i=0;i< numD;i++) {
			DestroyerShip object = new DestroyerShip(game,x,y);
			board.add(object);
			x++;
		}
	}

}
