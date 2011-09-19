package isnork.g6;

import isnork.sim.GameEngine;
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
			Point2D newPoint = getPointFromDirectionandPosition(
					currentPosition, nextD);
			if (!atTheWall(newPoint) && !isLocationDangerous(whatISee, newPoint)) {
					possibleSafePlaces.add(nextD);
			}
		}
		if (possibleSafePlaces.isEmpty()) {
			for (int i = 0; i < 4; i++){
				Direction randomDirection = null;
				Random r = new Random();
				do {
					randomDirection = directionOptions.get(r.nextInt(directionOptions.size()));
				} while (randomDirection.getDx() == 0 && randomDirection.getDy() == 0);
				newL.add(randomDirection);
			}
			return newL;
		} else {
			Direction randomDirection = null;
			if (possibleSafePlaces.size() == 1){
				newL.add(possibleSafePlaces.remove(0));
				return newL;
			}
			Random r = new Random();
			do {
				randomDirection = directionOptions.get(r.nextInt(possibleSafePlaces.size()));
			} while (randomDirection.getDx() == 0 && randomDirection.getDy() == 0);
			newL.add(randomDirection);
			return newL;
		}
	}

//	public LinkedList<Direction> bestDirections(Set<Observation> whatISee, Direction d, Point2D currentPosition) {
//		LinkedList<Direction> newL = new LinkedList<Direction>();
//		Random r = new Random();
//		Direction prevD = d;
//		Point2D prevP = currentPosition;
//		int end = (r.nextInt(10) + 5);
//		for (int i = 0; i < end; i++) {
//			Direction newD = getDirection(whatISee, prevD, prevP);
//			if (newD == null) {
//				ArrayList<Direction> directionOptions = Direction.allBut(prevD);
//				Direction randomDirection = null;
//				Point2D randomPoint = currentPosition;
//				do {
//					randomDirection = directionOptions.get(r.nextInt(directionOptions.size()));
//					randomPoint = getPointFromDirectionandPosition(prevP, randomDirection);
//				} while (illegalMove(randomPoint));
//				newD = randomDirection;
//			}
//			newL.add(newD);
//			prevD = newD;
//			prevP = getPointFromDirectionandPosition(prevP, newD);
//		}
//		return newL;
//	}

	public Direction getDirection(Set<Observation> whatISee, Direction d, Point2D currentPosition) {
		ArrayList<Direction> directionOptions = Direction.allBut(d);
		for (Direction nextD : directionOptions) {
			Point2D newPoint = getPointFromDirectionandPosition(currentPosition, nextD);
			if (!illegalMove(newPoint) && !isLocationDangerous(whatISee, newPoint)) {
				if (!(nextD.getDx() == 0 && nextD.getDy() == 0)) {
					return nextD;
				}
			}
		}
		return null;
	}

	private Point2D getPointFromDirectionandPosition(Point2D currentPosition, Direction nextD) {
		double newPosX = currentPosition.getX() + nextD.getDx();
		double newPosY = currentPosition.getY() + nextD.getDy();
		Point2D newPoint = new Point2D.Double(newPosX, newPosY);
		return newPoint;
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

	public static boolean illegalMove(Point2D p) {
		if (Math.abs(p.getX()) == (NewPlayer.d + 1) || Math.abs(p.getY()) == (NewPlayer.d + 1)) {
			return true;
		} else {
			return false;
		}
	}
}
