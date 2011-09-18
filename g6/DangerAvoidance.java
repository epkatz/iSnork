package isnork.g6;

import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
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

	public LinkedList<Direction> bestDirections(Set<Observation> whatISee,
			Direction d, Point2D currentPosition) {
		LinkedList<Direction> newL = new LinkedList<Direction>();
		ArrayList<Direction> directionOptions = Direction.allBut(d);
		ArrayList<Direction> possibleSafePlaces = new ArrayList<Direction>();
		for (Direction nextD : directionOptions) {
			double newPosX = currentPosition.getX() + nextD.getDx();
			double newPosY = currentPosition.getY() + nextD.getDy();
			Point2D newPoint = new Point2D.Double(newPosX, newPosY);
			if (!atTheWall(newPoint) && !isLocationDangerous(whatISee, newPoint)) {
					possibleSafePlaces.add(nextD);
			}
		}
		if (possibleSafePlaces.isEmpty()) {
			Direction randomDirection = null;
			do {
				Random r = new Random();
				randomDirection = directionOptions.get(r.nextInt(Direction.values().length - 1));
			} while (randomDirection == Direction.STAYPUT);
			newL.add(randomDirection);
			double newPosX = currentPosition.getX() + randomDirection.getDx();
			double newPosY = currentPosition.getY() + randomDirection.getDy();
			Point2D randomPoint = new Point2D.Double(newPosX, newPosY);
			LinkedList<Direction> temp = bestDirections(whatISee,
					randomDirection, randomPoint);
			for (Direction tmpD : temp) {
				newL.add(tmpD);
			}
			return newL;
		} else {
			Direction randomDirection = null;
			if (possibleSafePlaces.size() == 1){
				newL.add(possibleSafePlaces.remove(0));
				return newL;
			}
			do {
				Random r = new Random();
				randomDirection = directionOptions.get(r.nextInt(possibleSafePlaces.size()));
			} while (randomDirection == Direction.STAYPUT);
			newL.add(randomDirection);
			return newL;
		}
	}

	public int tilesAway(Point2D me, Point2D them) {
		return ((int) PathManager.computeTotalSpaces(me, them));
	}

	public static boolean atBoat(Point2D p) {
		if (p.getX() == 0 && p.getY() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean atTheWall(Point2D p) {
		if (Math.abs(p.getX()) == NewPlayer.d
				|| Math.abs(p.getY()) == NewPlayer.d) {
			return true;
		} else {
			return false;
		}
	}
}
