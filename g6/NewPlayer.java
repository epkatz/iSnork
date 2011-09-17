package isnork.g6;

import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.Player;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Set;

public class NewPlayer extends Player {
	
	private Strategy myStrategy;
	private CreatureTracker myTracker;
	public enum Danger {
		NONE (0.0), LOW (1.0), MEDIUM (5.0), MAYBE (0.2), HIGH (10.0), FML (20.0);
		private final double factor;
		Danger(double factor){
			this.factor = factor;
		}
		public double getFactor(){
			return factor;
		}
	}
	private static int d;
	private static int r;
	private static int n;
	private static Set<SeaLifePrototype> seaLifePossibilites;
	private static int penality;

	@Override
	public String getName() {
		return "G9: New Player";
	}

	@Override
	public void newGame(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
		NewPlayer.seaLifePossibilites = seaLifePossibilites;
		NewPlayer.penality = penalty;
		NewPlayer.d = d;
		NewPlayer.r = r;
		NewPlayer.n = n;
		//int dangerIndex = getBoardDanger();
		//myStrategy = decideStrategy(seaLifePossibilites, penality, d, r, n);
		//creatureTracker = new CreatureTracker();
	}

	@Override
	public String tick(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		return null;
	}

	@Override
	public Direction getMove() {
		System.out.println("My id is: " + this.getId());
		return myStrategy.nextMove();
	}
	
	public Strategy decideStrategy(){
		return null;
	}
	
	public Danger getBoardDanger(){
		int tiles = getNumTiles();
		int totalMaxDanger = 0;
		int totalMinDanger = 0;
		for (SeaLifePrototype s: seaLifePossibilites){
			if (s.isDangerous()){
				totalMaxDanger += s.getHappiness()*s.getMaxCount();
				totalMinDanger += s.getHappiness()*s.getMinCount();
			}
		}
		int averageDanger = (totalMaxDanger + totalMinDanger) / 2;
		int varianceNumerator = (totalMaxDanger - averageDanger)*(totalMaxDanger - averageDanger) + (totalMinDanger - averageDanger)*(totalMinDanger - averageDanger);
		int variance = varianceNumerator / 2;
		if (variance > averageDanger * Danger.MAYBE.getFactor()){	
			return Danger.MAYBE;
		}
		else if ((averageDanger/tiles) <=  Danger.NONE.getFactor()){
			return Danger.NONE;
		}
		else if ((averageDanger/tiles) <=  Danger.LOW.getFactor()){
			return Danger.LOW;
		}
		else if ((averageDanger/tiles) <=  Danger.MEDIUM.getFactor()){
			return Danger.MEDIUM;
		}
		else if ((averageDanger/tiles) <=  Danger.HIGH.getFactor()){
			return Danger.HIGH;
		}
		else
			return Danger.FML;
	}
		
	public String composeMessage(){
		return null;
	}	
	
	public int getNumTiles(){
		return (d + d + 1)*(d + d + 1);
	}
}
