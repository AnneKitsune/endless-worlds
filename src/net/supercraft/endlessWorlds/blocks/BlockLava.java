package net.supercraft.endlessWorlds.blocks;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;

public class BlockLava extends Block{

	public BlockLava(EndlessWorlds ed) {
		super(ed);
		this.setTexture("assets/blocks/Lava.png");
	}
	public void onWalk(Entity entity) {
		
	}
	public void onCollision(Entity entity) {
		entity.damage(50000000);
	}
}
