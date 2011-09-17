package isnork.g6.tests;

import isnork.g6.NewPlayer;
import isnork.g6.NewPlayer.Danger;
import isnork.sim.GameConfig;
import isnork.sim.SeaLifePrototype;

import java.io.File;
import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Before;

public class TestNewPlayer extends TestCase {

	NewPlayer player;
	HashSet<SeaLifePrototype> protos;
	int d = 20;
	int r = 5;
	int penalty = 0;
	int n = 20;

	@Before
	public void setUp() throws Exception {
		player = new NewPlayer();
	}
	
	public void setupBoard(File f){
		protos = new HashSet<SeaLifePrototype>();
		GameConfig config = new GameConfig();
		config.setSelectedBoard(f);
		config.load();
		for (SeaLifePrototype t : config.getSeaLifePrototypes()) {
			protos.add((SeaLifePrototype) t.clone());
		}
		player.newGame(protos, penalty, d, r, n);
	}

	public void testGetBoardFML() {
		setupBoard(new File("boards/Piranha.xml"));
		Danger myDanger = player.getBoardDanger();
		assertEquals(myDanger, Danger.FML);
	}
	
	public void testGetBoardNONE() {
		setupBoard(new File("boards/g5_very_happy.xml"));
		Danger myDanger = player.getBoardDanger();
		assertEquals(myDanger, Danger.NONE);
	}

}
