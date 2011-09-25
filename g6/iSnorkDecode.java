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
	
	public String getCreatureNameFromChar(char creatureChar) {
		for(int i = 0; i < creatureList.size(); i++)
			if(creatureList.get(i).creatureChar == creatureChar)
				return creatureList.get(i).getName();
		System.out.print("there is no creature assigned to letter: " + creatureChar);
		return null;
	}
	
	public char getCharFromCreatureName(String creatureName) {
		for(int i = 0; i < creatureList.size(); i++) 
			if(creatureList.get(i).creature.equals(creatureName))
				return creatureList.get(i).getChar();
		System.out.print("there is no char assigned to creature: " + creatureName + "\n");
		return ' ';
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
	
	
	private void filterCreatures() {
		Set<SeaLifePrototype> filteredCreatureList;
		filteredCreatureList = new HashSet<SeaLifePrototype>();
		int seaLifeCount = totalSeaLifeList.size();
		int minHap = 0;
		if(seaLifeCount < 26)
			return;
		for(SeaLifePrototype obj : totalSeaLifeList) { //only add non dangerous creatures w/ happiness greater than min
			if(obj.isDangerous())
				continue;
			//sort by happiness score
			if(minHap == 0) {
				minHap = obj.getHappiness();
			}
			if(filteredCreatureList.size() < 26 ) {
				if(obj.getHappiness() >= minHap) {
					filteredCreatureList.add(obj);
				} else if (obj.getHappiness() < minHap) { //check to see if enough space to add
						filteredCreatureList.add(obj);
						minHap = obj.getHappiness();
				}
				
			} else {
				if(obj.getHappiness() > minHap) {
					int min = 0;
					//loop through the filtered list and remove the lowest value and add the highest
					for(SeaLifePrototype tempObj : filteredCreatureList) {
						if(tempObj.getHappiness() <= minHap) {
							filteredCreatureList.remove(tempObj);
							//now add the new higher happiness
							filteredCreatureList.add(obj);
							break;
						}
					}
					//set the new min happiness based on altered list
					for(SeaLifePrototype tempObj : filteredCreatureList) {
						if(min == 0)
							min = tempObj.getHappiness();
						if(tempObj.getHappiness() < min) {
							min = tempObj.getHappiness();
						}
					}
					minHap = min; 
				}
			}
		}
	//set new sea life list that only includes first 26 highest creatures
		totalSeaLifeList = filteredCreatureList;
	}
	
	
	
	private void assignCreatureToChar() {
		filterCreatures(); //filter list before using it
		Iterator iter = totalSeaLifeList.iterator(); 
		SeaLifePrototype tempCreature = new SeaLifePrototype();
		char message = ' ';
		int i = 0;
		if(!totalSeaLifeList.isEmpty()){
			while(iter.hasNext()) {
				tempCreature = (SeaLifePrototype)iter.next();
				if(i < 26) {
					switch(i) {
					case 0:
						message = 'a';
						break;
					case 1:
						message = 'b';
						break;
					case 2:
						message = 'c';
						break;
					case 3:
						message = 'd';
						break;
					case 4:
						message = 'e';
						break;
					case 5:
						message = 'f';
						break;
					case 6:
						message = 'g';
						break;
					case 7:
						message = 'h';
						break;
					case 8:
						message = 'i';
						break;
					case 9:
						message = 'j';
						break;
					case 10:
						message = 'k';
						break;
					case 11:
						message = 'l';
						break;
					case 12:
						message = 'm';
						break;
					case 13:
						message = 'n';
						break;
					case 14:
						message = 'o';
						break;
					case 15:
						message = 'p';
						break;
					case 16:
						message = 'q';
						break;
					case 17:
						message = 'r';
						break;
					case 18:
						message = 's';
						break;
					case 19:
						message = 't';
						break;
					case 20:
						message = 'u';
						break;
					case 21:
						message = 'v';
						break;
					case 22:
						message = 'w';
						break;
					case 23:
						message = 'x';
						break;
					case 24:
						message = 'y';
						break;
					case 25:
						message = 'z';
						break;
					default:
						break;	
					}
					i++;
					//assign char to crature
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
	}
	
	public void printDecodedList() {
		for(int i = 0; i < creatureList.size(); i++) {
			System.out.print(creatureList.get(i).getName() + " = " + creatureList.get(i).getChar() + "\n");
		}
	}
		
	public class Creature {
		String creature;
		char creatureChar;
		boolean isStatic;
		
		public Creature(String creature, char creatureChar, boolean isStatic) {
			this.creature = creature;
			this.creatureChar = creatureChar;
			this.isStatic = isStatic;
		}
		
		public String getName() {
			return creature;
		}
		
		public char getChar() {
			return creatureChar;
		
		}
		
		public boolean isStatic() {
			return isStatic;
		}
		
		
	}
	

}
