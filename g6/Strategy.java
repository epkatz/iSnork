package isnork.g6;

import java.util.Set;

import isnork.sim.SeaLifePrototype;
import isnork.sim.GameObject.Direction;

public abstract class Strategy {
	
	public static Set<SeaLifePrototype> seaLifePossibilites;
	public static int penality;
	public static int d;
	public static int r;
	public static int n;
	public NewPlayer player;

	public Strategy(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n, NewPlayer p){
		Strategy.seaLifePossibilites = seaLifePossibilites;
		Strategy.penality = penalty;
		Strategy.d = d;
		Strategy.r = r;
		Strategy.n = n;
		this.player = p;
	}
	
	public abstract Direction nextMove();
		
}
