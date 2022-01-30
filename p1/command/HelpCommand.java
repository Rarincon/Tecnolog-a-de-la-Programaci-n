package tp.p1.command;

import tp.p1.board.Game;

public class HelpCommand extends NoParamsCommand{

	public HelpCommand() {
		super("help", "h", "[H]elp","Print this help message");
	}


	public boolean execute(Game game) {
		System.out.print(CommandGenerator.commandHelp());
		return false;
	}

}
