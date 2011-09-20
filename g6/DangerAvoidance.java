
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

	private static final int SPACES_TO_SAFETY = 5;

/*	public LinkedList<Direction> bestDirections(Set<Observation> whatISee, Direction d, Point2D currentPosition) {
		LinkedList<Direction> newL = new LinkedList<Direction>();
		Random r = new Random();
		Direction prevD = d;
		Point2D prevP = currentPosition;
		int end = (r.nextInt(10) + 5);
		for (int i = 0; i < end; i++) {
			Direction newD = getDirection(whatISee, prevD, prevP);
			if (newD == null) {
				ArrayList<Direction> directionOptions = Direction.allBut(prevD);
				Direction randomDirection = null;
				Point2D randomPoint = currentPosition;
				do {
					randomDirection = directionOptions.get(r.nextInt(directionOptions.size()));
					randomPoint = getPointFromDirectionandPosition(prevP, randomDirection);
				} while (illegalMove(randomPoint));
				newD = randomDirection;
			}
			newL.add(newD);
			prevD = newD;
			prevP = getPointFromDirectionandPosition(prevP, newD);
		}
		return newL;
	}*/

	/* Make a direction arraylist based on best near direction */
	public LinkedList<Node> buildSafePath(NewPlayer player) {
		LinkedList<Node> copy = new LinkedList<Node>();
		LinkedList<Node> path = player.currentPath;
		copy.addAll(path);
		Node[] nArr = new Node[SPACES_TO_SAFETY];
		int spaces = 1;
		for (int i = 0; i < SPACES_TO_SAFETY && !path.isEmpty(); i++) {
			nArr[i] = path.pop();
			spaces++;
		}
		LinkedList<Node> temp;
		if (spaces < SPACES_TO_SAFETY) {
			Point2D nextPoint = player.currentPosition;
			Node tmpN = nArr[0];
			for (int i = 0; i < SPACES_TO_SAFETY; i++) {
				Node opp = getOppositeDirection(tmpN, player.minutesLeft);
				nextPoint = getPointFromDirectionandPosition(nextPoint, opp.getDirection());
				if (isLocationDangerous(player.whatISee, nextPoint) || illegalMove(nextPoint)) {
					opp = goClockwise(tmpN, player.minutesLeft);
					nextPoint = getPointFromDirectionandPosition(nextPoint, opp.getDirection());
					if (isLocationDangerous(player.whatISee, nextPoint) || illegalMove(nextPoint)) {
						opp = goCounterClockwise(tmpN, player.minutesLeft);
						nextPoint = getPointFromDirectionandPosition(nextPoint, opp.getDirection());
						if (isLocationDangerous(player.whatISee, nextPoint) || illegalMove(nextPoint)) {
							player.myStrategy.createNextPath();
							return player.currentPath;
						}
					}
				}
				path.add(opp);
				tmpN = opp;
			}
			
			return path;
		} else {
			temp = buildPathAround(player, nArr);
			if (temp == null) {
				player.destination = new Point2D.Double(0, 0);
				return PathManager.buildPath(player.currentPosition, player.destination, player.minutesLeft);
			}
			return temp;
		}
	}

	/* Build a route around the danger */
	public LinkedList<Node> buildPathAround(NewPlayer player, Node[] nArr) {
		LinkedList<Node> temp = new LinkedList<Node>();
		Point2D nTurnsAway = player.currentPosition;
		for (int i = 0; i < SPACES_TO_SAFETY; i++) {
			nTurnsAway = getPointFromDirectionandPosition(nTurnsAway, nArr[i].getDirection());
		}
		return recursiveBuild(player, temp, player.currentPosition, nTurnsAway, 0);
	}
	
	public LinkedList<Node> recursiveBuild(NewPlayer p, LinkedList<Node> l, Point2D f, Point2D t, int depth) {
		if (depth >= 8) {
			return null;
		}
		LinkedList<Node> tmpL = new LinkedList<Node>();
		for (Direction d: Direction.values()) {
			Point2D tmpF = getPointFromDirectionandPosition(f, d);
			if (tmpF.equals(t) && !isLocationDangerous(p.whatISee, tmpF) && !illegalMove(tmpF)) {  
				return l;
			} else {
				tmpL.add(new Node(d, p.minutesLeft));
				recursiveBuild(p, tmpL, tmpF, t, ++depth);
				if (tmpL != null) {
					return tmpL;
				}
			}
		}
		return null;
	}

	/* get the opposite direction */
	public Node getOppositeDirection(Node n, int minuteCreated) {
		int deg = n.getDirection().getDegrees();
		switch (deg) {
			case 0:
				return new Node(Direction.W, minuteCreated);
			case 180:
				return new Node(Direction.E, minuteCreated);
			case 90:
				return new Node(Direction.S, minuteCreated);
			case 270:
				return new Node(Direction.N, minuteCreated);
			case 45:
				return new Node(Direction.SW, minuteCreated);
			case 225:
				return new Node(Direction.NE, minuteCreated);
			case 135:
				return new Node(Direction.SE, minuteCreated);
			case 315:
				return new Node(Direction.NW, minuteCreated);
			default:
				return new Node(Direction.STAYPUT, minuteCreated);
		}
	}
	
	/* get the clockwise direction */
	public Node goClockwise(Node n, int minuteCreated) {
		int deg = n.getDirection().getDegrees();
		switch (deg) {
			case 0:
				return new Node(Direction.N, minuteCreated);
			case 180:
				return new Node(Direction.S, minuteCreated);
			case 90:
				return new Node(Direction.W, minuteCreated);
			case 270:
				return new Node(Direction.E, minuteCreated);
			case 45:
				return new Node(Direction.SE, minuteCreated);
			case 225:
				return new Node(Direction.NW, minuteCreated);
			case 135:
				return new Node(Direction.NE, minuteCreated);
			case 315:
				return new Node(Direction.SW, minuteCreated);
			default:
				return new Node(Direction.STAYPUT, minuteCreated);
		}
	}
	
	/* get the opposite direction */
	public Node goCounterClockwise(Node n, int minuteCreated) {
		int deg = n.getDirection().getDegrees();
		switch (deg) {
			case 0:
				return new Node(Direction.S, minuteCreated);
			case 180:
				return new Node(Direction.N, minuteCreated);
			case 90:
				return new Node(Direction.E, minuteCreated);
			case 270:
				return new Node(Direction.W, minuteCreated);
			case 45:
				return new Node(Direction.NW, minuteCreated);
			case 225:
				return new Node(Direction.SE, minuteCreated);
			case 135:
				return new Node(Direction.SW, minuteCreated);
			case 315:
				return new Node(Direction.NE, minuteCreated);
			default:
				return new Node(Direction.STAYPUT, minuteCreated);
		}
	}

	/* Is a point on the board dangerous? */
	public boolean isLocationDangerous(Set<Observation> whatISee, Point2D pos) {
		Iterator<Observation> itr = whatISee.iterator();
		while (itr.hasNext()) {
			Observation o = itr.next();
			if (o.isDangerous()) {
				if (tilesAway(pos, o.getLocation()) <= 3) {
					return true;
				}
			}
		}
		return false;
	}

	/* Returns the next direction that isn't dangerous, illegal or staying place */
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

	/* get a point from the direction you're going and current position */
	private Point2D getPointFromDirectionandPosition(Point2D currentPosition, Direction nextD) {
		double newPosX = currentPosition.getX() + nextD.getDx();
		double newPosY = currentPosition.getY() + nextD.getDy();
		Point2D newPoint = new Point2D.Double(newPosX, newPosY);
		return newPoint;
	}

	public int tilesAway(Point2D me, Point2D them) {
		return ((int) PathManager.computeTotalSpaces(me, them));
	}

	/*
	 * Methods to see where you are relative to a static position
	 */

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

	public static boolean illegalMove(Point2D p) {
		if (Math.abs(p.getX()) == (NewPlayer.d + 1) || Math.abs(p.getY()) == (NewPlayer.d + 1)) {
			return true;
		} else {
			return false;
		}
	}
}