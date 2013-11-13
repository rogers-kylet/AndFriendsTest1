package weapon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.BasicProjectile;
import entity.Entity;

public class TripleShot extends BasicWeapon {

	public TripleShot(){
		this.name = "tripleShot";
		this.damage = 1;
		this.numOfShots = 3;
		this.sfxName = "shot";
	}
	
	@Override
	public List<Entity> attack(float angle, Entity player) throws IOException {
		List<Entity> bulletList = new ArrayList<Entity>();
		BasicProjectile bullet = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet.setAngle(angle);
		bulletList.add(bullet);
		
		BasicProjectile bullet2 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet2.setAngle(angle + 45);
		bulletList.add(bullet2);

		BasicProjectile bullet3 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet3.setAngle(angle - 45);
		bulletList.add(bullet3);

		return bulletList;
	}
}
