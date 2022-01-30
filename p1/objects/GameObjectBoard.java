package tp.p1.objects;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

public class GameObjectBoard {
	private GameObject[] objects;
	private int currentObjects;
	
	
	
	public GameObjectBoard (int width, int height) {
		objects = new GameObject[width*height];
		this.currentObjects=0;
	}
	
	private int getCurrentObjects () {
		return this.currentObjects;
	}
	
	public void add (GameObject object) {
		objects[this.getCurrentObjects()]= object;
		this.currentObjects++;
	}
	
	public void change(GameObject first, GameObject second) {
		int pos=this.getIndex(first.getX(), second.getY());
		objects[pos]=second;
	}
	
	private GameObject getObjectInPosition (int x, int y) {
		int pos=getIndex(x,y);
		if(pos>=0)
			return objects[pos];
		else
			return null;
	}
	
	private int getIndex(int x, int y) {
		for(int i=currentObjects-1;i>=0;i--) 
			if(objects[i].getX()==x && objects[i].getY()==y)
				return i;
		return -1;
	}
	
	private void remove (GameObject object) {
		int pos=getIndex(object.getX(),object.getY());
		if(pos>=0) {
			for(int i=pos;i<this.currentObjects;i++) 
				this.objects[i]=this.objects[i+1];
			this.currentObjects--;
		}
	}
	
	//Original
	public void update() {
		for(int i=0;i<this.currentObjects;i++) {
				this.objects[i].move();
				
			}
		for(int i=0;i<this.currentObjects;i++)
			checkAttacks(objects[i]);
		removeDead();
	}
	
	
	/*public void update() {
		for(int i=0;i<this.currentObjects;i++) {
				//checkAttacks(objects[i]);
				this.objects[i].move();
				checkAttacks(objects[i]);	
			}
	}*/

	public void checkAttacks(GameObject object) {
		for(int i=0;i< this.currentObjects;i++) {
			if(objects[i]!= object)
				objects[i].performAttack(object);

		}
		//removeDead();
	}
	
	public void checkAttacksExplosive(GameObject object) {
		for(int i=0;i< this.currentObjects;i++) {
			if(objects[i]!= object) {
				object.performAttackExplosive(objects[i]);
			}
		}
	}
	
	public void computerAction() {
		for(int i=0;i< this.currentObjects;i++) {
			objects[i].computerAction();
		}
	}
	
	
	private void removeDead() {
		for(int i=this.currentObjects-1;i>=0;i--) {
			if(!this.objects[i].isAlive()) { 
				this.objects[i].onDelete(); 
				this.remove(objects[i]);
			}
		}
		for(int i=this.currentObjects-1;i>=0;i--) { //Por el explosive, para que no se salte nada
			if(!this.objects[i].isAlive()) { 
				this.objects[i].onDelete(); 
				this.remove(objects[i]);
			}
		}
	}
	
	public String toString(int x, int y) {
		GameObject aux;
		aux=this.getObjectInPosition(x, y);
		if(aux!=null)
			return aux.toString();
		else
			return " ";
	}
	

	public String stringify() {
		String total="";
		for (int i = 0; i < this.currentObjects; i++) {
			total+=objects[i].stringify()+System.lineSeparator();
		}
		return total;
	}
	
	/*
	public void attackW() {
		for (int i = 0; i < this.currentObjects; i++) {
			objects[i].receiveShockWaveAttack(1);
		}
		removeDead();
	}*/
	
	
}
