package isnork.g6;

import isnork.sim.SeaLifePrototype;

import java.util.LinkedList;
import java.util.Set;

public class CreatureTracker {
	
	private LinkedList<Creature> creaturesSeen;
	public static final int MAX_SEEN = 3;
	
	public CreatureTracker(Set<SeaLifePrototype> seaLifePossibilites){
		creaturesSeen = new LinkedList<Creature>();
	}
	
	public void seeCreature(int id, String name){
		
	}
	
	public class Creature {
		LinkedList<Integer> seen;
		String creature;
		
		public Creature(String name){
			creature = name;
			seen = new LinkedList<Integer>();
		}
		
		public void addID(int id){
			
		}
		
		public boolean maxedOut(){
			if (seen.size() > MAX_SEEN){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
}
