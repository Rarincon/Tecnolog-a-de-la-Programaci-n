package tp.p1.board;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.util.Random;

import tp.p1.interfaces.IPlayerController;
import tp.p1.objects.GameObject;
import tp.p1.objects.GameObjectBoard;
import tp.p1.objects.ships.*;
import tp.p1.objects.ships.UCMShip;
import tp.p1.objects.weapons.Shockwave;

public class Game implements IPlayerController{
	public static final int DIM_X=9;
	public static final int DIM_Y=8;
	
	GameObjectBoard board;
	private UCMShip player;
	
	private Random rand;
	private Level level;
	BoardInitializer initializer;
	private int currentCycle;
	
	private boolean exit;
	private boolean Shockwave;
	

	public Game (Level level, Random random){ 
		this.rand = random;
		this.level = level; 
		initializer = new BoardInitializer(); 
		
		initGame();
		
		this.exit=false;
		this.Shockwave=false;
		
	}
	
	public void initGame() { 
		currentCycle = 0; 
		board = initializer.initialize(this, level);
		player = new UCMShip(this, DIM_X / 2, DIM_Y-1); 
		board.add(player); 
	}

	
	private String getLife() {
		return "Life: "+ this.player.getLive();
	}
	
	public String getCyclos() {
		return "Number cycles: " + this.currentCycle;
	}
	
	private String getPoints() {
		return "Points: "+ this.player.getPoints();
	}
	
	private String getRemainingAliens() {
		return "Remaining Aliens: "+ AlienShip.getCount();
	}
	
	private String getSuperMissils() {
		return "Super Missils: "+ player.getSuperMisil();
	}
	
	private String getShockWave() {
		if(Shockwave)
			return "Shockwave: SI";
		else
			return "Shockwave: NO";
	}
	
	
	public boolean Move() {
		if(this.currentCycle==0)
			return false;
		else
			return this.currentCycle% this.level.getNumCyclesToMoveOneCell()==0;
	}
	
	public boolean aliensWin() { 
		return !player.isAlive() || AlienShip.haveLanded(); 
	}
	
	private boolean playerWin () {
		return AlienShip.allDead();
	}
	
	public void update() {
		currentCycle += 1;
		board.computerAction();
		board.update(); 
	}

	public Random getRandom() {
		return this.rand;
	}

	public Level getLevel() {
		return this.level;
	}
	
	public boolean isOnBoard(int x, int y) { 
		if(x<this.DIM_X && x>=0 && y>=0 && y< this.DIM_Y)
			return true;
		return false;
	}
	
	public void addObject(GameObject obj) {
		this.board.add(obj);
	}
	
	public void changeObjects(GameObject first, GameObject second) {
		this.board.change(first, second);
	}
	
	public boolean getWinner() {
		return this.playerWin();
	}
	
	public boolean getFinish() {
		if(this.aliensWin() || this.playerWin())
			return true;
		else
			return false;
	}
	
	public void setExit() {
		this.exit=true;
	}
	
	public boolean getExit() {
		return this.exit;
	}
	
	public boolean buySuper() {
		return player.buySuper();
		
	}
	
	public boolean move(int numCells) {		
		return player.move(numCells);
	}

	public boolean shootLaser() {
		if(!player.shootSuperMisil()) {
			enableSuperMissile();
			return false;
		}
		else return true;
	}

	public boolean shootMissil() {
		if(!player.shootMisil()) {
			enableMissile();
			return false;
		}
		else return true;
	}
	
	public boolean shockWave() {
		if(Shockwave){
			this.Shockwave= false;
			//this.board.attackW();
			Shockwave shockwave =new Shockwave(this);
			this.addObject(shockwave);
			return true;
		}
		else {
			enableShockWave();
			return false;
		}
	}
	
	public void setShockwave() {
		Shockwave=true;
	}
	
	public boolean getShockwave() {
		return Shockwave;
	}

	public void receivePoints(int points) {
		player.receivePoints(points);
	}

	public void enableShockWave() {
		System.out.print("You can't use shockwave now");
		
	}

	public void enableMissile() {
		System.out.print("You can't shoot a missil right now");
	}
	
	public void enableSuperMissile() {
		System.out.print("You don't have super missils right now");
	}
	
	public void checkattacks(GameObject other) {
		board.checkAttacksExplosive(other);
		
	}
	
	public String toString() {
		return "\n"+this.getLife() +"\n"+ this.getCyclos() +"\n"+ this.getPoints()
		+"\n"+ this.getRemainingAliens()+"\n"+ this.getShockWave()+"\n"+ this.getSuperMissils()+"\n";
		}
	
	public String list() {
		return System.lineSeparator()+ "[R]egular ship: Points: 5 - Harm: 0 - Shield: 2 "+System.lineSeparator() 
		+"[D]estroyer ship: Points: 10 - Harm: 1 - Shield: 1"+System.lineSeparator()
		+"[O]vni: Points: 25 - Harm: 0 - Shield: 1"+System.lineSeparator()
		+"^__^: Harm: 1 - Shield: "+this.player.getLive()+ System.lineSeparator();
	}
	
	public void reset() {
		initGame();
	}
	
	
	public boolean store(String fileName) throws IOException{
		FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        boolean t = false;
     
        try {
        	bw.write(this.stringify());
			t = true;
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			
			bw.close();
			fw.close();
			System.err.format("No se pudo guardar correctamente el juego" + "%n%n");
		}
        return t;
        
        
	}
	
	public int Cycles_move() {
		if(this.currentCycle%this.level.getNumCyclesToMoveOneCell()==0)
			return this.level.getNumCyclesToMoveOneCell()-1;
		else if(this.currentCycle%this.level.getNumCyclesToMoveOneCell()==1)
			return 1;
		else
			return 0;
	}

	public String stringify(){
		String c;
		c= "Space Invaders v2.0 "+System.lineSeparator()+System.lineSeparator()+"G: "+ this.currentCycle+System.lineSeparator()
		+"L: "+this.level.name()+System.lineSeparator()+board.stringify();
		return c;
	}
}