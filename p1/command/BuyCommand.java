package tp.p1.command;

import tp.p1.board.Game;
import tp.p1.exception.CommandExecuteException;

public class BuyCommand extends NoParamsCommand{

	public BuyCommand() {
		super("Buy", "b", "[B]uy", "You can buy a Super Misil if you've more than 19 points");
	}
	
	public boolean execute(Game game)throws CommandExecuteException {
		if(game.buySuper()) {
			System.out.print("Comprado");
			return true;
		}
		else throw new CommandExecuteException("No puedes comprar un super misil ");
	}

}
