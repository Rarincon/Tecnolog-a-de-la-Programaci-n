package tp.p1;

import java.util.Scanner;

import tp.p1.board.Game;
import tp.p1.board.GamePrinter;
import tp.p1.board.PrinterTypes;
import tp.p1.command.*;
import tp.p1.exception.*;

public class Controller {
	private Game game;
	private Scanner in;
	
	private GamePrinter printer;
	
	private static final String PROMPT="\nCommand >";
	private static final String unknownCommandMsg= "unknownCommandMsg";
	
	public Controller(Game game) {
		this.game = game;
		
		this.in = new Scanner(System.in);
		
	}
	
	public void run() {
		boolean playerWins=false;
		this.printer=PrinterTypes.BOARDPRINTER.getObject(game);
		System.out.println(printer);
		 
		while (!game.getFinish() && !game.getExit()){
			
			System.out.println(PROMPT);
			String[] words = in.nextLine().toLowerCase().trim().split("\\s+");
			try {
				Command command = CommandGenerator.parseCommand(words);
				if (command != null) {
					if (command.execute(game)) {
						System.out.println(printer); 
					}
				}
				else { 
					System.out.format(unknownCommandMsg);
					} 
			}catch (CommandParseException | CommandExecuteException ex) {
				System.err.format(ex.getMessage() + "%n%n");
			}

			playerWins=this.game.getWinner();
		}
		if(!game.getExit()) {
			if (!playerWins) 
				System.out.println("Aliens wins!");
			 else 
				System.out.println("Player wins!");
		}
	}
}