package mafiacity.game;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Role{
	
	String id();
	String name();
	
	List<Action> actions(GameState state, Player p);
	
	default void applyPassives(Player p){}
	
	// helpers
	
	default Action invariant(String id, int priority, Runnable onSelected){
		return new Action(id, __ -> new ResolvedAction(priority, onSelected));
	}
	
	default Action targeted(String id, int priority, GameState state, Consumer<Player> onSelected){
		return new Action(id, text -> {
			Player target = state.find(text);
			return new ResolvedAction(priority, () -> onSelected.accept(target));
		});
	}
	
	default Action validTargeted(String id, int priority, GameState state, Predicate<Player> pred, Consumer<Player> onSelected){
		return new Action(id, text -> {
			Player target = state.find(text);
			return pred.test(target) ? new ResolvedAction(priority, () -> onSelected.accept(target)) : null;
		});
	}
}