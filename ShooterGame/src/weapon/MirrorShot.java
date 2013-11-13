package weapon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.BasicProjectile;
import entity.Entity;

public class MirrorShot extends BasicWeapon {
	public MirrorShot(){
		this.name = "doubleShot";
		this.damage = 1;
		this.numOfShots = 2;
		this.sfxName = "shot";
	}
	
	@Override
	public List<Entity> attack(float angle, Entity player) throws IOException {
		List<Entity> bulletList = new ArrayList<Entity>();
		BasicProjectile bullet = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet.setAngle(angle);
		bulletList.add(bullet);
		
		BasicProjectile bullet2 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet2.setAngle(angle + 180);
		bulletList.add(bullet2);

		return bulletList;
	}
}
