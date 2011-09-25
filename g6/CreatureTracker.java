package isnork.g6;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;
import java.util.LinkedList;

public class CreatureTracker {
	
	private LinkedList<Creature> creaturesSeen;
	private int playerID;
	public static final int MAX_SEEN = 3;
	private NewPlayer player; // added
	
	public CreatureTracker(int playerID){
		this.creaturesSeen = new LinkedList<Creature>();
		this.playerID = playerID;
	}
	
	/* added for returning to boat if no more possible positive points */
	public boolean seenAllCreatures(int numPossible)
	{
		if (getSizeOfList() == numPossible + 1)
		{
			for (Creature c : creaturesSeen)
			{
				if (!c.isMaxedOut())
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getSizeOfList() {
		return creaturesSeen.size();
	}
	
	/* added this so each player can add their object to their own creature tracker */
	public void setPlayer(NewPlayer p)
	{
		player = p;
	}

	public void addToTracker(Observation creature) {
		Creature newCreature = new Creature(creature.getName());
		newCreature.addID(creature.getId());
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
		if(creaturesSeen.size() == 0) {	
			return false;
		}
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

	/* added if case because when player is on the boat, it is not getting points for seeing creatures */
	public void seeCreature(Observation creature){
		if (player!= null && !player.isPlayerOnBoat())
		{

			for(Object obj : creaturesSeen) {
				Creature tempCreature = (Creature)obj;
				if(tempCreature.creature == creature.getName()) {
					//System.out.print("inside\n");
					if(tempCreature.isMaxedOut()) {
						//System.out.print("Player: " + playerID + " has seen 3 or more " + creature.getName() + "\n");
					} else {
						if(tempCreature.didSeeID(creature.getId()));
						//System.out.print("Player: " + playerID + " already seen ID " + creature.getId() + "\n");
						else {
							tempCreature.addID(creature.getId());
							//System.out.print("Added a unique id: " + creature.getId() + " of species: " + creature.getName() + "to Player " + playerID + " tracker\n");
						}
					}
				}
			}
			
		}
	}
	
	
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
		
		public boolean didSeeID(int id) {
			if(seen.contains(id))
				return true;
			return false;
		}
	}
	
}
