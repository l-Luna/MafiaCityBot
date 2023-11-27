package mafiacity.game;

import java.util.function.Function;

public record Action(String id, Function<String, ResolvedAction> resolution){
	
	public ResolvedAction resolve(String text){
		return resolution.apply(text);
	}
	
	public static final class Priorities{
		
		public static final int
				BLOCK = 0,
				DEFEND = 10,
				DEDUCE = 20,
				KILL = 30,
				TRACK = 40;
	}
}
