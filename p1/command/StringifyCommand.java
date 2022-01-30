package tp.p1.command;

import tp.p1.board.Game;
import tp.p1.board.Stringifier;
import tp.p1.exception.CommandExecuteException;

public class StringifyCommand extends NoParamsCommand{

	
	public StringifyCommand() {
		super("stringify", "str", "[STR]ingify", "Print the game in a different form");
	}

	public boolean execute(Game game) throws CommandExecuteException {
		Stringifier.String();
		//System.out.println(game.stringify());
		return false;
	}
}
