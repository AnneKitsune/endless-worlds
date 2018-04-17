package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Mob;

public abstract class AIMobBase extends AI{
	protected Mob mob;
	public AIMobBase(EndlessWorlds ed, Mob mob){
		super(ed);
		this.mob = mob;
	}
	
}
