package tp.p1.command;

import java.util.Arrays;
import tp.p1.exception.CommandParseException;

public class CommandGenerator {

	private static Command[] availableCommands = {
		new ListCommand(),
		new HelpCommand(),
		new ResetCommand(),
		new ExitCommand(),
		new UpdateCommand(),
		new MoveCommand(),
		new ShockwaveCommand(),
		new ShootCommand(),
		new BuyCommand(),
		new StringifyCommand(),
		new ListPrintersCommand(),
		new SaveCommand()
	};
	
	public static Command parseCommand(String[] commandWords) throws CommandParseException{ 
		for (Command command : availableCommands) {
			Command parseCommand = command.parse(commandWords);
			if(parseCommand != null)
				return parseCommand;
		}
			throw new CommandParseException("El comando '"+ Arrays.toString(commandWords) +"' no existe");
	}
	
	public static String commandHelp() {
		String help = "The available commands are: " + System.getProperty("line.separator");
		for (Command c: availableCommands) 
			help += "   " + c.helpText() + System.getProperty("line.separator");
		return help;
	}
}
