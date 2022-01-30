package tp.p1.interfaces;

import tp.p1.objects.GameObject;

public interface IAttack {
	default boolean performAttack(GameObject other) {
		return false;
	};
	
	default boolean receiveMissileAttack(int damage) {
		return false;
	};
	
	default boolean receiveBombAttack(int damage) {
		return false;
	};
	
	default boolean receiveShockWaveAttack(int damage) {
		return false;
	};
	

	
	default boolean receiveSuperMissileAttack(int damage) {
		return false;
	};
	
	default boolean receiveExplosiveAttack(int damage) {
		return false;
	};
	
	default void setWeapon() {
		
	};
	
	default boolean performAttackExplosive(GameObject other) {
		return false;
	};
}
