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
		
		Room startRoom = buildStartRoom(400,300);
		theQueue.add(startRoom);
		roomList.add(startRoom);
		
		//TODO exract the room creation to a different method, change those hardcoded values, a lot of this will be dependent on how we store the rooms
		while(!levelGenerated) {

			Room tempRoom = theQueue.poll();
			List<AnchorPoint> anchorPoints = tempRoom.getAnchorPoints();

			Collections.shuffle(anchorPoints);
			
			// TODO need to refactor to use anchor points to place the rooms, meaning we need to generate them but without their x/y coordinates and then set them
			for(AnchorPoint anchorPoint : anchorPoints){
				if(!anchorPoint.isHooked()) {
					Room nextRoom = null;
					if(anchorPoint.getDirection().equals("left")){
						nextRoom = buildRoom(anchorPoint.getX()-tempRoom.getWidth()/2,anchorPoint.getY());
						for(AnchorPoint hookedAnchorPoint: nextRoom.getAnchorPoints()) {
							if(hookedAnchorPoint.getDirection().equals("right")) { hookedAnchorPoint.setHooked(true); }
						}
					} else if(anchorPoint.getDirection().equals("right")){
						nextRoom = buildRoom(anchorPoint.getX() + tempRoom.getWidth()/2,anchorPoint.getY());
						for(AnchorPoint hookedAnchorPoint: nextRoom.getAnchorPoints()) {
							if(hookedAnchorPoint.getDirection().equals("left")) { hookedAnchorPoint.setHooked(true); }
						}
					} else if(anchorPoint.getDirection().equals("up")){
						nextRoom = buildRoom(anchorPoint.getX(),anchorPoint.getY() - tempRoom.getHeight()/2);
						for(AnchorPoint hookedAnchorPoint: nextRoom.getAnchorPoints()) {
							if(hookedAnchorPoint.getDirection().equals("down")) { hookedAnchorPoint.setHooked(true); }
						}
					} else if(anchorPoint.getDirection().equals("down")){
						nextRoom = buildRoom(anchorPoint.getX(),anchorPoint.getY() + tempRoom.getHeight()/2);
						for(AnchorPoint hookedAnchorPoint: nextRoom.getAnchorPoints()) {
							if(hookedAnchorPoint.getDirection().equals("up")) { hookedAnchorPoint.setHooked(true); }
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
						if(roomCount >= 10+levelNumber) {
							BasicBackground tempBack = (BasicBackground) nextRoom.getBackground().get(0);
							tempBack.setTexture("endRoom");
							BasicPickup endDoor = new BasicPickup(nextRoom.getX(), nextRoom.getY(), 0, 0, 40, 40);
							endDoor.setPickupType("end");
							nextRoom.setType("end");
							List<Entity> pickUpList = new ArrayList<Entity>();
							pickUpList.add(endDoor);
							nextRoom.setPickupList(pickUpList);
						}
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

	//TODO the given x and y only work for rooms that are the same size, need to update for various sizes
	public static Room buildRoom(float x, float y) throws IOException {
		Room tempRoom = new BasicRoom();
			tempRoom.setX(x);
			tempRoom.setY(y);
			tempRoom.setRotation(0);
			tempRoom.setHeight(800);
			tempRoom.setWidth(1000);
			tempRoom.setType("normal");
				List<Entity> enemyList = new ArrayList<Entity>();
				Entity enemy1 = new BasicFlyingEnemy(x + 350f,y + 250f,0f,0);
				Entity enemy2 = new BasicFlyingEnemy(x - 350f, y - 250f, 0f, 1);
				Entity enemy3 = new BasicFlyingEnemy(x + 350f, y - 250f, 0f, 2);
				Entity enemy4 = new BasicFlyingEnemy(x - 350f, y + 250f, 0f, 3);
				enemyList.add(enemy1);
				enemyList.add(enemy2);
				enemyList.add(enemy3);
				enemyList.add(enemy4);
			tempRoom.setEnemyList(enemyList);
			List<AnchorPoint> anchorPoints = new ArrayList<AnchorPoint>();
			AnchorPoint point1 = new AnchorPoint();
				point1.setX(x - tempRoom.getWidth()/2);
				point1.setY(y);
				point1.setDirection("left");
				anchorPoints.add(point1);
			AnchorPoint point2 = new AnchorPoint();
				point2.setX(x+tempRoom.getWidth()/2);
				point2.setY(y);
				point2.setDirection("right");
				anchorPoints.add(point2);
			AnchorPoint point3 = new AnchorPoint();
				point3.setX(x);
				point3.setY(y-tempRoom.getHeight()/2);
				point3.setDirection("up");
				anchorPoints.add(point3);
			AnchorPoint point4 = new AnchorPoint();
				point4.setX(x);
				point4.setY(y+tempRoom.getHeight()/2);
				point4.setDirection("down");
				anchorPoints.add(point4);
			tempRoom.setAnchorPoints(anchorPoints);
			tempRoom.setPickupList(new ArrayList<Entity>());
			List<Entity> backgroundList = new ArrayList<Entity>();
			Entity background = new BasicBackground(x,y,0,0, tempRoom.getWidth(), tempRoom.getHeight());
			backgroundList.add(background);
			tempRoom.setBackground(backgroundList);
			
		return tempRoom;
	}
	
	public static Room buildStartRoom(float x, float y) throws IOException {
		Room tempRoom = new BasicRoom();
			tempRoom.setX(x);
			tempRoom.setY(y);
			tempRoom.setRotation(0);
			tempRoom.setHeight(800);
			tempRoom.setWidth(1000);
			tempRoom.setType("start");
			tempRoom.setEnemyList(new ArrayList<Entity>());
			
			List<AnchorPoint> anchorPoints = new ArrayList<AnchorPoint>();
			AnchorPoint point1 = new AnchorPoint();
				point1.setX(x - tempRoom.getWidth()/2);
				point1.setY(y);
				point1.setDirection("left");
				anchorPoints.add(point1);
			AnchorPoint point2 = new AnchorPoint();
				point2.setX(x+tempRoom.getWidth()/2);
				point2.setY(y);
				point2.setDirection("right");
				anchorPoints.add(point2);
			AnchorPoint point3 = new AnchorPoint();
				point3.setX(x);
				point3.setY(y-tempRoom.getHeight()/2);
				point3.setDirection("up");
				anchorPoints.add(point3);
			AnchorPoint point4 = new AnchorPoint();
				point4.setX(x);
				point4.setY(y+tempRoom.getHeight()/2);
				point4.setDirection("down");
				anchorPoints.add(point4);
			tempRoom.setAnchorPoints(anchorPoints);
			tempRoom.setPickupList(new ArrayList<Entity>());
			List<Entity> backgroundList = new ArrayList<Entity>();
			BasicBackground background = new BasicBackground(x,y,0,0, tempRoom.getWidth(), tempRoom.getHeight());
			background.setTexture("startRoom");
			backgroundList.add(background);
			tempRoom.setBackground(backgroundList);
			
		return tempRoom;
	}
}
