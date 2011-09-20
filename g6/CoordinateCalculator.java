package isnork.g6;

import java.awt.geom.Point2D;
import java.util.HashMap;

public class CoordinateCalculator {
	public static HashMap<Integer, Point2D> coordMap;
	public static int d;
	public static int r;
	public static int n;
	static double diverSpread;
	static double maxPoint;
	static double nextIteration;
	static boolean headedIn;
	
	public static void initCoordinateCalculator(int d, int r, int n)
	{
		CoordinateCalculator.d = d;
		CoordinateCalculator.r = r;
		CoordinateCalculator.n = n;
		
		nextIteration = 0;
		headedIn = true;
		coordMap = new HashMap<Integer, Point2D>();
		for (int i = 0; i > n * -1; --i)
		{
			coordMap.put(i, new Point2D.Double());
		}
	}
	
	public static void updateCoordMap()
	{
		double indent = r/2 + r * nextIteration;
		maxPoint = d - indent;
		double targetSquares = (((d * 2 + 1) - indent * 2) - 1) * 4;
		diverSpread = (double)targetSquares/(double)n;
		double currentx = 0;
		double currenty = maxPoint;
		int i = 0;
		/* start to top right corner */
		while(currentx <= maxPoint && i > n * -1)
		{
			coordMap.get(i).setLocation(Math.round(currentx), Math.round(currenty));
			--i;
			currentx += diverSpread;
		}
		if (currentx - diverSpread == maxPoint)
		{
			currentx = maxPoint;
			currenty = maxPoint - diverSpread;
		}
		else
		{
			currenty = maxPoint - (currentx - maxPoint);
			currentx = maxPoint;
		}

		/* top right to bottom right */
		while(currenty >= -1 * maxPoint && i > n * -1)
		{
			coordMap.get(i).setLocation(Math.round(currentx), Math.round(currenty));
			--i;
			currenty -= diverSpread;
		}
		if (currenty + diverSpread == -1 * maxPoint) // corner
		{
			currenty = -1 * maxPoint;
			currentx = maxPoint - diverSpread;
		}
		else // not corner
		{
			currentx = maxPoint - (-1 * currenty - maxPoint);
			currenty = -1 * maxPoint;
		}
		
		/* bottom right to bottom left */
		while(currentx >= -1 * maxPoint && i > n * -1)
		{
			coordMap.get(i).setLocation(Math.round(currentx), Math.round(currenty));
			--i;
			currentx -= diverSpread;
		}
		if (currentx + diverSpread == -1 * maxPoint) // corner
		{
			currentx = -1 * maxPoint;
			currenty = -1 * maxPoint + diverSpread;
		}
		else // not corner
		{
			currenty = -1 * maxPoint + (currentx * -1 - maxPoint);
			currentx = -1 * maxPoint;
		}
		
		/* bottom left to top left */
		while(currenty <= maxPoint && i > n * -1)
		{
			coordMap.get(i).setLocation(Math.round(currentx), Math.round(currenty));
			--i;
			currenty += diverSpread;
		}
		if (currenty - diverSpread == maxPoint) // corner
		{
			currenty = maxPoint;
			currentx = -1 * maxPoint + diverSpread;
		}
		else // not corner
		{
			currentx = -1 * maxPoint + (currenty - maxPoint);
			currenty = maxPoint;
		}
		
		/* top left to start */
		while (currentx < 0 && i > n * -1)
		{
			coordMap.get(i).setLocation(Math.round(currentx), Math.round(currenty));
			--i;
			currentx += diverSpread;
		}
		
		nextIteration = getNextIteration();
	}
	
	private static double getNextIteration()
	{
		double i;
		if (headedIn)
		{
			i = nextIteration + 1;
		}
		else
		{
			i = nextIteration - 1;
		}
		double indent = r/2 + r * i;
		maxPoint = d - indent;
		if (maxPoint < r/2)
		{
			i -= 1.5;
			headedIn = false;
		}
		else if (maxPoint > d - r/2)
		{
			i += 1;
			headedIn = true;
		}
		
		return i;
	}
	
	public static void printCoords()
	{
		for (int i = 0; i > n * -1; --i)
		{
			System.out.println("Key: " + i + " Point: " + coordMap.get(i));
		}
	}
	
}
