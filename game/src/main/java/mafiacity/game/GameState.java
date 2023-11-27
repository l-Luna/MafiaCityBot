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
	private final List<Player> alive = new ArrayList<>(), dead = new ArrayList<>();
	/**
	 * The actions chosen by living players.
	 */
	private final Map<Player, ResolvedAction> chosenActions = new HashMap<>();
	
	// setup
	
	/**
	 * Add a new player to the game. They will be made alive and have no statues.
	 *
	 * @param p
	 * 		The player to introduce.
	 */
	public void introducePlayer(Player p){
		alive.add(p);
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
	}
	
	public void killPlayer(Player p){
		alive.remove(p);
		dead.add(p);
	}
	
	// helpers
	
	public Player find(String name){
		name = name.toLowerCase().trim();
		for(Player player : alive)
			if(player.name.equals(name))
				return player;
		for(Player player : dead)
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