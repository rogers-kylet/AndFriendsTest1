package Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import entity.BasicBackground;
import entity.enemy.BasicFlyingEnemy;
import entity.BasicPickup;
import entity.Entity;
import room.AnchorPoint;
import room.BasicRoom;
import room.Room;
import room.roomConfigurations.template.EndRoom;
import room.roomConfigurations.template.RoomOne;
import room.roomConfigurations.template.RoomTwo;
import room.roomConfigurations.template.StartRoom;

public class LevelGeneration {

	/**
	 * Temp Logic in order to play around with room and level generation
	 * @param levelName The Name of the Level being generated
	 * @param levelNumber Which level this is for the player (1st 2nd etc...)
	 * @return The level
	 * @throws IOException 
	 */
	public static Level generateLevel(String levelName, int levelNumber) throws IOException {
		Level theLevel = new BasicLevel();
		theLevel.setBackgroundMusic(levelName);
		theLevel.setName(levelName);
		theLevel.setType("Gameplay");
		
		List<Room> roomList = new ArrayList<Room>();
		Boolean levelGenerated = false;
		int roomCount = 0;
		
		Queue<Room> theQueue = new LinkedList<Room>();
		
		Room startRoom = new BasicRoom(new StartRoom(400,300, ""));
		theQueue.add(startRoom);
		roomList.add(startRoom);
		
		//TODO exract the room creation to a different method, change those hardcoded values, a lot of this will be dependent on how we store the rooms
		// We can probably just give the location of the anchor point and the direction and let the room know that it needs to add/subject length/width/2 accordingly. 
		while(!levelGenerated) {

			Room tempRoom = theQueue.poll();
			List<AnchorPoint> anchorPoints = tempRoom.getAnchorPoints();

			Collections.shuffle(anchorPoints);
			
			// TODO need to refactor to use anchor points to place the rooms, meaning we need to generate them but without their x/y coordinates and then set them
			for(AnchorPoint anchorPoint : anchorPoints){
				if(!anchorPoint.isHooked()) {
					Room nextRoom;
					if(roomCount >= 10 + levelNumber) {
						nextRoom = new BasicRoom(new EndRoom(anchorPoint.getX(),anchorPoint.getY(), anchorPoint.getDirection()));
					} else {
						// TODO temp logic just to get two rooms in, soon will need to get a list, at least it's the same call so it'll be alright
						if((int)(Math.random()*2) == 0){
							nextRoom = new BasicRoom(new RoomOne(anchorPoint.getX(),anchorPoint.getY(), anchorPoint.getDirection()));
						} else {
							nextRoom = new BasicRoom(new RoomTwo(anchorPoint.getX(),anchorPoint.getY(), anchorPoint.getDirection()));
						}
					}
					boolean roomOkay = true;
					for(Room room: roomList) {
						if(room.roomCollision(nextRoom)) { roomOkay = false; }
					}
					if(roomOkay) {
						anchorPoint.setHooked(true);
						nextRoom.setParentRoom(tempRoom);
						roomCount++;
						roomList.add(nextRoom);
						theQueue.add(nextRoom);
						break;
					} else { continue; }
				} else { continue; }
			}
			
			// Temp number to allow for basic level scaling
			if(roomCount >= 10+levelNumber) { levelGenerated = true; }
		}
		for(Room room : roomList) { room.generateWalls(); }
		theLevel.setRoomList(roomList);
		
		Float minX = null, minY = null, maxX = null, maxY = null;
		
		//TODO add this into the other loop
		// Get the max/min x and y values for the rooms
		for(Room room: roomList) {
			if(minX == null) { minX = room.getX(); }
			if(minY == null) { minY = room.getY(); }
			if(maxX == null) { maxX = room.getX(); }
			if(maxY == null) { maxY = room.getY(); }
			
			if(room.getX() - room.getWidth()/2 < minX) {
				minX = room.getX() - room.getWidth()/2;
			}
			
			if(room.getX() + room.getWidth()/2 > maxX) {
				maxX = room.getX() + room.getWidth()/2;
			}
			
			if(room.getY() - room.getHeight()/2 < minY) {
				minY = room.getY() - room.getHeight()/2;
			}
			
			if(room.getY() + room.getHeight()/2 > maxY) {
				maxY = room.getY() + room.getHeight()/2;
			}
		}
		theLevel.setxMin(minX);
		theLevel.setxMax(maxX);
		theLevel.setyMin(minY);
		theLevel.setyMax(maxY);
		return theLevel;
	}
	
}
