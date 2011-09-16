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
	
	Strategy myStrategy;
	CreatureTracker myTracker;

	@Override
	public String getName() {
		return "G9: New Player";
	}

	@Override
	public void newGame(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
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
	
	public Strategy decideStrategy(int dangerIndex, int numPlayers, int d){
		return null;
	}
	
	public int getBoardDanger(Set<SeaLifePrototype> seaLifePossibilites, int d, int r){
		return 0; //0-100 Happy to Dangerous
	}
	
	public String composeMessage(){
		return null;
	}
	
}
