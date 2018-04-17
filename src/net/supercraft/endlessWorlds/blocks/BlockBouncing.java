package net.supercraft.endlessWorlds.blocks;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;
import net.supercraft.jojoleproUtils.math.PointXY;

public class BlockBouncing extends Block{

	public BlockBouncing(EndlessWorlds ed) {
		super(ed);
		this.setTexture("assets/blocks/Bouncing.png");
	}

	@Override
	public void onWalk(Entity entity) {
	}

	@Override
	public void onCollision(Entity entity) {
		entity.setvSpeed(-35);
	}
	
}
