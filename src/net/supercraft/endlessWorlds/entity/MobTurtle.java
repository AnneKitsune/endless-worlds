package net.supercraft.endlessWorlds.entity;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.ai.AIAttackMelee;
import net.supercraft.endlessWorlds.ai.AINoFall;
import net.supercraft.endlessWorlds.ai.AIWalk;
import net.supercraft.endlessWorlds.animation.AnimationSet;
import net.supercraft.jojoleproUtils.math.PointXY;

public class MobTurtle extends Mob{
	public MobTurtle(EndlessWorlds ed){
		super(ed);
		this.setHealth(1);
		this.setSpeed(1f);//0.5
		this.setJumpForce(0);
		this.setStrengh(0.5f);
		this.setTexture();
		this.setMeleeAttackRange(50);
		this.setSize(new PointXY(20,20));
		
		this.addAI(new AIWalk(ed,this));
		this.addAI(new AINoFall(ed,this,50));
		this.addAI(new AIAttackMelee(ed, this, ed.getModuleEntity().getPlayer()));
		
	}

	@Override
	public AnimationSet[] getEnabledAnimationSets() {
		AnimationSet[] enabledAnimationSet = {AnimationSet.NORMAL};
		return enabledAnimationSet;
	}
	
}
