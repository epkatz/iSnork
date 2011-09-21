
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

	private static final int DEPTH = 4;
	
	public LinkedList<Node> buildSafePath (NewPlayer p) {
		LinkedList<Direction> dl = buildSafePath(p.whatISee, p.currentPath.peekFirst().getDirection(), p.currentPosition);
		LinkedList<Node> l = new LinkedList<Node>();
		for (Direction d: dl) {
			l.add(new Node(d, p.minutesLeft));
		}
		return l;
	}
	
	public LinkedList<Direction> buildSafePath(Set<Observation> whatISee, Direction d, Point2D currentPosition) {
		LinkedList<Direction> newL = new LinkedList<Direction>();
		ArrayList<Direction> directionOptions = Direction.allBut(d);
		Direction bestDirection = null;
		Point2D bestPoint = null;
		for (Direction nextD : directionOptions) {
			double newPosX = currentPosition.getX() + nextD.getDx();
			double newPosY = currentPosition.getY() + nextD.getDy();
			Point2D newPoint = new Point2D.Double(newPosX, newPosY);
			if (!atTheWall(newPoint) && !isLocationDangerous(whatISee, newPoint)){
				if (bestPoint == null){
					bestPoint = newPoint;
					bestDirection = nextD;
				}
				else{
					if (tilesAway(currentPosition, newPoint) < tilesAway(currentPosition, bestPoint)){
						bestPoint = newPoint;
						bestDirection = nextD;
					}
				}
			}
		}
		if (bestDirection == null){
			Random r = new Random();
			Direction randomDirection = directionOptions.get(r.nextInt(Direction.values().length));
			newL.add(randomDirection);
			double newPosX = currentPosition.getX() + randomDirection.getDx();
			double newPosY = currentPosition.getY() + randomDirection.getDy();
			Point2D randomPoint = new Point2D.Double(newPosX, newPosY);
			LinkedList<Direction> temp = buildSafePath(whatISee, randomDirection, randomPoint);
			for (Direction tmpD: temp){
				newL.add(tmpD);
			}
			return newL;
		}
		else{
			newL.add(bestDirection);
			return newL;
		}
	}

	/* Attempt 6 - DFS  

	public LinkedList<Node> buildSafePath(NewPlayer player) {
		System.out.println("FOUND DANGER!!");
		LinkedList<Node> l = new LinkedList<Node>();
		int depth = 0;
		Point2D curr = player.currentPosition;
		Point2D dest = player.destination;
		System.out.println("I'm at " + curr + " trying to get to " + dest);
		int tilesAway = tilesAway(curr, dest);
		Node n = new Node(Direction.STAYPUT, player.minutesLeft);
		n.setAssumedPoint(curr);
		l.add(n);
		System.out.println("Searching to depth: " + tilesAway);
		recursive_dfs(l, depth, DEPTH, player, tilesAway);
		l.removeFirst();
		return l;
	}

	private boolean recursive_dfs(LinkedList<Node> l, int depth, int path, NewPlayer player, int tilesAway) {
		boolean b = !isLocationDangerous(player.whatISee, l.peekLast().getAssumedPoint());
		if (b && l.size() > 3) {
			System.out.println("Made it to goal at " + player.destination);
			return true;
		}
		boolean success = false;
		Point2D currP = l.peekLast().getAssumedPoint();
		System.out.println("New Node at: " + currP);
		if (path > depth && !success) {
			for (Direction nextD : getRandomDirectionArray()) {
				Point2D nextP = getPointFromDirectionandPosition(currP, nextD);
				if (!illegalMove(nextP)) {
					System.out.println("Direction " + nextD + " at " + nextP + " is not an illegal move");
					Node tmp = new Node(nextD, player.minutesLeft);
					tmp.setAssumedPoint(nextP);
					l.addLast(tmp);
					System.out.println("depth is " + depth + " can go " + (path - depth) + " further");
					success = recursive_dfs(l, depth + 1, path, player, tilesAway);
					if (success) {
						return success;
					}
				}
			}
		}
		return false;
	}*/

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
	
	public ArrayList<Direction> getRandomDirectionArray() {
		ArrayList<Direction> arrL = Direction.allBut(Direction.STAYPUT);
		try {
		Random r = new Random();
			for (int i = 0; i < 7; i++) {
				int first = r.nextInt(arrL.size());
				int second = r.nextInt(arrL.size());
				Direction d = arrL.get(first);
				arrL.set(first, arrL.get(second));
				arrL.set(second, d);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrL;
	}

	/* Is a point on the board dangerous? */
	public boolean isLocationDangerous(Set<Observation> whatISee, Point2D pos) {
		Iterator<Observation> itr = whatISee.iterator();
		while (itr.hasNext()) {
			Observation o = itr.next();
			if (o.isDangerous() && o.getLocation().distance(pos) <= 2) {
				return true;
			}
		}
		return false;
	}

	public double getDanger(Set<Observation> whatISee, Point2D pos) {
		double danger = 0;
		Iterator<Observation> itr = whatISee.iterator();
		while (itr.hasNext()) {
			Observation o = itr.next();
			if (o.isDangerous() && o.getLocation().distance(pos) <= 2) {
				danger += o.happiness() * 2;
			}
		}
		return danger;
	}

	public boolean constantDanger(Set<Observation> whatISee, Point2D pos) {
		Iterator<Observation> itr = whatISee.iterator();
		while (itr.hasNext()) {
			Observation o = itr.next();
			if (o.isDangerous() && o.getDirection().equals(Direction.STAYPUT)) {
				if (tilesAway(pos, o.getLocation()) <= 2) {
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