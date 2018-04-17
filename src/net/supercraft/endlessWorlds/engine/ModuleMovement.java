package net.supercraft.endlessWorlds.engine;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.animation.AnimatedInstance;
import net.supercraft.endlessWorlds.animation.AnimationSet;
import net.supercraft.endlessWorlds.blocks.Block;
import net.supercraft.endlessWorlds.effect.EffectNoJump;
import net.supercraft.endlessWorlds.entity.Entity;
import net.supercraft.jojoleproUtils.math.PointXY;
import net.supercraft.jojoleproUtils.module.control.ConfigNotFoundException;
import net.supercraft.jojoleproUtils.module.control.Module;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.model.IKeyControllable;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;
import net.supercraft.jojoleproUtils.module.model.KeyState;

public class ModuleMovement extends Module implements IUpdatable, IKeyControllable{
	private EndlessWorlds ed;
	//private static boolean isAtGround = false;
	private static float gravity = 1f;
	private static float maxClimbHeight = 2f;
	private boolean[] moveKey = {false,false,false,false};
	private boolean sprint = false;
	public ModuleMovement(EndlessWorlds ed,ModuleManager moduleManager) {
		super(moduleManager);
		this.ed = ed;
	}
	
	public void update(float tpf) {
		if(moveKey[0]){
			jump(ed.getModuleEntity().getPlayer());
			AnimatedInstance animatedInstance = new AnimatedInstance(ed.getModuleEntity().getPlayer(),AnimationSet.JUMP,1,800);
			animatedInstance.setAutoRollback(true);
			ed.getModuleAnimation().addAnimation(animatedInstance);
		}
		if(moveKey[2]&&sprint&&ed.getModuleEntity().getPlayer().getEnergy()>=0){
			move(Direction.LEFT,ed.getModuleEntity().getPlayer(),ed.getModuleEntity().getPlayer().getSpeed()*(tpf/1000)*2);
			ed.getModuleEntity().getPlayer().setEnergy(ed.getModuleEntity().getPlayer().getEnergy()-13*(tpf/1000));//energy lost by sprinting
			ed.getModuleEntity().getPlayer().setFacingDirection(Direction.LEFT);
			ed.getModuleAnimation().addAnimation(new AnimatedInstance(ed.getModuleEntity().getPlayer(),AnimationSet.WALKINGLEFT,1,500));
		}else if(moveKey[2]){
			move(Direction.LEFT,ed.getModuleEntity().getPlayer(),ed.getModuleEntity().getPlayer().getSpeed()*(tpf/1000));
			ed.getModuleEntity().getPlayer().setFacingDirection(Direction.LEFT);
			ed.getModuleAnimation().addAnimation(new AnimatedInstance(ed.getModuleEntity().getPlayer(),AnimationSet.WALKINGLEFT,1,500));
		}
		if(moveKey[3]&&sprint&&ed.getModuleEntity().getPlayer().getEnergy()>=0){
			move(Direction.RIGHT,ed.getModuleEntity().getPlayer(),ed.getModuleEntity().getPlayer().getSpeed()*(tpf/1000)*2);
			ed.getModuleEntity().getPlayer().setEnergy(ed.getModuleEntity().getPlayer().getEnergy()-13*(tpf/1000));//energy lost by sprinting
			ed.getModuleEntity().getPlayer().setFacingDirection(Direction.RIGHT);
			ed.getModuleAnimation().addAnimation(new AnimatedInstance(ed.getModuleEntity().getPlayer(),AnimationSet.WALKINGRIGHT,1,500));
		}else if(moveKey[3]){
			move(Direction.RIGHT,ed.getModuleEntity().getPlayer(),ed.getModuleEntity().getPlayer().getSpeed()*(tpf/1000));
			ed.getModuleEntity().getPlayer().setFacingDirection(Direction.RIGHT);
			ed.getModuleAnimation().addAnimation(new AnimatedInstance(ed.getModuleEntity().getPlayer(),AnimationSet.WALKINGRIGHT,1,500));
		}
		
		for(int i=0;i<ed.getModuleEntity().getEntityList().size();i++){
			this.applyGravity(ed.getModuleEntity().getEntityList().get(i),gravity,tpf);
		}
		//this.applyGravity(ed.getModuleEntity().getPlayer(), gravity,tpf);
		ed.getDisplay().repaint();
	}
	public static boolean move(Direction direction,Entity entity, float amount){
		ArrayList<Block> collidingBlocks = new ArrayList<Block>();
		for(int i=0;i<Math.abs(amount);i++){
			if((collidingBlocks=checkCollisions(calculateNewPosition(direction,entity.getPos(),1),entity.getSize(),entity.getRotation())).isEmpty()){
				entity.setPos(calculateNewPosition(direction,entity.getPos(),1));
			}else{//Colliding
				for(int j=0;j<collidingBlocks.size();j++){//On collide HOOK  Need to repair this part!!
					for(int k=0;k<EndlessWorlds.getGame().getWorld().getLevel().getBlockList().size();k++){
						if(collidingBlocks.get(j).equals(EndlessWorlds.getGame().getWorld().getLevel().getBlockList().get(k))){
							EndlessWorlds.getGame().getWorld().getLevel().getBlockList().get(k).onCollision(entity);
						}
					}
				}
				if(direction.equals(Direction.LEFT)||direction.equals(Direction.RIGHT)){
					boolean slopeSuccess = false;
					for(int j=1;j<maxClimbHeight;j++){
							if(checkCollisions(calculateNewPosition(Direction.UP,calculateNewPosition(direction,entity.getPos(),1),1),entity.getSize(),entity.getRotation()).isEmpty()){
								entity.setPos(calculateNewPosition(Direction.UP,calculateNewPosition(direction,entity.getPos(),1),1));
								slopeSuccess = true;
								break;
							}
					}
					if(!slopeSuccess){
						return false;
					}
				}else{
					return false;
				}
			}
		}
		return true;
	}
	
