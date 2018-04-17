package net.supercraft.endlessWorlds.engine;

public enum Direction {
	UP,
	DOWN,
	LEFT,
	RIGHT;
	public static Direction getDir(int dir){
		switch(dir){
		case 0: return Direction.UP;
		case 1: return Direction.DOWN;
		case 2: return Direction.LEFT;
		case 3: return Direction.RIGHT;
		default: return null;
		}
	}
}
