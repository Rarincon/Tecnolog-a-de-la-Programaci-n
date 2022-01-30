  package tp.p1.command;

import java.util.Arrays;
import java.util.Objects;

import tp.p1.board.Game;
import tp.p1.exception.CommandExecuteException;
import tp.p1.exception.CommandParseException;

public class MoveCommand extends Command{
	private String[] mover;

	public MoveCommand() {
		super("move", "m", "[M]ove<[R]ight/[L]eft><1/2>", "Move the Ship");
	}
	
	public MoveCommand(String[] other){
		super("move", "m", "[M]ove<[R]ight/[L]eft><1/2>", "Move the Ship");
		mover=other;
	}
	

	public boolean execute(Game game)throws CommandExecuteException {
		if(mover.length==3) {	
			if((Objects.equals("right",mover[1]))||(Objects.equals("r",mover[1]))){ 
				if(Objects.equals("1",mover[2])) {
					if(game.move(1)) {
						game.update();
						return true;
					}
					else 	
						throw new CommandExecuteException("El usuario no se puede mover a esa posicion");
				}
				else if(Objects.equals("2",mover[2])) {
					if(game.move(2)) {
						game.update();
						return true;
					}
					else throw new CommandExecuteException("El usuario no se puede mover a esa posicion");
					}
				else
					throw new CommandExecuteException("El comando '"+ mover[2] +"' no existe");
			}
			else if((Objects.equals("left",mover[1]))||(Objects.equals("l",mover[1]))) {
				if(Objects.equals("1",mover[2])) {
					if(game.move(-1)) {
						game.update();
						return true;
					}
					else throw new CommandExecuteException("El usuario no se puede mover a esa posicion");
					}
				else if(Objects.equals("2",mover[2])) {
					if(game.move(-2)) {
						game.update();
						return true;
					}
					else throw new CommandExecuteException("El usuario no se puede mover a esa posicion");
					}
				else
					throw new CommandExecuteException("El comando '"+ mover[2] +"' no existe");
			}
			else
				throw new CommandExecuteException("El comando '"+ mover[1] +"' no existe");
		}
		else
			throw new CommandExecuteException("El comando esta incompleto");
	}



	public Command parse(String[] commandWords) {
		if(this.matchCommandName(commandWords[0]))
			return new MoveCommand(commandWords);
		else
			return null;
	}

}
