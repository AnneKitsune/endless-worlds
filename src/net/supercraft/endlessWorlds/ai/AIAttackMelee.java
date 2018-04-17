package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.animation.Animation;
import net.supercraft.endlessWorlds.engine.IUpdating;
import net.supercraft.endlessWorlds.entity.Entity;
import net.supercraft.endlessWorlds.entity.Mob;
import net.supercraft.jojoleproUtils.math.PointXY;

public class AIAttackMelee extends AIAttackBase implements IUpdating, IAIDetect{
	float lastAttack=0;
	public AIAttackMelee(EndlessWorlds ed, Mob mob, Entity target){
		super(ed,mob,target);
	}
	public void update(float tpf){
		if(lastAttack>=mob.getAttackSpeed()){
			this.attack(target);
		}
		lastAttack+=tpf/1000;//setting in seconds
	}
	
	public void attack(Entity entity) {
		if(this.getDistance(new PointXY(mob.getPos().getX()+(mob.getSize().getX()/2),mob.getPos().getY()+(mob.getSize().getY()/2)),new PointXY(target.getPos().getX()+(target.getSize().getX()/2),target.getPos().getY()+(target.getSize().getY()/2)))<=mob.getMeleeAttackRange()){
			//mob.animate(Animations.ATTACK);
			target.damage(mob.getStrengh());
			lastAttack=0;
			Animation.ATTACK.animate(ed, mob, 500f, false);
		}
	}
	//MOVE TO JOJOLEPROUTILS LATER!
	public float getDistance(PointXY from,PointXY to){
		//System.out.println((float)Math.sqrt(Math.pow(to.getX()-from.getX(), 2)+Math.pow(to.getY()-from.getY(), 2)));
		return (float)Math.sqrt(Math.pow(to.getX()-from.getX(), 2)+Math.pow(to.getY()-from.getY(), 2));
	}
	@Override
	public void detected(PointXY pos) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void notDetected(PointXY pos) {
		// TODO Auto-generated method stub
		
	}
}
