package net.supercraft.endlessWorlds.animation;

import java.util.ArrayList;

import net.supercraft.jojoleproUtils.module.control.Module;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;

public class ModuleAnimation extends Module implements IUpdatable{
	private ArrayList<AnimatedInstance> currentlyAnimated = new ArrayList<AnimatedInstance>();
	
	public ModuleAnimation(ModuleManager mm){
		super(mm);
	}

	@Override
	public void update(float arg0) {
		for(int i=0;i<currentlyAnimated.size();i++){
			currentlyAnimated.get(i).autoAnimate(arg0);
			if(currentlyAnimated.get(i).getRepeatLeft()<=0){
				currentlyAnimated.get(i).close();
				
				if(currentlyAnimated.get(i).isAutoRollback()){//The animation set is not normal? when the animation finish we set the normal images back.
					this.addAnimation(new AnimatedInstance(currentlyAnimated.get(i).getToAnimate(),AnimationSet.NORMAL,1,1));
					System.out.println("Setting NORMAL AnimationSet back from: "+currentlyAnimated.get(i).getAnimationSet().name());
				}
				
				currentlyAnimated.remove(i);
				i-=1;//We want to update the next element in the list that moved 1 place before because of the remove.
			}
		}
	}

	public ArrayList<AnimatedInstance> getCurrentlyAnimated() {
		return currentlyAnimated;
	}
	public AnimatedInstance getAnimatedInstance(IAnimated animatedObject,AnimationSet animationSet){
		for(int i=0;i<currentlyAnimated.size();i++){
			if(currentlyAnimated.get(i).getToAnimate().equals(animatedObject)){
				if(currentlyAnimated.get(i).getAnimationSet().equals(animationSet)){
					return currentlyAnimated.get(i);
				}
			}
		}
		return null;
	}
	public boolean addAnimation(AnimatedInstance animatedInstance){
		if(!animatedInstance.isCurrentAnimationSetEnabled()){//Used animation set is not enabled for the animated instance.
			return false;
		}
		for(int i=0;i<currentlyAnimated.size();i++){
			if((currentlyAnimated.get(i).getAnimationSet().equals(animatedInstance.getAnimationSet())&&
					currentlyAnimated.get(i).getToAnimate().equals(animatedInstance.getToAnimate()))){//Currently animating the same animation for the same instance.
				return false;
			}
		}
		currentlyAnimated.add(animatedInstance);
		return true;
	}
	
}
