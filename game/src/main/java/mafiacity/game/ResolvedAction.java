package mafiacity.game;

public record ResolvedAction(int priority, Runnable onSelected){}