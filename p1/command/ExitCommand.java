package tp.p1.command;

import tp.p1.board.Game;

public class ExitCommand extends NoParamsCommand{

	public ExitCommand() {
		super("exit", "e", "[E]xit","Terminates the Game");
	}

	public boolean execute(Game game) {
		game.setExit();
		System.out.print("Game Over");
		return false;
	}
}
