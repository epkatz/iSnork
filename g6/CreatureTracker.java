package isnork.g6;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;
import java.util.LinkedList;

public class CreatureTracker {
	
	private LinkedList<Creature> creaturesSeen;
	private int playerID;
	public static final int MAX_SEEN = 3;
	
	public CreatureTracker(int playerID){
		this.creaturesSeen = new LinkedList<Creature>();
		this.playerID = playerID;
		
	}
	
	public void addToTracker(Observation creature) {
		Creature newCreature = new Creature(creature.getName());
		creaturesSeen.add(newCreature);
		//System.out.print("Player " + playerID + "added new creature: " + creature.getName() + "\n");
	}
	
	public LinkedList<Creature> getListOfCreatures() {
		return creaturesSeen;
	}
	
	public int getPlayerID() {
		return playerID;
	}
		
	//check if creature is in the list
	public boolean didSeeCreature(String name) {
		for(int i = 0; i < creaturesSeen.size(); i++) {
			Creature tempObject = creaturesSeen.get(i);
			if(tempObject.getName().equals(name))
				return true;
		}
		//if here then creature not in list
		return false;
	}
	
	public void printListOfSeenCreatures() {
		System.out.print("TRACKER LIST FOR PLAYER: " + playerID + "\n");
		for(int i = 0; i < creaturesSeen.size(); i++) {
			int temp = i+1;
			System.out.print("creature " + temp + ": " + creaturesSeen.get(i).creature + "\n");
		}
	}
	
//	//retrieve element index of specified creature
//	public int getCreatureIndex(String name) {
//		for(int i = 0; i < creaturesSeen.size(); i++) {
//			Creature tempObject = creaturesSeen.get(i);
//			if(tempObject.creature)
//				return i;
//		}
//		//error if reached here
//		System.out.print("ERROR: inside creature tracker get method");
//		return -1;
//		
//	}
//	
//	public void seeCreature(int id, String name){
//		if(!didSeeCreature(name)) {
//			//add creature to the list
//			Creature newCreature = new Creature(name);
//			newCreature.addID(id);			
//			//add this to the creature tracker
//			creaturesSeen.add(newCreature);
//			System.out.print("added creature: " + name + "\n" );
//		} else {
//			int creatureIndex = getCreatureIndex(name);
//			
//		}
//		
//	}
	
	public class Creature {
		LinkedList<Integer> seen; //store id of the creatures seen
		String creature;
		
		public Creature(String creature) {
			this.creature = creature;
			seen = new LinkedList<Integer>();
		}
		
		public LinkedList<Integer> getListOfSeenIDs() {
			return seen;
		}
		
		public boolean isMaxedOut() {
			if(seen.size() > MAX_SEEN)
				return true;
			return false;
		}
		
		public void addID(int id) {
			seen.add(id);
		}
		
		public String getName() {
			return creature;
		}
	}
	
}
