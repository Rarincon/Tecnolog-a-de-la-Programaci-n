/*package tp.p1.objects;


import tp.p1.board.Game;
import tp.p1.exception.FileContentsException;
import tp.p1.exception.FileContentsVerifier;
import tp.p1.objects.ships.*;
import tp.p1.objects.weapons.*;

public class GameObjectGenerator {
	
	private static GameObject[] availableGameObjects = {
			new UCMShip(),
			new Ovni(),
			new RegularShip(),
			new DestroyerShip(),
			new ExplosiveShip(),
			new Shockwave(),
			new Proyectil(),
			new Missile(),
			new Supermisil()
		};
	
	public static GameObject parse(String stringFromFile, Game game, FileContentsVerifier veriﬁer) throws FileContentsException { 
		GameObject gameObject = null;
		for (GameObject go: availableGameObjects) {
			gameObject = go.parse(stringFromFile, game, veriﬁer);
			if (gameObject != null) break;
		}
		return gameObject;
	}
}*/
