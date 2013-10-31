package Level;

import java.util.ArrayList;
import java.util.List;

import Background.Background;

import entity.BasicBackground;
import entity.BasicEnemy;
import entity.BasicEntity;
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
	 */
	public static Level generateLevel(String levelName, int levelNumber) {
		Level theLevel = new BasicLevel();
		theLevel.setBackgroundMusic(levelName);
		theLevel.setName(levelName);
		theLevel.setType("Gameplay");
		
		List<Room> roomList = new ArrayList<Room>();
		Boolean levelGenerated = false;
		
		//TODO exract the room creation to a different method, change those hardcoded values, a lot of this will be dependent on how we store the rooms
		while(!levelGenerated) {
			Room tempRoom = buildRoom(400,300);
			roomList.add(tempRoom);
			tempRoom = buildRoom(1200,900);
			roomList.add(tempRoom);
			tempRoom = buildRoom(1200,300);
			roomList.add(tempRoom);
			tempRoom = buildRoom(2000, 900);
			roomList.add(tempRoom);
			tempRoom = buildRoom(2000, 1500);
			roomList.add(tempRoom);
			levelGenerated = true;
		}
		theLevel.setRoomList(roomList);
		return theLevel;
	}

	//TODO need to 
	public static Room buildRoom(float x, float y) {
		Room tempRoom = new BasicRoom();
			tempRoom.setX(x);
			tempRoom.setY(y);
			tempRoom.setRotation(0);
			tempRoom.setHeight(600);
			tempRoom.setWidth(800);
				List<Entity> enemyList = new ArrayList<Entity>();
				Entity enemy1 = new BasicEnemy(x + 350f,y + 250f,0f,0);
				Entity enemy2 = new BasicEnemy(x - 350f, y - 250f, 0f, 1);
				Entity enemy3 = new BasicEnemy(x + 350f, y - 250f, 0f, 2);
				Entity enemy4 = new BasicEnemy(x - 350f, y + 250f, 0f, 3);
				enemyList.add(enemy1);
				enemyList.add(enemy2);
				enemyList.add(enemy3);
				enemyList.add(enemy4);
			tempRoom.setEnemyList(enemyList);
			List<AnchorPoint> anchorPoints = new ArrayList<AnchorPoint>();
			AnchorPoint point1 = new AnchorPoint();
				point1.setX(x - 400f);
				point1.setY(y);
				anchorPoints.add(point1);
			AnchorPoint point2 = new AnchorPoint();
				point2.setX(x+400f);
				point2.setY(y);
				anchorPoints.add(point2);
			AnchorPoint point3 = new AnchorPoint();
				point3.setX(x);
				point3.setY(y-300f);
				anchorPoints.add(point3);
			AnchorPoint point4 = new AnchorPoint();
				point4.setX(x);
				point4.setY(y+300f);
				anchorPoints.add(point4);
			tempRoom.setAnchorPoints(anchorPoints);
			
			List<Entity> backgroundList = new ArrayList<Entity>();
			Entity background = new BasicBackground(x,y,0,0, tempRoom.getWidth(), tempRoom.getHeight());
			backgroundList.add(background);
			tempRoom.setBackground(backgroundList);
			
		return tempRoom;
	}
}