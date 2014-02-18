package Engine;

import org.lwjgl.input.Keyboard;

public class Options {

	int up;
	int down;
	int left;
	int right;
	
	int jump;
	int shoot;
	int meleeAttack;
	
	// Default Constructor used when no options have been saved
	public Options() {
		this.up = Keyboard.KEY_W;
		this.down = Keyboard.KEY_S;
		this.left = Keyboard.KEY_A;
		this.right = Keyboard.KEY_D;
		
		this.jump = Keyboard.KEY_SPACE;
		this.shoot = Keyboard.KEY_K;
		this.meleeAttack = Keyboard.KEY_J;
	}
	
	public int getUp() { return up; }
	public void setUp(int up) { this.up = up; }
	public int getDown() { return down; }
	public void setDown(int down) { this.down = down; }
	public int getLeft() { return left; }
	public void setLeft(int left) { this.left = left; }
	public int getRight() { return right; }
	public void setRight(int right) { this.right = right; }
	public int getJump() { return jump; }
	public void setJump(int jump) { this.jump = jump; }
	public int getShoot() { return shoot; }
	public void setShoot(int shoot) { this.shoot = shoot; }
	public int getMeleeAttack() { return meleeAttack; }
	public void setMeleeAttack(int meleeAttack) { this.meleeAttack = meleeAttack; }
	
	
}
