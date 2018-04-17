package net.supercraft.endlessWorlds.entity;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.ai.AIAttackMelee;
import net.supercraft.endlessWorlds.ai.AIRandomJump;
import net.supercraft.endlessWorlds.ai.AIWalk;
import net.supercraft.endlessWorlds.animation.AnimationSet;
import net.supercraft.jojoleproUtils.math.PointXY;

public class MobSuperBall extends Mob{
	public MobSuperBall(EndlessWorlds ed){
		super(ed);
		this.setHealth(3);
		this.setSpeed(4f);
		this.setJumpForce(20);
		this.setStrengh(15f);
		this.setTexture();
		this.setMeleeAttackRange(22);
		this.setSize(new PointXY(20,20));
		
		this.addAI(new AIWalk(ed,this));
		this.addAI(new AIAttackMelee(ed, this, ed.getModuleEntity().getPlayer()));
		this.addAI(new AIRandomJump(ed,this).setDelay(100));
	}

	@Override
	public AnimationSet[] getEnabledAnimationSets() {
		// TODO Auto-generated method stub
		return null;
	}
}
