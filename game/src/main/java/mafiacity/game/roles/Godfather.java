package mafiacity.game.roles;

import mafiacity.game.*;

import java.util.List;

public class Godfather implements Role{
	
	public String id(){
		return "godfather";
	}
	
	public String name(){
		return "Godfather";
	}
	
	public List<Action> actions(GameState state, Player p){
		return List.of(
			targeted("godfather:kill", Action.Priorities.KILL, state, target -> {
				p.visit(target);
				target.kill(1);
			})
		);
	}
	
	public void applyPassives(Player p){
		p.protection = 1;
	}
}