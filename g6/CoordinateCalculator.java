package isnork.g6;

import java.awt.geom.Point2D;
import java.util.HashMap;

public class CoordinateCalculator {
	public static HashMap<Integer, Destination> coordMap;
	public static int d;
	public static int r;
	public static int n;
	static double diverSpread;
	static double maxPoint;
	static double indent;
	static boolean headedIn;
	static int callCounter;
	private static boolean loadSpiralDestinations;

	
	public static void initCoordinateCalculator(int d, int r, int n)
	{
		CoordinateCalculator.d = d;
		CoordinateCalculator.r = r;
		CoordinateCalculator.n = n;
		
		indent = r;
		callCounter = 0;
		headedIn = true;
		coordMap = new HashMap<Integer, Destination>();
		for (int i = 0; i > n * -1; --i)
		{
			coordMap.put(i, new Destination(new Point2D.Double(), 0, i, true));
		}
	}
	
	public static boolean getLoadSpiralDestinations()
	{
		if (loadSpiralDestinations)
		{
			if (++callCounter == n)
			{
				callCounter = 0;
				loadSpiralDestinations = false;
				System.out.println("Everyone has updated their queues");
				return true;
			}
		}
		return loadSpiralDestinations;
	}
	
	public static void updateCoordMap()
	{
		loadSpiralDestinations = true;

		maxPoint = d - indent;
		double targetSquares = (((d * 2 + 1) - indent * 2) - 1) * 4;
		diverSpread = (double)targetSquares/(double)n;
		double currentx = 0;
		double currenty = maxPoint;
		int i = 0;
		/* start to top right corner */
		while(currentx <= maxPoint && i > n * -1)
		{
			coordMap.get(i).getDestination().setLocation(Math.round(currentx), Math.round(currenty));
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
			coordMap.get(i).getDestination().setLocation(Math.round(currentx), Math.round(currenty));
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
			coordMap.get(i).getDestination().setLocation(Math.round(currentx), Math.round(currenty));
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
			coordMap.get(i).getDestination().setLocation(Math.round(currentx), Math.round(currenty));
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
			coordMap.get(i).getDestination().setLocation(Math.round(currentx), Math.round(currenty));
			--i;
			currentx += diverSpread;
		}
		
		getNextIteration();
	}
	
	private static void getNextIteration()
	{
		if (headedIn)
		{
			indent += ((r * 3) / 2);
		}
		else
		{
			indent -= ((r * 3) / 2);
		}
		maxPoint = d - indent;
		if (indent < r)
		{
			headedIn = true;
			indent = r;
		}
		else if (maxPoint < r)
		{
			headedIn = false;
			indent = r;
		}
	}
	
	public static void printCoords()
	{
		for (int i = 0; i > n * -1; --i)
		{
			System.out.println("Key: " + i + " Point: " + coordMap.get(i).getDestination() + " Priority: " + coordMap.get(i).getPriority());
		}
	}
	
}
