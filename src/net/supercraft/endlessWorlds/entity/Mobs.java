package net.supercraft.endlessWorlds.entity;

import net.supercraft.endlessWorlds.EndlessWorlds;

public enum Mobs {
	TURTLE(new MobTurtle(EndlessWorlds.getGame())),
	BALL(new MobBall(EndlessWorlds.getGame())),
	SUPERBALL(new MobSuperBall(EndlessWorlds.getGame()));
	
	private Mob mob;
	private Mobs(Mob mob){
		this.mob = mob;
	}
	public Mob getMob() {
		return mob;
	}
}
