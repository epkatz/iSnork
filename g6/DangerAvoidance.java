package isnork.g6;

import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class DangerAvoidance {

	public boolean isLocationDangerous(Set<Observation> whatISee, Point2D pos) {
		Iterator<Observation> itr = whatISee.iterator();
		while (itr.hasNext()) {
			Observation o = itr.next();
			if (o.isDangerous()) {
				if (tilesAway(pos, o.getLocation()) <= 2) {
					return true;
				}
			}
		}
		return false;
	}

	public Direction bestDirection(Set<Observation> whatISee, Direction d, Point2D currentPosition) {
		ArrayList<Direction> directionOptions = Direction.allBut(d);
		Point2D bestPoint = null;
		for (Direction nextD : directionOptions) {
			double newPosX = currentPosition.getX() + nextD.getDx();
			double newPosY = currentPosition.getY() + nextD.getDy();
			Point2D newPoint = new Point2D.Double(newPosX, newPosY);
			if (!atBoat(newPoint) && !isLocationDangerous(whatISee, newPoint)){
				if tilesAway(newPoint)
			}
		}
		
	}

	public int tilesAway(Point2D me, Point2D them) {
		return (int) me.distance(them);
	}

	// public void alterPath(LinkedList<Node> path, Point2D destination,
	// PathManager pManager){
	// LinkedList<Node> startRoute
	// }

	public static boolean atBoat(Point2D p) {
		if (p.getX() == 0 && p.getY() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean atTheWall(Point2D p) {
		if (Math.abs(p.getX()) == NewPlayer.d || Math.abs(p.getY()) == NewPlayer.d) {
			return true;
		} else {
			return false;
		}
	}
}
