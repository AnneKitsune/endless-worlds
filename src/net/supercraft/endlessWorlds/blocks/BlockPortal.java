package net.supercraft.endlessWorlds.blocks;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;

public class BlockPortal extends Block{
	public BlockPortal(EndlessWorlds ed){
		super(ed);
		this.isHardBlock = false;
		this.setTexture("assets/blocks/portalTemp.png");
	}

	@Override
	public void onWalk(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollision(Entity entity) {
		if(entity.equals(ed.getModuleEntity().getPlayer())){
			System.out.println("BlockPortal onCollision() Triggered!");
			ed.getWorld().getCurrentGamemode().onLevelChangeRequest();
		}
	}
}
