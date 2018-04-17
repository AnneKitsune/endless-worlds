package net.supercraft.endlessWorlds.blocks;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;

public class BlockGrass extends Block{
	
	public BlockGrass(EndlessWorlds ed) {
		super(ed);
		this.setTexture("assets/blocks/Dirt.png");
	}
	public void updateDisplay(){
		//this.setRotation(48);
		//this.scaleX(75);
		//this.scaleY(75);
	}
	@Override
	public void onWalk(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCollision(Entity entity) {
	}
}
