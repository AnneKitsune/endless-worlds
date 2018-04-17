package net.supercraft.endlessWorlds.blocks;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;

public class BlockRock extends Block{

	public BlockRock(EndlessWorlds ed) {
		super(ed);
		this.setTexture("assets/blocks/Rock.png");
	}

	@Override
	public void onWalk(Entity entity) {
	}

	@Override
	public void onCollision(Entity entity) {
	}

}
