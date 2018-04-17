package net.supercraft.endlessWorlds.entity;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.animation.AnimationSet;
import net.supercraft.jojoleproUtils.math.PointXY;

public class EntityArrow extends Entity{
	
	public EntityArrow(EndlessWorlds ed,PointXY from, PointXY target, float speed) {
		super(ed);
	}

	@Override
	public AnimationSet[] getEnabledAnimationSets() {
		// TODO Auto-generated method stub
		return null;
	}
}
