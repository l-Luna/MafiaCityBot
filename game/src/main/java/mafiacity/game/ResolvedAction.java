package mafiacity.game;

public record ResolvedAction(int priority, Runnable onSelected){
	
	public static final class Priorities{
		
		public static final int
				DEFEND = 0,
				DEDUCE = 10,
				KILL = 20,
				TRACK = 30;
	}
}