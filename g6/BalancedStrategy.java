package isnork.g6;

import java.util.Set;

import isnork.sim.GameObject.Direction;
import isnork.sim.SeaLifePrototype;

public class BalancedStrategy extends Strategy {

	public BalancedStrategy(Set<SeaLifePrototype> seaLifePossibilites,
			int penalty, int d, int r, int n) {
		super(seaLifePossibilites, penalty, d, r, n);
	}

	@Override
	public Direction nextMove() {
		return null;
	}

}
