package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Mob;

public class AIRandomJump extends AIMobBase{
	private float delay = 0f;
	private float lastJump = 0;
	public AIRandomJump(EndlessWorlds ed, Mob mob) {
		super(ed, mob);
	}
	
	public void update(float tpf) {
		lastJump+=tpf;
		if(lastJump>=delay){
			ed.getModuleMovement().jump(mob);
		}
	}

	public float getDelay() {
		return delay;
	}

	public AIRandomJump setDelay(float delay) {
		this.delay = delay;
		return this;
	}
	
}
