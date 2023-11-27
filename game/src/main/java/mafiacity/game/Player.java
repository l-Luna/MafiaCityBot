package mafiacity.game;

import java.util.*;

public final class Player{
	
	public String name;
	public Role role;
	// discord id...
	
	public final List<Status> statuses = new ArrayList<>();
	public final Map<Integer, Set<Player>> visitLog = new HashMap<>();
	
	public boolean blocked = false;
	public int protection = 0;
	
	// helpers
	
	public void visit(Player target, int time){
		visitLog.computeIfAbsent(time, __ -> new HashSet<>()).add(target);
	}
}