package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.Direction;
import net.supercraft.endlessWorlds.entity.Mob;
import net.supercraft.jojoleproUtils.math.PointXY;

public class AIRunAway extends AI{
	protected Mob mob;
	protected PointXY target;
	protected AIWalk aiWalk;
	public AIRunAway(EndlessWorlds ed,Mob mob, PointXY target){
		super(ed);
		this.mob = mob;
		this.target = target;
		for(int i=0;i<mob.getAIList().size();i++){
			if(mob.getAIList().get(i).getClass().equals(AIWalk.class)){
				this.aiWalk = (AIWalk)mob.getAIList().get(i);
			}
		}
		if(aiWalk==null){
			System.err.println("Error: AINoFall was implemented but the mob don't have AIWalk implemented! AI will not work!");
		}
	}
	
	public void update(float tpf) {
		if(mob.getPos().getX()>target.getX()){
			if(aiWalk.getDirection()==Direction.LEFT){
				aiWalk.setDirection(Direction.RIGHT);
			}
		}else if(mob.getPos().getX()<target.getX()){
			if(aiWalk.getDirection()==Direction.RIGHT){
				aiWalk.setDirection(Direction.LEFT);
			}
		}
	}
}
