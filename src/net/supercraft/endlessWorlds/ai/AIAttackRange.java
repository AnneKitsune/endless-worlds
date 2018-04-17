package net.supercraft.endlessWorlds.ai;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Entity;
import net.supercraft.endlessWorlds.entity.EntityArrow;
import net.supercraft.endlessWorlds.entity.Mob;
import net.supercraft.jojoleproUtils.math.PointXY;

public class AIAttackRange extends AIAttackBase{
	float lastAttack=0;
	public AIAttackRange(EndlessWorlds ed, Mob mob, Entity target) {
		super(ed, mob, target);
	}
	public void update(float tpf){
		if(lastAttack>=mob.getAttackSpeed()){
			this.attack(target);
		}
		lastAttack+=tpf/1000;//setting in seconds
	}
	
	public void attack(Entity entity) {
		if(this.getDistance(new PointXY(mob.getPos().getX()+(mob.getSize().getX()/2),mob.getPos().getY()+(mob.getSize().getY()/2)),new PointXY(target.getPos().getX()+(target.getSize().getX()/2),target.getPos().getY()+(target.getSize().getY()/2)))<=mob.getMeleeAttackRange()){
			//mob.animate(Animations.ATTACKRANGE);
			ed.getWorld().getLevel().getEntityList().add(new EntityArrow(ed,new PointXY(mob.getPos().getX()+mob.getSize().getX()/2,mob.getPos().getY()+mob.getSize().getY()/2),new PointXY(target.getPos().getX()+target.getSize().getX()/2,target.getPos().getY()+target.getSize().getY()/2),mob.getAttackSpeed()));
			lastAttack=0;
		}
	}
	public float getDistance(PointXY from,PointXY to){
		//System.out.println((float)Math.sqrt(Math.pow(to.getX()-from.getX(), 2)+Math.pow(to.getY()-from.getY(), 2)));
		return (float)Math.sqrt(Math.pow(to.getX()-from.getX(), 2)+Math.pow(to.getY()-from.getY(), 2));
	}

}
