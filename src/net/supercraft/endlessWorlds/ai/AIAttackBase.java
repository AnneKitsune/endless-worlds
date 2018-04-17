package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;
import net.supercraft.endlessWorlds.entity.Mob;

public abstract class AIAttackBase extends AIMobBase{
	Entity target;
	public AIAttackBase(EndlessWorlds ed,Mob mob,Entity target){
		super(ed,mob);
		this.target = target;
	}
	public abstract void attack(Entity target);
}
