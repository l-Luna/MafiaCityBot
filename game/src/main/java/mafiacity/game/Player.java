package mafiacity.game;

import java.util.*;

public final class Player{
	
	// defining, roughly immutable characteristics
	public String name;
	public Role role;
	private final GameState state;
	
	public boolean alive = true;
	
	public Player(String name, Role role, GameState state){
		this.name = name;
		this.role = role;
		this.state = state;
	}
	
	// general extra attributes
	public Map<String, String> userdata = new HashMap<>();
	
	// persistent across nights, but changes as the game progresses
	public final List<Status> statuses = new ArrayList<>();
	public final Map<Integer, Set<Player>> visitLog = new HashMap<>();
	
	// transient and exists only for the current night
	public boolean blocked = false;
	public int protection = 0;
	
	public void reset(){
		blocked = false;
		protection = 0;
		role.applyPassives(this);
	}
	
	// helpers
	
	public void kill(int attack){
		if(protection <= attack)
			alive = false;
		// notify...
	}
	
	public void visit(Player target){
		visitLog.computeIfAbsent(state.time(), __ -> new HashSet<>()).add(target);
	}
}