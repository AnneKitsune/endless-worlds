package net.supercraft.endlessWorlds.animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//Make animation set, rename this as animatedInstance, make a list of activated animation sets(from an enum of all default animation sets) for each toAnimate.
public class AnimatedInstance {
	private IAnimated toAnimate;
	private ArrayList<BufferedImage> imageSet = new ArrayList<BufferedImage>();
	private AnimationSet animationSet;
	private int currentImageFrame = 0;
	private int repeatLeft = 1;
	
	private int loopSpeed = 1000;
	private int sinceLastChange = -1;
	
	private boolean autoRollback = true;
	
	public AnimatedInstance(IAnimated toAnimate, AnimationSet animationSet,int repeat,int loopSpeed){
		this.toAnimate = toAnimate;
		this.repeatLeft = repeat;
		this.animationSet = animationSet;
		this.loopSpeed = loopSpeed;
		this.generateAnimationSet(toAnimate,animationSet);
	}
	public void autoAnimate(float tpf){
		if(sinceLastChange==-1){//We look if its the first time that we go though this loop
			if(imageSet.size()>=1){//If so, we show the image while bypassing the time counter.
				toAnimate.setDisplayedImage(imageSet.get(currentImageFrame));//We change the displayed of the animated object.
			}
		}
		sinceLastChange+=tpf;
		if(sinceLastChange>=loopSpeed){//Enough time from last loop to update?
			if(imageSet.size()>=1){
				toAnimate.setDisplayedImage(imageSet.get(currentImageFrame));//We change the displayed of the animated object.
			}
			currentImageFrame++;
			if(currentImageFrame>=imageSet.size()){//Current image is out of bound of the image list? We restart the loop by setting the frame at 0.
				repeatLeft--;
				if(repeatLeft<=0){//No more animation is to be done, we cancel the image refresh.
					return;
				}
				currentImageFrame=0;
			}
			
			sinceLastChange=0;
		}
		
	}
	public void close(){
		repeatLeft = 0;
		currentImageFrame = 0;
	}
	public int getRepeatLeft(){
		return repeatLeft;
	}
	public int getLoopSpeed() {
		return loopSpeed;
	}
	public void setLoopSpeed(int loopSpeed) {
		this.loopSpeed = loopSpeed;
	}
	
	public IAnimated getToAnimate() {
		return toAnimate;
	}
	public AnimationSet getAnimationSet() {
		return animationSet;
	}
	public boolean isAutoRollback() {
		return autoRollback;
	}
	public void setAutoRollback(boolean autoRollback) {
		this.autoRollback = autoRollback;
	}
	public void generateAnimationSet(IAnimated toAnimate, AnimationSet set){
		if(isCurrentAnimationSetEnabled()){
			this.imageSet = getFrameList(toAnimate, set);
			return;
		}
		
		
		//System.err.println("Non-Existing Animation set or non compatible with this animated instance! AnimationSet:"+set);
		this.repeatLeft=0;
	}
	public boolean isCurrentAnimationSetEnabled(){
		for(int i=0;i<toAnimate.getEnabledAnimationSets().length;i++){
			if(toAnimate.getEnabledAnimationSets()[i].equals(this.animationSet)){//This animation set is enabled for this instance, generate the frames of the animation.
				return true;
			}
		}
		return false;
	}
	//PERFORMENCE LEAK! The animation system will load the image BEFORE checking if an other is running on the same animationset/animated object
	private ArrayList<BufferedImage> getFrameList(IAnimated toAnimate, AnimationSet set){
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();
		int filesInDirectory = new File(toAnimate.getTextureFolderLocation()).listFiles().length;
		int frameNumber = 0;
		try{
			for(int i=0;i<filesInDirectory;i++){
				if(new File(toAnimate.getTextureFolderLocation()).listFiles()[i].getName().contains(set.name().toLowerCase())){
					list.add(ImageIO.read(new File(toAnimate.getTextureFolderLocation()).listFiles()[i]));
					frameNumber++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
