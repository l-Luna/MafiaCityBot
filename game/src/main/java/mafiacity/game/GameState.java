package mafiacity.game;

import java.util.*;

public final class GameState{
	
	/**
	 * The current time in phases. Day 1 = 0, Night 1 = 1, Day 2 = 2, Night 2 = 3, etc.
	 */
	private int time = 0;
	/**
	 * The set of dead and alive players.
	 */
	private final List<Player> players = new ArrayList<>();
	/**
	 * The actions chosen by living players.
	 */
	private final Map<Player, ResolvedAction> chosenActions = new HashMap<>();
	
	// setup
	
	/**
	 * Add a new player to the game, alive and with no statues.
	 */
	public Player introducePlayer(String name, Role role){
		var player = new Player(name, role, this);
		players.add(player);
		return player;
	}
	
	// making things happen
	
	/**
	 * Commit an action for a player. When {@linkplain GameState#advance} is next called, all committed actions will be performed.
	 * <p>
	 * If the player has already committed an action, calling this again will replace that action.
	 */
	public void commitAction(Player p, ResolvedAction a){
		chosenActions.put(p, a);
	}
	
	public boolean commitAction(String playerName, String actionName, String actionParams){
		if(find(playerName) instanceof Player p){
			Optional<Action> action = p.role.actions(this, p).stream().filter(a -> a.id().equals(actionName)).findFirst();
			if(action.isPresent()){
				ResolvedAction resolved = action.get().resolve(actionParams);
				if(resolved != null){
					commitAction(p, resolved);
					return true;
				}
			}
		}
		return false;
	}
	
	public void advance(){
		List<Map.Entry<Player, ResolvedAction>> list = chosenActions
				.entrySet().stream()
				.sorted(Comparator.comparingInt(x -> x.getValue().priority()))
				.toList();
		for(Map.Entry<Player, ResolvedAction> action : list)
			if(!action.getKey().blocked)
				action.getValue().onSelected().run();
		
		chosenActions.clear();
		time++;
		for(Player player : players)
			player.reset();
	}
	
	// helpers
	
	public Player find(String name){
		name = name.toLowerCase().trim();
		for(Player player : players)
			if(player.name.equals(name))
				return player;
		return null;
	}
	
	public int time(){
		return time;
	}
	
	public boolean isDay(){
		return time % 2 == 0;
	}
	
	public boolean isNight(){
		return time % 2 == 1;
	}
	
	public int dayIdx(){
		return time / 2;
	}
}