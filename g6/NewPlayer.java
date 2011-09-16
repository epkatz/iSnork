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

	@Override
	public String getName() {
		return "G9: New Player";
	}

	@Override
	public void newGame(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
		// TODO Auto-generated method stub

	}

	@Override
	public String tick(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		return null;
	}

	@Override
	public Direction getMove() {
		Direction d = Direction.N;
		Random r = new Random();
		switch(r.nextInt(4)){
		case 0:
			d = Direction.N;
			break;
		case 1:
			d = Direction.S;
			break;
		case 2:
			d = Direction.E;
			break;
		case 3:
			d = Direction.W;
			break;
		}
		return d;
	}

}
