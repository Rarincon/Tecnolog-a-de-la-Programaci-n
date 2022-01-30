package tp.p1.command;

import tp.p1.board.Game;

public class ListCommand extends NoParamsCommand{
	
	public ListCommand() {
		super("list","l", "[L]ist","ver lista");
	}


	public boolean execute(Game game) {
		System.out.print(game.list());
		return false;
		}

}
