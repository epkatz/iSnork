package isnork.g6;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

import java.awt.geom.Point2D;

public class BalancedStrategy extends Strategy {
	Direction currentDirection;
	Direction preferredDirection; // direction according to pattern
	int acceptableDanger;

	public BalancedStrategy(Set<SeaLifePrototype> seaLifePossibilites,
			int penalty, int d, int r, int n, NewPlayer p) {
		super(seaLifePossibilites, penalty, d, r, n, p);
		
		acceptableDanger = 0;
	}

	@Override
	public Direction nextMove() {
		if (player.currentPath.isEmpty())
		{
			if (player.minutesLeft == 8 * 60 - 1)
			{
				player.destination = determineInitialDestination();
			}
			else
			{
				player.destination = new Point2D.Double(0, 0);
			}
			player.currentPath = PathManager.buildPath(player.currentPosition, player.destination, player.minutesLeft);
		}
		
		Node nextMove = player.currentPath.pop();
//		System.out.println("returning direction " + nextMove.getDirection());
		return nextMove.getDirection();
/*		// if it's the first turn, pick a direction
		if (NewPlayer.getMinutesLeft() == 8 * 60 - 1)
		{
			currentDirection = determineInitialDirection();
			preferredDirection = currentDirection;
		}
		// otherwise, continue the pattern
		else
		{
			currentDirection = determineNextDirection();
		}
		
		return currentDirection;
		*/
	}
	
	private Point2D determineInitialDestination()
	{
		Point2D destination = new Point2D.Double();
		// direction determined by player id
		switch(Math.abs(player.getId()) % 8)
		{
		case 0:
			destination.setLocation(0, d);
			break;
		case 1:
			destination.setLocation(0, -1 * d);
			break;
		case 2:
			destination.setLocation(d, 0);
			break;
		case 3:
			destination.setLocation(-1 * d, 0);
			break;
		case 4:
			destination.setLocation(d, d);
			break;
		case 5:
			destination.setLocation(-1 * d, -1 * d);
			break;
		case 6:
			destination.setLocation(-1 * d, d);
			break;
		case 7:
			destination.setLocation(d, -1 * d);
			break;
		}
		
//		System.out.println("Destination for player with id " + player.getId() + " is " + destination);
		return destination;
	}
	
	private Direction determineNextDirection()
	{
		// make a list of where all the dangerous creatures are
		List<Direction> dangerousDir = new ArrayList<Direction>();
		Object[] whatISee = player.whatISee.toArray();
		for (Object obj : whatISee)
		{
			// for each creature I see, check if it's dangerous
			Observation o = (Observation)obj;
			if (o.isDangerous()) // && o.happiness() > acceptableDanger)
			{
				// if it is, add what direction it's in to the list
				dangerousDir.add(getDirectionFromPoints(player.currentPosition, o.getLocation()));
			}
		}
		
		// if surrounded by danger, don't move
		if (dangerousDir.size() == 8)
		{
			return Direction.STAYPUT;
		}
		// test the direction from pattern against danger
		Direction nextDir = getNextDirInPattern();
		while (dangerousDir.contains(nextDir))
		{
			// while it's dangerous, try a different direction
			nextDir = moveClockwise(nextDir, 1);
		}
		
		// return final direction decision
		return nextDir;
	}
	
	private Direction getNextDirInPattern()
	{
		if (atTheWall(player.currentPosition))
		{
			preferredDirection = moveClockwise(preferredDirection, 4);
		}
		if (atBoat(player.currentPosition))
		{
			preferredDirection = moveClockwise(preferredDirection, 1);
		}
		return preferredDirection;
	}
	
	private boolean atBoat(Point2D p)
	{
		if (p.getX() == 0 && p.getY() == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean atTheWall(Point2D p)
	{
		if (Math.abs(p.getX()) == d || Math.abs(p.getY()) == d)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private Direction moveClockwise(Direction d, int numSteps)
	{
		if (numSteps == 0 || d == Direction.STAYPUT)
		{
			return d;
		}
		else
		{
			switch (d)
			{
			case N:
				d = Direction.NE;
				break;
			case NE:
				d = Direction.E;
				break;
			case E:
				d =  Direction.SE;
				break;
			case SE:
				d = Direction.S;
				break;
			case S:
				d = Direction.SW;
				break;
			case SW:
				d = Direction.W;
				break;
			case W:
				d = Direction.NW;
				break;
			case NW:
				d = Direction.N;
				break;
			default:
				d = Direction.STAYPUT;
				break;
			}
			return moveClockwise(d, numSteps - 1);
		}
	}
	
	private Direction getDirectionFromPoints(Point2D from, Point2D to)
	{
		double deltax = from.getX() - to.getX();
		double deltay = from.getY() - to.getY();
		
		Direction d = Direction.STAYPUT;
		
		if (deltax == 0 && deltay > 0)
		{
			d = Direction.N;
		}
		else if (deltax == 0 && deltay < 0)
		{
			d = Direction.S;
		}
		else if (deltax > 0 && deltay == 0)
		{
			d = Direction.W;
		}
		else if (deltax < 0 && deltay == 0)
		{
			d = Direction.E;
		}
		else if (deltax > 0 && deltay > 0)
		{
			d = Direction.NW;
		}
		else if (deltax > 0 && deltay < 0)
		{
			d = Direction.SW;
		}
		else if (deltax < 0 && deltay > 0)
		{
			d = Direction.NE;
		}
		else if (deltax < 0 && deltay < 0)
		{
			d = Direction.SE;
		}

		return d;
	}

}
