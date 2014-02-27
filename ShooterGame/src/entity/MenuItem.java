package entity;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import room.Room;

public class MenuItem extends BasicEntity {

	private String buttonAction;
	
	public MenuItem(float x, float y, String textureName) throws IOException {
		this.x = x;
		this.y = y;
		this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("assets/images/" + textureName + ".png"));
		this.width = texture.getTextureWidth()/2;
		this.height = texture.getTextureHeight()/2;
		this.buttonAction = textureName;
	}
	@Override
	public void render() {
		Color.white.bind();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glPushMatrix();
	
			// Replace glBeing with vertex buffer
		
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
		
		GL11.glDisable(GL11.GL_BLEND);

	}

	@Override
	protected boolean processCollisionTick(Entity target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processMovementTick(Entity target) {
		// TODO Auto-generated method stub

	}
	
	public boolean mouseClick(float convertedX, float convertedY) {
		if(
				( convertedX < ( this.getX() + this.width / 2 ) ) && 
				( convertedX > ( this.getX() - this.width / 2 ) ) && 
				( convertedY > ( this.getY() - this.height / 2 ) ) && 
				( convertedY < ( this.getY() + this.height / 2 ) ) ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<Entity> attack(float angle) {
		return null;
	}
	
	public String getButtonAction(){ return this.buttonAction; }
	@Override
	public List<Entity> attack(Entity target) throws IOException { return null; }
	@Override
	public void setTexture(Texture texture) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void processMovementTick(Entity target, List<Room> entityList) {
		// TODO Auto-generated method stub
		
	}

}
