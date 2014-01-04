package entity;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Background extends BasicBackground implements Entity {

	public Background(float x, float y, float z, int eid, float width,
			float height) throws IOException {
		super(x, y, z, eid, width, height);
	}

	public void render(float minX, float maxX, float minY, float maxY) {
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
		Color.white.bind();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		this.texture.bind(); // or GL11.glBind(texture.getTextureID());
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		GL11.glPushMatrix();
	
			// Replace glBeing with vertex buffer
		
		// The screen length relative to the screen placement
		float xLeftDenom = Math.abs(maxX - minX);
		// The amount of width to be displayed
		float xRightDenom = this.texture.getWidth() * (1/3f);
		// The position of the background relative to the left side of the screen
		float xLeftNum = Math.abs(this.getX() - minX);
		
		// The conversion of level coordinates to texture coordinates
		float xRightNum = xRightDenom*xLeftNum/xLeftDenom + this.texture.getWidth() * (1/3f);
		
		float yLeftDenom = Math.abs(maxY - minY);
		float yRightDenom = this.texture.getHeight() * (1/3f);
		float yLeftNum = Math.abs(this.getY() - minY);
		
		float yRightNum = yRightDenom*yLeftNum/yLeftDenom + this.texture.getHeight() * (1/3f);
		
			GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(xRightNum - this.texture.getWidth()/3,yRightNum - this.texture.getHeight()/3);
					GL11.glVertex2f(this.x - this.width/2, this.y - this.height/2);
					
					GL11.glTexCoord2f(xRightNum + this.texture.getWidth()/3,yRightNum - this.texture.getHeight()/3);
					GL11.glVertex2f(this.x + this.width/2, this.y - this.height/2);
					
					GL11.glTexCoord2f(xRightNum + this.texture.getWidth()/3,yRightNum + this.texture.getHeight()/3);
					GL11.glVertex2f(this.x + this.width/2, this.y + this.height/2);
					
					GL11.glTexCoord2f(xRightNum - this.texture.getWidth()/3,yRightNum + this.texture.getHeight()/3);
					GL11.glVertex2f(this.x - this.width/2, this.y + this.height/2);
			GL11.glEnd();
			
		GL11.glPopMatrix();
		
		GL11.glDisable(GL11.GL_BLEND);
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
}
