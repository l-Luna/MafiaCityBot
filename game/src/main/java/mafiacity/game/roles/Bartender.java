package mafiacity.game.roles;

import mafiacity.game.Action;
import mafiacity.game.GameState;
import mafiacity.game.Player;
import mafiacity.game.Role;

import java.util.List;

public class Bartender implements Role{
	
	public String id(){
		return "bartender";
	}
	
	public String name(){
		return "Bartender";
	}
	
	public List<Action> actions(GameState state, Player p){
		return List.of(
			targeted("bartender:drink_up", Action.Priorities.BLOCK, state, target -> {
				p.visit(target);
				target.blocked = true;
			})
			// another round...
		);
	}
}
