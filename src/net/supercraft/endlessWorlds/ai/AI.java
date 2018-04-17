package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.IUpdating;

public abstract class AI implements IUpdating{
	protected EndlessWorlds ed;
	public AI(EndlessWorlds ed){
		this.ed = ed;
	}
	
	public abstract void update(float tpf);
	
}
