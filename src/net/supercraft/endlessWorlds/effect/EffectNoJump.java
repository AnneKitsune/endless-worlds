package net.supercraft.endlessWorlds.effect;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;

public class EffectNoJump extends Effect{
	public EffectNoJump(EndlessWorlds ed, String name, float timeout, Entity entity) {
		super(ed, name, timeout, entity);
		this.onStart();
	}
	public void onStart() {
		entity.setJumpForce(0);
	}
	public void onTimeout() {
		entity.setJumpForce(entity.getDefaultJumpForce());
		this.isFinish = true;
	}
	public void update(float tpf) {
		entity.setJumpForce(0);
		timeout-=tpf/1000;
		if(timeout<=0){
			onTimeout();
		}
	}
}
