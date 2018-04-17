package net.supercraft.endlessWorlds.blocks;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;

public class BlockIce extends Block{

	public BlockIce(EndlessWorlds ed) {
		super(ed);
		this.setTexture("assets/blocks/Ice.png");
		this.setFriction(0.1f);
	}

	@Override
	public void onWalk(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollision(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}
