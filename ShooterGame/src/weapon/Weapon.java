package weapon;

import java.io.IOException;
import java.util.List;

import entity.Entity;

public interface Weapon {

	List<Entity> attack(float angle, Entity player) throws IOException;

	String getName();

	void setName(String name);

	void setDamage(float damage);

	int getNumOfShots();

	void setNumOfShots(int numOfShots);

	float getDamage();

	String getSfxName();

	void setSfxName(String sfxName);

}
