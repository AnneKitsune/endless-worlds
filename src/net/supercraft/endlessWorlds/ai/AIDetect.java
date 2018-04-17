package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Mob;
import net.supercraft.jojoleproUtils.math.MathUtils;
import net.supercraft.jojoleproUtils.math.PointXY;

public class AIDetect extends AIMobBase{
	private PointXY detectTo;
	private float radius;
	
	public AIDetect(EndlessWorlds ed,Mob mob,PointXY detectTo,float radius){
		super(ed,mob);
		this.detectTo = detectTo;
		this.radius = radius;
	}
	public void update(float tpf){
		if(this.getDistance(new PointXY(mob.getPos().getX()+(mob.getSize().getX()/2),mob.getPos().getY()+(mob.getSize().getY()/2)),detectTo)<=radius){
			
			for(int i=0;i<mob.getAIList().size();i++){
				if(mob.getAIList().get(i) instanceof IAIDetect){
					IAIDetect ai = (IAIDetect)mob.getAIList().get(i);
					ai.detected(detectTo);
				}
			}
		}else{
			for(int i=0;i<mob.getAIList().size();i++){
				if(mob.getAIList().get(i) instanceof IAIDetect){
					IAIDetect ai = (IAIDetect)mob.getAIList().get(i);
					ai.notDetected(detectTo);
				}
			}
		}
		detectTo = new PointXY(ed.getModuleEntity().getPlayer().getPos().getX()+ed.getModuleEntity().getPlayer().getSize().getX(),ed.getModuleEntity().getPlayer().getPos().getY()+ed.getModuleEntity().getPlayer().getSize().getY());
	}
	//MOVE TO JOJOLEPROUTILS LATER!
	public float getDistance(PointXY from,PointXY to){
		//System.out.println((float)Math.sqrt(Math.pow(to.getX()-from.getX(), 2)+Math.pow(to.getY()-from.getY(), 2)));
		return (float)Math.sqrt(Math.pow(to.getX()-from.getX(), 2)+Math.pow(to.getY()-from.getY(), 2));
	}
}
