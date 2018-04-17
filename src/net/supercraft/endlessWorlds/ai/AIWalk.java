package net.supercraft.endlessWorlds.ai;

import java.util.Random;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.Direction;
import net.supercraft.endlessWorlds.engine.ModuleMovement;
import net.supercraft.endlessWorlds.entity.Mob;

public class AIWalk extends AIMobBase{
	private Direction direction;
	public AIWalk(EndlessWorlds ed,Mob mob) {
		super(ed,mob);
		this.generateDir();
	}
	private void generateDir(){
		Random rand = new Random();
		int n = rand.nextInt(2);
		if(n==0){
			direction = Direction.LEFT;
		}else{
			direction = Direction.RIGHT;
		}
	}
	public void update(float tpf){
		ModuleMovement.move(direction, mob, mob.getSpeed());
	}
	
	public Direction getDirection(){
		return direction;
	}
	public void setDirection(Direction dir){
		this.direction = dir;
	}
	
}
