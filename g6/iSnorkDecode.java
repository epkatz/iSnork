package isnork.g6;
import isnork.sim.SeaLifePrototype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class iSnorkDecode {
	
	private ArrayList<Creature> creatureList;
	static Set<SeaLifePrototype> seaLifeList;
	
	public iSnorkDecode(Set<SeaLifePrototype> seaLifePossibilites) {
		this.creatureList = new ArrayList<Creature>();
		seaLifeList = seaLifePossibilites;
		assignCreatureToChar();
	}
	
	public void assignCreatureToChar() {
		Iterator iter = seaLifeList.iterator(); 
		SeaLifePrototype tempCreature = new SeaLifePrototype();
		char message = ' ';
		int i = 0;
		if(!seaLifeList.isEmpty()){
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
					Creature creature = new Creature(tempCreature.getName(), message);
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
		
		public Creature(String creature, char creatureChar) {
			this.creature = creature;
			this.creatureChar = creatureChar;
		}
		
		public String getName() {
			return creature;
		}
		
		public char getChar() {
			return creatureChar;
		
		}
		
		
	}
	

}
