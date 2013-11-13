package weapon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.BasicProjectile;
import entity.Entity;

public class DoubleShot extends BasicWeapon {
	public DoubleShot(){
		this.name = "doubleShot";
		this.damage = 1;
		this.numOfShots = 2;
		this.sfxName = "shot";
	}
	
	@Override
	public List<Entity> attack(float angle, Entity player) throws IOException {
		List<Entity> bulletList = new ArrayList<Entity>();
		BasicProjectile bullet = new BasicProjectile(player.getX() + 2 * (float)Math.cos(Math.toRadians(angle + 90)), player.getY() + 2 * (float)Math.cos(Math.toRadians(angle + 90)), 0f, 1);
		bullet.setAngle(angle);
		bulletList.add(bullet);
		
		BasicProjectile bullet2 = new BasicProjectile(player.getX() + 2 * (float)Math.cos(Math.toRadians(angle - 90)), player.getY() + 2 * (float)Math.cos(Math.toRadians(angle - 90)), 0f, 1);
		bullet2.setAngle(angle);
		bulletList.add(bullet2);

		return bulletList;
	}
}
