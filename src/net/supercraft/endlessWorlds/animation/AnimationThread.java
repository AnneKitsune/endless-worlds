package net.supercraft.endlessWorlds.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;

public class AnimationThread implements IUpdatable{
	private EndlessWorlds ed;
	private ArrayList<BufferedImage> frameList;
	private IAnimated toAnimate;
	private float delayBetweenFrame;
	private boolean isDone;
	private boolean isContinuous;
	private String animationName = "";
	private int currentFrame=0;
	public AnimationThread(EndlessWorlds ed,ArrayList<BufferedImage> frameList,IAnimated toAnimate,float delayBetweenFrame, boolean isContinuous,String animationName){
		this.ed = ed;
		this.frameList = frameList;
		this.toAnimate = toAnimate;
		this.delayBetweenFrame = delayBetweenFrame;
		this.isContinuous = isContinuous;
		this.animationName=animationName;
	}
	public void run(){
		do{
			for(currentFrame=0;currentFrame<frameList.size();currentFrame++){
				toAnimate.setDisplayedImage(frameList.get(currentFrame));
				try {
					Thread.sleep((long)delayBetweenFrame);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}while(isContinuous);
		toAnimate.setDisplayedImage(null);
	}
	public ArrayList<BufferedImage> getFrameList() {
		return frameList;
	}
	public void setFrameList(ArrayList<BufferedImage> frameList) {
		this.frameList = frameList;
	}
	public float getDelayBetweenFrame() {
		return delayBetweenFrame;
	}
	public void setDelayBetweenFrame(float delayBetweenFrame) {
		this.delayBetweenFrame = delayBetweenFrame;
	}
	public boolean isContinuous() {
		return isContinuous;
	}
	public void setContinuous(boolean isContinuous) {
		this.isContinuous = isContinuous;
	}
	public String getAnimationName() {
		return animationName;
	}
	public void setAnimationName(String animationName) {
		this.animationName = animationName;
	}
	public void update(float arg0) {
		run();
		if(isContinuous){
			
		}else{
			toAnimate.setDisplayedImage(null);
		}
	}
	
}
