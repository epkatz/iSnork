package isnork.g6;

import isnork.sim.GameObject.Direction;

public class Node {
	private Direction direction;
	private int minuteCreated;
	
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
	
	
}
