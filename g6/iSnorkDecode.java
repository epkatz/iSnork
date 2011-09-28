package isnork.g6;
import isnork.sim.SeaLifePrototype;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class iSnorkDecode {
	
	public ArrayList<Creature> creatureList;
	private Set<SeaLifePrototype> totalSeaLifeList;
	
	public iSnorkDecode(Set<SeaLifePrototype> seaLifePossibilites) {
		this.creatureList = new ArrayList<Creature>();
		totalSeaLifeList = seaLifePossibilites;
		assignCreatureToChar();
	}
	
	public String getCreatureNameFromChar(String creatureChar) {
		for(int i = 0; i < creatureList.size(); i++) {
			if(creatureList.get(i).creatureChar == creatureChar) 
				return creatureList.get(i).getName();
		}
		//System.out.print("there is no creature assigned to letter: " + creatureChar);
		return null;
	}
	
	public String getCharFromCreatureName(String creatureName) {
		for(int i = 0; i < creatureList.size(); i++) 
			if(creatureList.get(i).creature.equals(creatureName))
				return creatureList.get(i).getChar();
		//System.out.print("there is no char assigned to creature: " + creatureName + "\n");
		return null;
	}
	
	public ArrayList<Creature> getCreatureList() {
		return creatureList;
	}
	
	//check if creature seen is top 26 highest before sending message
	public boolean isCreatureInDecoder(String creatureName) {
		for(Object obj : creatureList) {
			Creature creature = (Creature)obj;
			if(creature.getName().equals(creatureName))
				return true;
		}
		return false;
	}
	
	//get the priority of a message
	public int getPriorityOfMessage(String message) {
		if(message == "a" || message ==  "b")
			return 130;
		if(message == "c" || message == "d")
			return 120;
		if(message == "e" || message == "f")
			return 110;
		if(message == "g" || message == "h")
			return 100;
		if(message == "i" || message == "j")
			return 90;
		if(message == "k" || message == "l")
			return 80;
		if(message == "m" || message == "n")
			return 70;
		if(message == "o" || message == "p")
			return 60;
		if(message == "q" || message == "r")
			return 50;
		if(message == "s" || message == "t")
			return 40;
		if(message == "u" || message == "v")
			return 30;
		if(message == "w" || message == "x")
			return 20;
		if(message == "y" || message == "z")
			return 10;
		return 0;
	}
				
		
	
	private ArrayList<SeaLifePrototype> prioritizeCreatureList() {
		
		ArrayList<SeaLifePrototype> staticHappyHighCreatures = new ArrayList<SeaLifePrototype>(); //high is anywhere from 25-infinity points
		ArrayList<SeaLifePrototype> staticHappyLowCreatures = new ArrayList<SeaLifePrototype>(); // low is below 25
		ArrayList<SeaLifePrototype> movingHappyHighCreatures = new ArrayList<SeaLifePrototype>();
		ArrayList<SeaLifePrototype> movingHappyLowCreatures = new ArrayList<SeaLifePrototype>();
		ArrayList<SeaLifePrototype> staticDangerousHighCreatures = new ArrayList<SeaLifePrototype>();
		ArrayList<SeaLifePrototype> staticDangerousLowCreatures = new ArrayList<SeaLifePrototype>();
		ArrayList<SeaLifePrototype> movingDangerousHighCreatures = new ArrayList<SeaLifePrototype>();
		ArrayList<SeaLifePrototype> movingDangerousLowCreatures = new ArrayList<SeaLifePrototype>();
		
		//create local copy
		Set<SeaLifePrototype> tempList = new HashSet<SeaLifePrototype>();
		tempList = totalSeaLifeList;
		
		//sort based on creature stats
		for(int i = 0; i < tempList.size(); i++) {
			SeaLifePrototype creature = getHighestCreature(tempList);
			if(creature.getHappiness() >= 25 && creature.getSpeed() == 0 && !creature.isDangerous())
				staticHappyHighCreatures.add(creature);
			if(creature.getHappiness() < 25 && creature.getSpeed() == 0 && !creature.isDangerous())
				staticHappyLowCreatures.add(creature);
			if(creature.getHappiness() >= 25 && creature.getSpeed() == 1 && !creature.isDangerous())
				movingHappyHighCreatures.add(creature);
			if(creature.getHappiness() < 25 && creature.getSpeed() == 1 && !creature.isDangerous())
				movingHappyLowCreatures.add(creature);
			if(creature.getHappiness() >= 25 && creature.getSpeed() == 1 && !creature.isDangerous())
				staticHappyLowCreatures.add(creature);
			if(creature.getHappiness() >= 25 && creature.getSpeed() == 0 && creature.isDangerous())
				staticDangerousHighCreatures.add(creature);
			if(creature.getHappiness() < 25 && creature.getSpeed() == 0 && creature.isDangerous())
				staticDangerousLowCreatures.add(creature);
			if(creature.getHappiness() >= 25 && creature.getSpeed() == 1 && creature.isDangerous())
				movingDangerousHighCreatures.add(creature);
			if(creature.getHappiness() >= 25 && creature.getSpeed() == 1 && creature.isDangerous())
				movingDangerousLowCreatures.add(creature);
			
			tempList.remove(creature);
		}
		
		ArrayList<SeaLifePrototype> sortedList = new ArrayList<SeaLifePrototype>();
		//add all the sub arrays to one sorted by priority
		sortedList.addAll(staticHappyHighCreatures);
		sortedList.addAll(staticHappyLowCreatures);
		sortedList.addAll(movingHappyHighCreatures);
		sortedList.addAll(movingHappyLowCreatures);
		sortedList.addAll(staticDangerousHighCreatures);
		sortedList.addAll(staticDangerousLowCreatures);
		sortedList.addAll(movingDangerousHighCreatures);
		sortedList.addAll(movingDangerousLowCreatures);
		
		
		
		
		return sortedList;
		
		
		
		
	}
	
	private SeaLifePrototype getHighestCreature(Set<SeaLifePrototype> list) {
		
		int max = 0;
		SeaLifePrototype highestCreature = new SeaLifePrototype();
		for(SeaLifePrototype obj : list) {
			if(max == 0) {
				max = obj.getHappiness();
				highestCreature = obj;
			}
			if(obj.getHappiness() > max) {
				max = obj.getHappiness();
				highestCreature = obj;
			}
		}
		//System.out.print("highest creature:" + highestCreature.getName() + "\n");
		return highestCreature;
	}
	
	
	
	public void assignCreatureToChar() {
		//filterCreatures(); //filter list before using it
		ArrayList<SeaLifePrototype> seaLifeList = new ArrayList<SeaLifePrototype>();
		seaLifeList = prioritizeCreatureList();
		SeaLifePrototype tempCreature = new SeaLifePrototype();
		String message = null;
		for(int i = 0; i < seaLifeList.size(); i++) {
			tempCreature = seaLifeList.get(i);
			//System.out.print(tempCreature.getName() + "\n");
			if(i < 26) {
				switch(i) {
				case 0:
					message = "a";
					break;
				case 1:
					message = "b";
					break;
				case 2:
					message = "c";
					break;
				case 3:
					message = "d";
					break;
				case 4:
					message = "e";
					break;
				case 5:
					message = "f";
					break;
				case 6:
					message = "g";
					break;
				case 7:
					message = "h";
					break;
				case 8:
					message = "i";
					break;
				case 9:
					message = "j";
					break;
				case 10:
					message = "k";
					break;
				case 11:
					message = "l";
					break;
				case 12:
					message = "m";
					break;
				case 13:
					message = "n";
					break;
				case 14:
					message = "o";
					break;
				case 15:
					message = "p";
					break;
				case 16:
					message = "q";
					break;
				case 17:
					message = "r";
					break;
				case 18:
					message = "s";
					break;
				case 19:
					message = "t";
					break;
				case 20:
					message = "u";
					break;
				case 21:
					message = "v";
					break;
				case 22:
					message = "w";
					break;
				case 23:
					message = "x";
					break;
				case 24:
					message = "y";
					break;
				case 25:
					message = "z";
					break;
				default:
					break;	
				}
				//assign char to creature
				//check if static
				boolean isStatic;
				if(tempCreature.getSpeed() == 0)
					isStatic = true;
				else
					isStatic = false;
				Creature creature = new Creature(tempCreature.getName(), message, isStatic);
				//add to the creature list
				creatureList.add(creature);
			}
		}		
	}
	
	
	public void printDecodedList() {
		for(int i = 0; i < creatureList.size(); i++) {
			System.out.print(creatureList.get(i).getName() + " = " + creatureList.get(i).getChar() + creatureList.get(i).isStatic() + "\n");
		}
	}
		
	public class Creature {
		String creature;
		String creatureChar;
		boolean isStatic;
		
		public Creature(String creature, String creatureChar, boolean isStatic) {
			this.creature = creature;
			this.creatureChar = creatureChar;
			this.isStatic = isStatic;
		}
		
		public String getName() {
			return creature;
		}
		
		public String getChar() {
			return creatureChar;
		
		}
		
		public boolean isStatic() {
			return isStatic;
		}
		
		
	}
	

}
