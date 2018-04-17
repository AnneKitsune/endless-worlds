package net.supercraft.endlessWorlds.effect;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.IUpdating;
import net.supercraft.endlessWorlds.entity.Entity;

public abstract class Effect implements IUpdating{
	protected EndlessWorlds ed;
	protected float timeout = 0;
	protected String name = "DEFAULT_EFFECT";
	protected Entity entity;
	protected boolean isFinish = false;
	
	public Effect(EndlessWorlds ed,String name,float timeout, Entity entity){
		this.ed = ed;
		this.name = name;
		this.timeout = timeout;
		this.entity = entity;
	}
	
	public abstract void onStart();
	public abstract void onTimeout();
	public abstract void update(float tpf);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getTimeout() {
		return timeout;
	}
	public boolean isFinish(){
		return isFinish;
	}
	
}
