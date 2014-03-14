package room.roomConfigurations.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.BasicBackground;
import entity.Entity;
import room.AnchorPoint;
import room.roomConfigurations.BasicRoomConfiguration;

//TODO use these to hold item/background/enemy placement/whatever else is in a room in order to generate this type of room. 
//All need to extend an interface so that the level generator can use them
public class StartRoom extends BasicRoomConfiguration {

	public StartRoom(float x, float y, String direction) throws IOException {
		this.width = 1000f;
		this.height = 800f;
		this.rotation = 0;
		this.type = "start";
		
		// Offset x and y based on the direction
		if(direction.equals("up")) {
			this.x = x;
			this.y = y - this.height/2;
		} else if (direction.equals("down")) {
			this.x = x;
			this.y = y + this.height/2;
		} else if (direction.equals("left")) {
			this.x = x - this.width/2;
			this.y = y;
		} else if (direction.equals("right")) {
			this.x = x + this.width/2;
			this.y = y;
		} else {
			this.x = x;
			this.y = y;
		}
		
		// Create obstacles for the room
		List<Entity> backgroundList = new ArrayList<Entity>();
			Entity background = new BasicBackground(this.x,this.y,0,0, this.width, this.height);
			backgroundList.add(background);
		this.setBackground(backgroundList);
		
		// Create anchor points for the room
		List<AnchorPoint> anchorPoints = new ArrayList<AnchorPoint>();
		AnchorPoint point1 = new AnchorPoint();
			point1.setX(this.x - this.width/2);
			point1.setY(this.y);
			point1.setDirection("left");
			if(direction.equals("right")) {
				point1.setHooked(true);
			}
			anchorPoints.add(point1);
		AnchorPoint point2 = new AnchorPoint();
			point2.setX(this.x+this.width/2);
			point2.setY(this.y);
			point2.setDirection("right");
			if(direction.equals("left")) {
				point2.setHooked(true);
			}
			anchorPoints.add(point2);
		AnchorPoint point3 = new AnchorPoint();
			point3.setX(this.x);
			point3.setY(this.y-this.height/2);
			point3.setDirection("up");
			if(direction.equals("down")) {
				point3.setHooked(true);
			}
			anchorPoints.add(point3);
		AnchorPoint point4 = new AnchorPoint();
			point4.setX(this.x);
			point4.setY(this.y+this.height/2);
			point4.setDirection("down");
			if(direction.equals("up")) {
				point4.setHooked(true);
			}
			anchorPoints.add(point4);
		this.setAnchorPoints(anchorPoints);
		
		this.setPickupList(new ArrayList<Entity>());
		this.setEnemyList(new ArrayList<Entity>());

	}
}
