package tp.p1.command;

import java.util.Arrays;

import tp.p1.exception.CommandParseException;

public abstract class NoParamsCommand extends Command{ 
	
	public NoParamsCommand(String name, String shortName, String commandText, String helpText) {
		super(name, shortName, commandText, helpText);
	}
	
	
	public Command parse(String[] commandWords) {
		if(this.matchCommandName(commandWords[0]))
			return this;
		else
			return null;
	}
}
