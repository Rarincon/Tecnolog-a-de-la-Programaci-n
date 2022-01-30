package tp.p1.command;

import tp.p1.board.Game;

public class UpdateCommand extends NoParamsCommand{

	public UpdateCommand() {
		super("none", "","none","Skips a cycle");
	}

	public boolean execute(Game game) {
		game.update();
		return true;
	}
}
