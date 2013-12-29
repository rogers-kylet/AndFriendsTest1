package entity;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import ai.Ai;
import ai.BasicAi;

import entityAttack.BasicEntityAttack;
import entityMovement.BasicEntityMovement;

import room.Room;

public class BasicEnemy extends BasicEntity {

	private Texture texture;
	int mask;
	Ai ai;
	
	public BasicEnemy(float x, float y, float z, int eid) throws IOException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.acceleration = new Vector3f(0,0,0);
		this.angle=90;
		this.baseHealth=5f;
		this.health=1f;
		this.height=50f;
		this.width=50f;
		this.entityType=entityClass.HOSTILE;
		this.maxHealth=10f;
		this.maxSpeed=3f;
		this.speed=3f;
		this.minimumSpeed=3f;
		this.name="Unnamed Enemy "+eid;
		this.rotation=0f;
		this.rotationSpeed=0f;
		this.scale=1f;
		this.eid = eid;
		this.hitSfx = "enemyhit";
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + "Enemy" + ".png"));
		this.mask = (int) Math.floor(Math.random() * 9);
		this.ai = new BasicAi(new BasicEntityMovement(), new BasicEntityAttack());
	}
	
	@Override
	public void render() {
		
		switch(mask) {
		case 1:
			Color.white.bind();
			break;
		case 2:
			Color.blue.bind();
			break;
		case 3:
			Color.cyan.bind();
			break;
		case 4:
			Color.green.bind();
			break;
		case 5:
			Color.magenta.bind();
			break;
		case 6:
			Color.orange.bind();
			break;
		case 7:
			Color.pink.bind();
			break;
		case 8:
			Color.red.bind();
			break;
		case 9:
			Color.yellow.bind();
			break;
		default:
			Color.white.bind();
			break;
		}
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		GL11.glPushMatrix();
	
			// Replace glBegin with vertex buffer
			GL11.glTranslatef(this.x,this.y,0);
			GL11.glRotatef(this.rotation, 0f, 0f, 1f);
			GL11.glTranslatef(-this.x, -this.y, 0);
		
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0,0);
				GL11.glVertex2f(this.x - this.width/2, this.y - this.height/2);
				
				GL11.glTexCoord2f(this.texture.getWidth(),0);
				GL11.glVertex2f(this.x + this.width/2, this.y - this.height/2);
				
				GL11.glTexCoord2f(this.texture.getWidth(),this.texture.getHeight());
				GL11.glVertex2f(this.x + this.width/2, this.y + this.height/2);
				
				GL11.glTexCoord2f(0,this.texture.getHeight());
				GL11.glVertex2f(this.x - this.width/2, this.y + this.height/2);
			GL11.glEnd();
			
		GL11.glPopMatrix();
		
	}

	@Override
	protected boolean processCollisionTick(Entity target) {
		if(
				( target.getX() - target.getWidth() / 2 < ( this.getX() + this.width / 2 ) ) && 
				( target.getX() + target.getWidth() / 2 > ( this.getX() - this.width / 2 ) ) && 
				( target.getY() + target.getHeight() / 2 > ( this.getY() - this.height / 2 ) ) && 
				( target.getY() - target.getHeight() / 2 < ( this.getY() + this.height / 2 ) ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void processMovementTick(Entity target) {
		this.ai.move(this, target);
	}
	
	@Override
	public void processMovementTick(Entity target, List<Room> entityList) {
		this.ai.move(this, target, entityList);
	}

	@Override
	public List<Entity> attack(float angle) {
		return null;
	}
	
	@Override
	public List<Entity> attack(Entity target) throws IOException  {
		return this.ai.attack(this, target);
	}

	@Override
	public void setTexture(Texture texture) {
		// TODO Auto-generated method stub
		
	}
	

}
