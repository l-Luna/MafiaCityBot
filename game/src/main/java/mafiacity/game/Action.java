package mafiacity.game;

import java.util.function.Function;

public record Action(String id, Function<String, ResolvedAction> resolution){
	
	public ResolvedAction resolve(String text){
		return resolution.apply(text);
	}
}
