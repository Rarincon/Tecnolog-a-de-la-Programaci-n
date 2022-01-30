package tp.p1.interfaces;

import tp.p1.board.Game;

public interface IExecuteRandomActions {
	
	static boolean canGenerateRandomOvni(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getOvniFrequency();
	}
	
	static boolean canGenerateRandomBomb(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getShootFrequency();
	}
	
	static boolean canGenerateRandomExplosiveShip(Game game){
		return game.getRandom().nextDouble() < game.getLevel().getTurnExplodeFrequency();
	}
	
	default void computerAction() { 
		
	}
	
	default String stringify() {
		return null;
	}
}