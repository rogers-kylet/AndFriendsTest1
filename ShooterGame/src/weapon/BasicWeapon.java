package weapon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.BasicProjectile;
import entity.Entity;

public class BasicWeapon implements Weapon {

	String name;
	
	float damage;
	
	int numOfShots;
	
	String sfxName;
	
	public BasicWeapon(){
		this.name = "shot";
		this.damage = 1;
		this.numOfShots = 1;
		this.sfxName = "shot";
	}
	@Override
	public List<Entity> attack(float angle, Entity player) throws IOException {
		List<Entity> bulletList = new ArrayList<Entity>();
		BasicProjectile bullet = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet.setAngle(angle);
		bulletList.add(bullet);
		return bulletList;
	}

	@Override
	public String getName() { return name; }

	@Override
	public void setName(String name) { this.name = name; }

	@Override
	public float getDamage() { return damage; }

	@Override
	public void setDamage(float damage) { this.damage = damage; }

	@Override
	public int getNumOfShots() { return numOfShots; }

	@Override
	public void setNumOfShots(int numOfShots) { this.numOfShots = numOfShots; }

	@Override
	public String getSfxName() { return sfxName; }

	@Override
	public void setSfxName(String sfxName) { this.sfxName = sfxName; }
	
}
