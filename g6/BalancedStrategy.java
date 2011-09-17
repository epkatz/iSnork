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
		// if it's the first turn, pick a direction
		if (player.minutesLeft == 8 * 60 - 1)
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
	}
	
	private Direction determineInitialDirection()
	{
		// direction determined by player id
		switch(Math.abs(player.getId()) % 8)
		{
		case 0:
			return Direction.N;
		case 1:
			return Direction.S;
		case 2:
			return Direction.E;
		case 3:
			return Direction.W;
		case 4:
			return Direction.NE;
		case 5:
			return Direction.SW;
		case 6:
			return Direction.NW;
		case 7:
			return Direction.SE;
		}
		return null;
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
