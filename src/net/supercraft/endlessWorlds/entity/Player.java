package net.supercraft.endlessWorlds.entity;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.animation.AnimatedInstance;
import net.supercraft.endlessWorlds.animation.Animation;
import net.supercraft.endlessWorlds.animation.AnimationSet;
import net.supercraft.endlessWorlds.display.ModuleMenuMain;
import net.supercraft.endlessWorlds.engine.Direction;
import net.supercraft.endlessWorlds.engine.ModuleMovement;
import net.supercraft.endlessWorlds.items.Inventory;
import net.supercraft.jojoleproUtils.math.PointXY;
import net.supercraft.jojoleproUtils.module.control.ConfigNotFoundException;
import net.supercraft.jojoleproUtils.module.model.IKeyControllable;
import net.supercraft.jojoleproUtils.module.model.KeyState;

public class Player extends Entity implements IKeyControllable{
	public BufferedImage texture;
	private float energy = 100;
	private float energyTimer = 0;
	private float lastEnergy = 100;
	private float lastAttack = 0;
	private int coin = 0;
	private int lifes = 3;
	private float superJumpTimeout = 0;
	private boolean attackKey = false;
	
	private Inventory inventory;
	
	public Player(EndlessWorlds ed){
		super(ed);
		this.setTexture("assets/textures/player/normal-000.png");
		this.setTextureFolderLocation("assets/textures/player/");
		this.setAttackSpeed(0.5f);
		this.setMeleeAttackRange(20f);
		this.setJumpForce(8);
		this.setStrengh(1f);
		this.defaultJumpForce = 8;
		this.inventory = new Inventory(ed);
	}
	
	public void update(float tpf) {
		if(attackKey){//Can hit multiple entities with this method(at the same time)
			if(lastAttack>=this.getAttackSpeed()){
				lastAttack=0;
				Animation.ATTACK.animate(ed, this, 250f, false);
				for(int i=0;i<ed.getModuleEntity().getEntityList().size();i++){
					if(!ed.getModuleEntity().getEntityList().get(i).equals(ed.getModuleEntity().getPlayer())){
						if(facingDirection.equals(Direction.LEFT)){//5px left and 5px top
							if(ModuleMovement.collideWith(new PointXY(pos.getX()-20,pos.getY()-5), new PointXY(size.getX()+20,size.getY()+5), this.getRotation(), ed.getModuleEntity().getEntityList().get(i))){//Collide Player's foot with entity
								this.attack(ed.getModuleEntity().getEntityList().get(i));
							}
						}else if(facingDirection.equals(Direction.RIGHT)){//5px left and 5px top
							if(!ed.getModuleEntity().getEntityList().get(i).equals(ed.getModuleEntity().getPlayer())){
								if(ModuleMovement.collideWith(new PointXY(pos.getX(),pos.getY()-5), new PointXY(size.getX()+20,size.getY()+5), this.getRotation(), ed.getModuleEntity().getEntityList().get(i))){//Collide Player's foot with entity
									this.attack(ed.getModuleEntity().getEntityList().get(i));
								}
							}
						}
					}
				}
			}
		}
		lastAttack+=tpf/1000;//setting in seconds
		
		if(this.getPos().getY()>=2500){
			this.damage(health*defense+1);
		}
		
		if(energy<lastEnergy){//Energy calculation
			energyTimer=0;
		}
		lastEnergy = energy;
		if(this.energy<100 && energyTimer>=3){
			energy+=0.25;
		}else{
			energyTimer+=tpf/1000;
		}
		
		superJumpTimeout+=tpf/1000;
		
		for(int j=0;j<getEffectList().size();j++){
			getEffectList().get(j).update(tpf);
			if(getEffectList().get(j).isFinish()){
				getEffectList().remove(j);
			}
		}
	}
	public void attack(Entity entity) {
		ed.getModuleAnimation().addAnimation(new AnimatedInstance(this,AnimationSet.ATTACKMELEE,1,250));
		entity.damage(this.getStrengh());
		
		System.out.println("Attacking! Damage:"+this.getStrengh()+" ,Entity Health:"+entity.getHealth());
	}
	public void damage(float damageValue){
		this.health-=(damageValue/defense);
		if(this.health<=0){
			health = 0;
			lifes--;
			if(lifes<=0){
				this.kill();
				energy = 100;
				coin = 0;
				health = 100;
				lifes = 3;
				ed.getDisplay().changeMenu(new ModuleMenuMain(EndlessWorlds.getGame().getEngine()));
				ed.pause();
			}else{
				this.respawn();
			}
		}
		ed.getDisplay().setDisplayDamageScreen(true);
	}
	public void respawn(){
		//ed.getWorld().loadLevel(Levels.getLevelByNumber(ed.getWorld().getLevelNumber()));causes stack overflow
		this.clearEffect();
		this.setEnergy(100);
		this.setPos(ed.getWorld().getLevel().getSpawnpoint());
		this.setvSpeed(0);
		this.setHealth(100);
	}
	//MOVE TO JOJOLEPROUTILS LATER!
	public float getDistance(PointXY from,PointXY to){
		//System.out.println((float)Math.sqrt(Math.pow(to.getX()-from.getX(), 2)+Math.pow(to.getY()-from.getY(), 2)));
		return (float)Math.sqrt(Math.pow(to.getX()-from.getX(), 2)+Math.pow(to.getY()-from.getY(), 2));
	}
	public String getTextureFolderLocation() {
		return "assets/textures/player/";
	}
	public BufferedImage getDisplayedImage(){
		return this.displayedImage;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public float getSuperJumpTimeout() {
		return superJumpTimeout;
	}

	public void setSuperJumpTimeout(float superJumpTimeout) {
		this.superJumpTimeout = superJumpTimeout;
	}

	@Override
	public void updatedKeyState(KeyEvent arg0, KeyState arg1) {
		try {
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("AttackKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key is pushed
					attackKey=true;
				}else{//Key is not pushed
					attackKey=false;
				}
			}
		} catch (ConfigNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public AnimationSet[] getEnabledAnimationSets() {
		AnimationSet[] animationSet = {AnimationSet.NORMAL,AnimationSet.JUMP,AnimationSet.WALKINGLEFT,AnimationSet.WALKINGRIGHT,AnimationSet.ATTACKMELEE};
		return animationSet;
	}
	
}
