package isnork.g6;

import java.awt.geom.Point2D;

public class PathManager {

	public List<Node> buildPath(Point2D current, Point2D destination)
	{
		List<Node> path = new LinkedList<Node>();
		int deltax = destination.getX() - current.getX();
		int deltay = destination.getY() - current.getY();
	}
	
}
