package isnork.g6;

import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.LinkedList;

public class PathManager {

	public static LinkedList<Node> buildPath(Point2D current, Point2D destination, int minutesLeft)
	{
		LinkedList<Node> path = new LinkedList<Node>();
//		System.out.println("Building path from " + current + " to " + destination);
		double deltax = destination.getX() - current.getX();
		double deltay = destination.getY() - current.getY();
		
		if (destination.distance(current) == 0)
		{
			path.add(new Node(Direction.STAYPUT, minutesLeft));
		}
		
		while (deltax != 0 || deltay != 0)
		{
//			System.out.println("deltax = " + deltax + " deltay = " + deltay);
			if (deltax > 0 && deltay == 0)
			{
				path.add(new Node(Direction.E, minutesLeft));
				deltax -= 1;
			}
			else if (deltax < 0 && deltay == 0)
			{
				path.add(new Node(Direction.W, minutesLeft));
				deltax += 1;
			}
			else if (deltax == 0 && deltay >  0)
			{
				path.add(new Node(Direction.S, minutesLeft));
				deltay -= 1;
			}
			else if (deltax == 0 && deltay <  0)
			{
				path.add(new Node(Direction.N, minutesLeft));
				deltay += 1;
			}
			else if (deltax > 0 && deltay >  0)
			{
				path.add(new Node(Direction.SE, minutesLeft));
				deltax -= 1;
				deltay -= 1;
			}
			else if (deltax > 0 && deltay <  0)
			{
				path.add(new Node(Direction.NE, minutesLeft));
				deltax -= 1;
				deltay += 1;
			}
			else if (deltax < 0 && deltay >  0)
			{
				path.add(new Node(Direction.SW, minutesLeft));
				deltax += 1;
				deltay -= 1;
			}
			else if (deltax < 0 && deltay <  0)
			{
				path.add(new Node(Direction.NW, minutesLeft));
				deltax += 1;
				deltay += 1;
			}
		}
		
//		System.out.println("Returning path of length " + path.size());
		return path;
	}
	
//	public LinkedList<>
	
}
