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
		
		// skip day 1
		state.advance();
		
		// setup some actions and continue the night
		assertTrue(state.commitAction("aiden", "bartender:drink_up", "juckter"));
		assertTrue(state.commitAction("juckter", "godfather:kill", "luna"));
		assertTrue(state.commitAction("luna", "jester:prank", "aiden"));
		state.advance();
		
		// everyone should still be alive
		assertTrue(aiden.alive);
		assertTrue(juckter.alive);
		assertTrue(luna.alive);
		
		// aiden and luna should have visited, but juckter should have not
		assertTrue(aiden.visitLog.containsKey(1) && aiden.visitLog.get(1).contains(juckter));
		assertTrue(luna.visitLog.containsKey(1) && luna.visitLog.get(1).contains(aiden));
		assertTrue(!juckter.visitLog.containsKey(1) || juckter.visitLog.get(1).isEmpty());
		
		// skip day 2
		state.advance();
		
		// now juckter kills luna, and nothing else happens
		assertTrue(state.commitAction("juckter", "godfather:kill", "luna"));
		state.advance();
		
		// luna should be dead, and nobody else
		assertTrue(aiden.alive);
		assertTrue(juckter.alive);
		assertFalse(luna.alive);
		
		// juckter should have visited, and nobody else
		assertTrue(!aiden.visitLog.containsKey(3) || aiden.visitLog.get(3).isEmpty());
		assertTrue(!luna.visitLog.containsKey(3) || luna.visitLog.get(3).isEmpty());
		assertTrue(juckter.visitLog.containsKey(3) && juckter.visitLog.get(3).contains(luna));
	}
}