package tp.p1.command;

import tp.p1.board.Game;
import tp.p1.exception.CommandExecuteException;

public class ShockwaveCommand extends NoParamsCommand{

	public ShockwaveCommand() {
		super("shockwave","w","Shock[W]ave","Use shockwave");
	}


	public boolean execute(Game game) throws CommandExecuteException {

		if(game.shockWave()) {
			game.update();
			return true;
		}
		else {
			throw new CommandExecuteException("\nNo puedes usar el shockwave ahora");

		}
	}
}
