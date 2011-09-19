package isnork.g6;

import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.Player;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Set;


public class NewPlayer extends Player {
	
	private LinkedList<Node> path;
	private Strategy myStrategy;
	private LinkedList<CreatureTracker> myTracker;
	public static final int turnAroundTimeAllowance = 7;
	public static int dangerAvoidTravelTime = 0; // default for really happy maps
//TODO: initialize dangerAvoidTravelTime when determining how dangerous the board is
	public static final boolean DANGER = true;
	public static final boolean HAPPY = false;
	public enum Level {
		NONE (0.0), LOW (1.0), MEDIUM (5.0), MAYBE (0.2), HIGH (10.0), OMG (20.0);
		private final double factor;
		Level(double factor){
			this.factor = factor;
		}
		public double getFactor(){
			return factor;
		}
	}
	public enum Risk {
		VERYBAD (2.0), BAD (0.5), EVEN (0.2), GOOD (0.5), VERYGOOD (2.0), NONE(0);
		private final double factor;
		Risk(double factor){
			this.factor = factor;
		}
		public double getFactor(){
			return factor;
		}
	}
	static int d;
	static int r;
	static int n;
	static Set<SeaLifePrototype> seaLifePossibilites;
	static int penality;
	int minutesLeft;
	Point2D currentPosition;
	Point2D destination;
	Set<Observation> whatISee;
	LinkedList<Node> currentPath;

	@Override
	public String getName() {
		return "G6: SELECT from team_names WHERE attribute = 'clever'";
	}
	
	@Override
	public void newGame(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
		NewPlayer.seaLifePossibilites = seaLifePossibilites;
		NewPlayer.penality = penalty;
		NewPlayer.d = d;
		NewPlayer.r = r;
		NewPlayer.n = n;
		
		minutesLeft = 8 * 60;
		assignTravelTimeAllowance();
		myStrategy = new BalancedStrategy(seaLifePossibilites, penalty, d, r, n, this);
		this.currentPath = new LinkedList<Node>();
		initializeTracker();
		//int dangerIndex = getBoardDanger();
		//myStrategy = decideStrategy(seaLifePossibilites, penality, d, r, n);
	}
	
	private void initializeTracker() {
		myTracker = new LinkedList<CreatureTracker>();
		for(int i = 0; i < n; i++) 
			myTracker.add(new CreatureTracker(i));

	}
	
	
		
	
	private CreatureTracker getWhatPlayerSaw(int id) {
		//make id positive
		if(id < 0) id *= -1;
		for(int i = 0; i < myTracker.size();  i++) {
			CreatureTracker creatureTracker = myTracker.get(i);
			if(creatureTracker.getPlayerID() == id)
				return creatureTracker;
		}
		
		System.out.print("ERROR: Diver's tracker could not be found");
		return null;
	}
	 
	private void updatePlayerTracker() {
		
		//**uncomment lines for debuggin and printing tracker list to the console
		CreatureTracker creatureTracker = getWhatPlayerSaw(getId());
		int id = getId() * -1; //make positive
		//creatureTracker.printListOfSeenCreatures();
		for(Object obj : whatISee) {
			Observation creature = (Observation)obj;
			if(!creatureTracker.didSeeCreature(creature.getName())) {
				creatureTracker.addToTracker(creature);
				//System.out.print("Player " + id + " First time seeing: " + creature.getName() + "\n");
			} else {
				//add id to the list
				creatureTracker.seeCreature(creature);
				//System.out.print("Player " + id + " Already saw: " + creature.getName()+ "\n");
			}
		}
		
	}
	
	@Override
	public String tick(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		minutesLeft -= 1;
		currentPosition = myPosition;
		whatISee = whatYouSee;
		updatePlayerTracker();
		return null;
	}

	@Override
	public Direction getMove() {
		return myStrategy.nextMove();
	}
	
	public Strategy decideStrategy(){
		return null;
	}
	
	public void assignTravelTimeAllowance()
	{
		int minPerMove = 3;
		switch(getLevel(DANGER))
		{
		case NONE:
			dangerAvoidTravelTime = 0;
			break;
		case LOW:
			dangerAvoidTravelTime = d * minPerMove;
			break;
		case MEDIUM:
			dangerAvoidTravelTime = d * 2 * minPerMove;
			break;
		case MAYBE:
			dangerAvoidTravelTime = d * minPerMove;
			break;
		case HIGH:
			dangerAvoidTravelTime = d * 3 * minPerMove;
			break;
		case OMG:
			dangerAvoidTravelTime = d * 4 * minPerMove;
			break;
		}
	}
	
	public Risk getRisk(){
		Level happy = getLevel(HAPPY);
		Level danger = getLevel(DANGER);
		if (Risk.NONE.factor*happy.factor == danger.factor){
			return Risk.NONE;
		}
		else if(Risk.VERYBAD.factor*danger.factor > happy.factor){
			return Risk.VERYBAD;
		}
		else if(Risk.BAD.factor*danger.factor > happy.factor){
			return Risk.BAD;
		}
		else if(Risk.VERYGOOD.factor*happy.factor > danger.factor){
			return Risk.VERYGOOD;
		}
		else if(Risk.GOOD.factor*happy.factor > danger.factor){
			return Risk.GOOD;
		}
		else 
			return Risk.EVEN;
	}
	
	public Level getLevel(boolean isDangerous){
		int tiles = getNumTiles();
		int totalMaxDanger = 0;
		int totalMinDanger = 0;
		for (SeaLifePrototype s: seaLifePossibilites){
			if (s.isDangerous() && isDangerous){
				totalMaxDanger += s.getHappiness()*s.getMaxCount();
				totalMinDanger += s.getHappiness()*s.getMinCount();
			}
		}
		int averageDanger = (totalMaxDanger + totalMinDanger) / 2;
		int varianceNumerator = (totalMaxDanger - averageDanger)*(totalMaxDanger - averageDanger) + (totalMinDanger - averageDanger)*(totalMinDanger - averageDanger);
		int variance = varianceNumerator / 2;
		if (variance > averageDanger * Level.MAYBE.getFactor()){	
			return Level.MAYBE;
		}
		else if ((averageDanger/tiles) <=  Level.NONE.getFactor()){
			return Level.NONE;
		}
		else if ((averageDanger/tiles) <=  Level.LOW.getFactor()){
			return Level.LOW;
		}
		else if ((averageDanger/tiles) <=  Level.MEDIUM.getFactor()){
			return Level.MEDIUM;
		}
		else if ((averageDanger/tiles) <=  Level.HIGH.getFactor()){
			return Level.HIGH;
		}
		else
			return Level.OMG;
	}
		
	public String composeMessage(){
		return null;
	}	
	
	public int getNumTiles(){
		return (d + d + 1)*(d + d + 1);
	}
}
