package tp.p1.command;

import java.util.Objects;

import tp.p1.board.Game;
import tp.p1.exception.CommandExecuteException;

public class ShootCommand extends Command{
	private String[] shooter;
	
	public ShootCommand() {
		super("shoot","s","[S]hoot<[M]issile/[SUP]ermissile>", "Shoot a missile or a super missile");
	}
	
	public ShootCommand(String[] other) {
		super("shoot","s","[S]hoot<[M]issile/[SUP]ermissile>", "Shoot a missile or a super missile");
		shooter=other;
	}


	public boolean execute(Game game) throws CommandExecuteException {
		if(shooter.length==2) {
			if((Objects.equals("missile",shooter[1]))||(Objects.equals("m",shooter[1]))){ 
				if(game.shootMissil()) {
					game.update();
					return true;
				}
				else 
					throw new CommandExecuteException("\nNo puedes disparar un misil ahora");
					
			}
			else if((Objects.equals("supermissile",shooter[1]))||(Objects.equals("sup",shooter[1]))){
				if(game.shootLaser()) {
					game.update();
					return true;
				}
				else 
					throw new CommandExecuteException("\nNo puedes disparar un supermisil ahora");
					
			}
			else 
				throw new CommandExecuteException("El comando '"+ shooter[1] +"' no existe");
		}
		else
			throw new CommandExecuteException("El comando esta incompleto");
	}

	public Command parse(String[] commandWords) {
		if(this.matchCommandName(commandWords[0]))
			return new ShootCommand(commandWords);
		else
			return null;
	}

}
