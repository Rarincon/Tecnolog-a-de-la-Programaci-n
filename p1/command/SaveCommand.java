package tp.p1.command;

import java.io.File;
import java.io.IOException;

import tp.p1.board.Game;
import tp.p1.exception.CommandExecuteException;
import tp.p1.exception.CommandParseException;


public class SaveCommand extends Command{
	
	private String fileName;
	private String[] save;
	public static final String filenameInUseMsg = "The file already exists ; do you want to overwrite it ? (Y/N)";
	
	public SaveCommand() {
		super("save", "sa", "[SA]ve <filename>", "Save the state of the game to a file");
		this.fileName = null;
	}
	
	public SaveCommand(String[] other) {
		super("save", "s", "[S]ave <filename>", "Save the state of the game to a file");
		this.save = other;
	}
	
	
	public boolean execute(Game game)throws CommandExecuteException{
		if(!(this.save.length==1)) {
			this.fileName=save[1]+".dat";
			try {
				if(game.store(this.fileName))
					System.out.print("Game successfully saved in ﬁle "+this.fileName+"\n");
					return true;
			} catch (IOException e) {
			}
			return false;
		}
		else throw new CommandExecuteException("El comando necesita ir seguido del nombre del archivo a guardar");
	}

	public Command parse(String[] commandWords) throws CommandParseException {
		if(this.matchCommandName(commandWords[0])) 
			return new SaveCommand(commandWords);
		else
			return null;
	}
}
