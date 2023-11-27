package mafiacity.game.roles;

import mafiacity.game.*;

import java.util.List;
import java.util.function.Function;

public class Jester implements Role{
	
	public String id(){
		return "jester";
	}
	
	public String name(){
		return "Jester";
	}
	
	public List<Action> actions(GameState state, Player p){
		return List.of(
				targeted("prank", ResolvedAction.Priorities.DEDUCE, state, target -> p.visit(target, state.time()))
		);
	}
}
