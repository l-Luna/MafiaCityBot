package mafiacity.game.roles;

import mafiacity.game.*;

import java.util.List;

public class Jester implements Role{
	
	public String id(){
		return "jester";
	}
	
	public String name(){
		return "Jester";
	}
	
	public List<Action> actions(GameState state, Player p){
		if(state.isNight())
			return List.of(
					targeted("jester:prank", Action.Priorities.DEDUCE, state, p::visit)
			);
		return List.of();
	}
}
