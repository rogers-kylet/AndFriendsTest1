package Level;

import java.util.ArrayList;
import java.util.List;

import entity.BasicEnemy;
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
	public Level generateLevel(String levelName, int levelNumber) {
		Level theLevel = new BasicLevel();
		theLevel.setBackgroundMusic(levelName);
		theLevel.setName(levelName);
		theLevel.setType("Gameplay");
		
		List<Room> roomList = new ArrayList<Room>();
		Room tempRoom = new BasicRoom();
			tempRoom.setX(400);
			tempRoom.setY(300);
			tempRoom.setRotation(0);
			tempRoom.setHeight(600);
			tempRoom.setWidth(800);
				List<Entity> enemyList = new ArrayList<Entity>();
				Entity enemy1 = new BasicEnemy(750f,550f,0f,0);
				Entity enemy2 = new BasicEnemy(50f, 50f, 0f, 1);
				Entity enemy3 = new BasicEnemy(750f, 50f, 0f, 2);
				Entity enemy4 = new BasicEnemy(50f, 550f, 0f, 3);
				enemyList.add(enemy1);
				enemyList.add(enemy2);
				enemyList.add(enemy3);
				enemyList.add(enemy4);
			tempRoom.setEnemyList(enemyList);
			List<AnchorPoint> anchorPoints = new ArrayList<AnchorPoint>();
			AnchorPoint point1 = new AnchorPoint();
				point1.setX(0f);
				point1.setY(300f);
				anchorPoints.add(point1);
			AnchorPoint point2 = new AnchorPoint();
				point2.setX(800);
				point2.setY(300f);
				anchorPoints.add(point2);
			AnchorPoint point3 = new AnchorPoint();
				point3.setX(400f);
				point3.setY(0f);
				anchorPoints.add(point3);
			AnchorPoint point4 = new AnchorPoint();
				point4.setX(400f);
				point4.setY(600f);
				anchorPoints.add(point4);
			tempRoom.setAnchorPoints(anchorPoints);
		roomList.add(tempRoom);
		return theLevel;
	}
}
