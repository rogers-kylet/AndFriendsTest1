package weapon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.BasicProjectile;
import entity.Entity;

public class CircleShot extends BasicWeapon {
	public CircleShot(){
		this.name = "CircleShot";
		this.damage = 1;
		this.numOfShots = 4;
		this.sfxName = "shot";
	}
	
	@Override
	public List<Entity> attack(float angle, Entity player) throws IOException {
		List<Entity> bulletList = new ArrayList<Entity>();
		BasicProjectile bullet = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet.setAngle(angle);
		bulletList.add(bullet);
		
		BasicProjectile bullet2 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet2.setAngle(angle + 90);
		bulletList.add(bullet2);
		
		BasicProjectile bullet3 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet3.setAngle(angle - 90);
		bulletList.add(bullet3);

		BasicProjectile bullet4 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet4.setAngle(angle + 180);
		bulletList.add(bullet4);
		
		BasicProjectile bullet5 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet5.setAngle(angle + 45);
		bulletList.add(bullet5);
		
		BasicProjectile bullet6 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet6.setAngle(angle - 45);
		bulletList.add(bullet6);
		
		BasicProjectile bullet7 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet7.setAngle(angle + 180 + 45);
		bulletList.add(bullet7);

		BasicProjectile bullet8 = new BasicProjectile(player.getX(), player.getY(), 0f, 1);
		bullet8.setAngle(angle + 180 - 45);
		bulletList.add(bullet8);


		return bulletList;
	}
}
