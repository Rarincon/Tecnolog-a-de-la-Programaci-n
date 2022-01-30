package tp.p1.command;

import tp.p1.board.Game;
import tp.p1.board.PrinterTypes;

public class ListPrintersCommand extends NoParamsCommand{

	public ListPrintersCommand() {
		super("listprinters", "lp", "[L]ist[P]rinters", "No se que hace");
	}
	
	public boolean execute(Game game) {
		System.out.println(PrinterTypes.printerHelp(game));
		return true;
	}
}
