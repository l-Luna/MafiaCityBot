package mafiacity.game;

import mafiacity.game.roles.Bartender;
import mafiacity.game.roles.Godfather;
import mafiacity.game.roles.Jester;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBasic{
	
	@Test
	void testBasic(){
		// build a state and add some players
		GameState state = new GameState();
		Player aiden = state.introducePlayer("aiden", new Bartender()),
				juckter = state.introducePlayer("juckter", new Godfather()),
				luna = state.introducePlayer("luna", new Jester());
		
		// setup some actions and continue the night
		state.commitAction("aiden", "bartender:drink_up", "juckter");
		state.commitAction("juckter", "godfather:kill", "luna");
		state.commitAction("luna", "jester:prank", "aiden");
		state.advance();
		
		// everyone should still be alive
		assertTrue(aiden.alive);
		assertTrue(juckter.alive);
		assertTrue(luna.alive);
		
		// aiden and luna should have visited, but juckter should have not
		assertTrue(aiden.visitLog.containsKey(0) && aiden.visitLog.get(0).contains(juckter));
		assertTrue(luna.visitLog.containsKey(0) && luna.visitLog.get(0).contains(aiden));
		assertTrue(!juckter.visitLog.containsKey(0) || juckter.visitLog.get(0).isEmpty());
		
		// now juckter kills luna, and nothing else happens
		state.commitAction("juckter", "godfather:kill", "luna");
		state.advance();
		
		// luna should be dead, and nobody else
		assertTrue(aiden.alive);
		assertTrue(juckter.alive);
		assertFalse(luna.alive);
		
		// juckter should have visited, and nobody else
		assertTrue(!aiden.visitLog.containsKey(1) || aiden.visitLog.get(1).isEmpty());
		assertTrue(!luna.visitLog.containsKey(1) || luna.visitLog.get(1).isEmpty());
		assertTrue(juckter.visitLog.containsKey(1) && juckter.visitLog.get(1).contains(luna));
	}
}