	public static ArrayList<Block> checkCollisions(PointXY pos, PointXY size, float rotation){
		ArrayList<Block> collidingBlocks = new ArrayList<Block>();
		Rectangle rectangle = new Rectangle((int)pos.getX(),(int)pos.getY(),(int)size.getX(),(int)size.getY());
		AffineTransform at = new AffineTransform();
		at.rotate(rotation);
		GeneralPath path = new GeneralPath();
		path.append(rectangle.getPathIterator(at), true);
		Area collide = new Area(path);
		for(int i=0;i<EndlessWorlds.getGame().getModuleBlock().getBlockList().size();i++){
			Area collide2 = (Area)collide.clone();
			collide2.intersect(EndlessWorlds.getGame().getModuleBlock().getBlockList().get(i).getArea());
			if(!collide2.isEmpty()){
				collidingBlocks.add(EndlessWorlds.getGame().getModuleBlock().getBlockList().get(i));
			}
		}
		
		return collidingBlocks;
	}
	
	public void applyGravity(Entity entity, float gravityValue, float tpf){
		if(entity.getvSpeed()>0){
			if(ModuleMovement.move(Direction.DOWN, entity, (entity.getvSpeed()*gravityValue))){
				entity.setvSpeed(entity.getvSpeed()+gravityValue);
			}else{//Touch ground
				entity.setvSpeed(0);
			}
		}else if(entity.getvSpeed()<0){
			if(ModuleMovement.move(Direction.UP, entity, (entity.getvSpeed()*gravityValue))){
				entity.setvSpeed(entity.getvSpeed()+gravityValue);
			}else{
				entity.setvSpeed(0);
			}
		}else{
			if(ModuleMovement.move(Direction.DOWN, entity, 1)){
				entity.setvSpeed(entity.getvSpeed()+gravityValue);
			}else{
				entity.setvSpeed(0);
			}
		}
	}
	public void jump(Entity entity){
		if(isOnGround(entity)){
			entity.setvSpeed(-entity.getJumpForce());
			if(entity==ed.getModuleEntity().getPlayer()){
				ed.getModuleAudio().getAudioTrack("SWIPE").startAudio();
			}
		}
	}
	public boolean isOnGround(Entity entity){
		if(entity.getvSpeed()==0&&!ModuleMovement.checkCollisions(new PointXY(entity.getPos().getX(),entity.getPos().getY()+1),entity.getSize(),0).isEmpty()){//vspeed=0 AND touching ground
			return true;
		}
		return false;
	}
	public void jump(Entity entity,float force){
		if(entity.getvSpeed()==0&&!ModuleMovement.checkCollisions(new PointXY(entity.getPos().getX(),entity.getPos().getY()+1),entity.getSize(),0).isEmpty()){//vspeed=0 AND touching ground
			entity.setvSpeed(-force);
			if(entity==ed.getModuleEntity().getPlayer()){
				ed.getModuleAudio().getAudioTrack("SWIPE").startAudio();
			}
		}
	}
	public static boolean collideWith(PointXY pos, PointXY size, float rotation, Entity entity){
		Rectangle rectangle = new Rectangle((int)pos.getX(),(int)pos.getY(),(int)size.getX(),(int)size.getY());
		AffineTransform at = new AffineTransform();
		GeneralPath path = new GeneralPath();
		path.append(rectangle.getPathIterator(at), true);
		Area collide = new Area(path);
		Area collide2 = (Area)collide.clone();
		collide2.intersect(entity.getArea());
		if(!collide2.isEmpty()){
			return true;
		}
		
		return false;
	}
	
