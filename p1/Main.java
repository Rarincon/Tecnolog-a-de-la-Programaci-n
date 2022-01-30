package tp.p1;

import java.util.Random;
import tp.p1.board.Game;
import tp.p1.board.Level;


public class Main {
	
	public static void main(String[] args) { 
		Level level =null;
		Random rand = new Random();
		int seed;
		try {
			if (args.length > 0) {
				level = Level.createLevel(args[0]);
				
					if (args.length == 2) {
						seed = Integer.parseInt(args[1]);
						rand.setSeed(seed);
					}
				
			}
	
			if (level != null) {
				
				Game game = new Game(level, rand);
				
				Controller controller = new Controller(game);
	
				controller.run();
			} else {
				System.out.println("The level doesn't exist");
			}
		}
		catch(NumberFormatException ex) {
			System.err.format("La semilla no es valida" + "%n%n");
		}
	}

}	
