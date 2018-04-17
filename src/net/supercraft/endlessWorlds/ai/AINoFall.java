package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.Direction;
import net.supercraft.endlessWorlds.engine.ModuleMovement;
import net.supercraft.endlessWorlds.entity.Mob;
import net.supercraft.jojoleproUtils.math.PointXY;

/**
 * Needs AIWalk to work!
 * @author jojolepro
 *
 */
public class AINoFall extends AIMobBase{
	private float maxFall = 1;
	private AIWalk aiWalk;
	public AINoFall(EndlessWorlds ed,Mob mob,float maximumFallHeight){
		super(ed,mob);
		this.maxFall = maximumFallHeight;
		for(int i=0;i<mob.getAIList().size();i++){
			if(mob.getAIList().get(i).getClass().equals(AIWalk.class)){
				this.aiWalk = (AIWalk)mob.getAIList().get(i);
			}
		}
		if(aiWalk==null){
			System.err.println("Error: AINoFall was implemented but the mob don't have AIWalk implemented! AI will not work!");
		}
	}
	public void update(float tpf){
		if(aiWalk!=null && mob.getvSpeed()==0){
			if(aiWalk.getDirection().equals(Direction.RIGHT)){
				if(ModuleMovement.checkCollisions(new PointXY(mob.getPos().getX()+mob.getSpeed()+1,mob.getPos().getY()+maxFall+1),new PointXY(20,20), 0).isEmpty()){
					aiWalk.setDirection(Direction.LEFT);
				}
			}else{
				if(ModuleMovement.checkCollisions(new PointXY(mob.getPos().getX()-mob.getSpeed()-1,mob.getPos().getY()+maxFall+1),new PointXY(20,20), 0).isEmpty()){
					aiWalk.setDirection(Direction.RIGHT);
				}	
			}
		}
	}
}