	public static PointXY calculateNewPosition(Direction direction, PointXY pos,float amount){
		PointXY newPos;
		if(direction.equals(Direction.UP)){
			newPos = new PointXY(pos.getX(),pos.getY()-amount);
		}else if(direction.equals(Direction.DOWN)){
			newPos = new PointXY(pos.getX(),pos.getY()+amount);
		}else if(direction.equals(Direction.RIGHT)){
			newPos = new PointXY(pos.getX()+amount,pos.getY());
		}else if(direction.equals(Direction.LEFT)){
			newPos = new PointXY(pos.getX()-amount,pos.getY());
		}else{
			newPos = null;
		}
		return newPos;
	}
	
	public void moveTo(PointXY pos){
		ed.getModuleEntity().getPlayer().setPos(pos);
	}
	
	/*public boolean isAtGround(){
		return isAtGround;
	}*/

	@Override
	public void updatedKeyState(KeyEvent arg0, KeyState arg1) {
		try {
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("JumpKey").getValue()||
					arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("JumpArrowKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key is pushed
					moveKey[0]=true;
				}else{//Key is not pushed
					moveKey[0]=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("DownKey").getValue()||
					arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("DownArrowKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key is pushed
					moveKey[1]=true;
				}else{//Key is not pushed
					moveKey[1]=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("LeftKey").getValue()||
					arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("LeftArrowKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key is pushed
					moveKey[2]=true;
				}else{//Key is not pushed
					moveKey[2]=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("RightKey").getValue()||
					arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("RightArrowKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key is pushed
					moveKey[3]=true;
				}else{//Key is not pushed
					moveKey[3]=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("SprintKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key is pushed
					sprint=true;
				}else{//Key is not pushed
					sprint=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("SuperJumpKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key is pushed
					if(ed.getModuleEntity().getPlayer().getEnergy()>=25&&ed.getModuleEntity().getPlayer().getSuperJumpTimeout()>=4){
						ed.getModuleEntity().getPlayer().setvSpeed(-ed.getModuleEntity().getPlayer().getJumpForce()*2);
						ed.getModuleEntity().getPlayer().setEnergy(ed.getModuleEntity().getPlayer().getEnergy()-25);
						ed.getModuleEntity().getPlayer().setSuperJumpTimeout(0);
						ed.getModuleEntity().getPlayer().addEffect(new EffectNoJump(ed,"No Jump",4,ed.getModuleEntity().getPlayer()));
						ed.getModuleAudio().getAudioTrack("SUPERJUMP").startAudio();
					}
				}
			}
		} catch (ConfigNotFoundException e) {
			e.printStackTrace();
		}
	}
}
