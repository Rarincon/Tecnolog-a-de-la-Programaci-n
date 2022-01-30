package tp.p1.command;

import tp.p1.board.Game;

public class ResetCommand extends NoParamsCommand {

	public ResetCommand() {
		super("reset","r","[R]eset","Reset the game");
	}


	public boolean execute(Game game) {
		game.reset();
		return true;
	}

}
