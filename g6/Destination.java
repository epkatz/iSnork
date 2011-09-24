package isnork.g6;

import java.awt.geom.Point2D;

public class Destination implements Comparable<Destination> {
	private Point2D destination;
	private int priority;
	private int id;
	private boolean isStatic;
	
	public Destination(Point2D destination, int priority, int id, boolean isStatic)
	{
		this.destination = destination;
		this.priority = priority;
		this.id = id;
		this.isStatic = isStatic;
	}
	
	public Point2D getDestination()
	{
		return destination;
	}
	public int getPriority()
	{
		return priority;
	}
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	public int getId()
	{
		return id;
	}
	
	public void decrementPriority()
	{
		if (!isStatic)
		{
			priority -= 1;
		}
	}

	@Override
	public int compareTo(Destination arg0) {
		if (this.priority < arg0.getPriority())
		{
			return 1;
		}
		else if (this.priority > arg0.getPriority())
		{
			return -1;
		}
		return 0;
	}
}
