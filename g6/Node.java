package isnork.g6;

import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;

public class Node {
	private Direction direction;
	private int minuteCreated;
	private Point2D assumedPoint;
	
	public Node(Direction direction, int minuteCreated){
		this.direction = direction;
		this.minuteCreated = minuteCreated;
	}
	
	public Direction getDirection(){
		return direction;
	}
	public int getMinuteCreated(){
		return minuteCreated;
	}
	/* For Danger Avoidance */
	public Point2D getAssumedPoint(){
		return assumedPoint;
	}
	public void setAssumedPoint(Point2D assumedPoint) {
		this.assumedPoint = assumedPoint;
	}
	
	
}
