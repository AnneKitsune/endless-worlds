package net.supercraft.endlessWorlds.animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;

public enum Animation implements IUpdatable{
	ATTACK("attack"),
	WALKRIGHT("walkright"),
	WALKLEFT("walkleft"),
	NORMAL("normal");
	
	private String name;
	private Animation(String name){
		this.name = name;
	}
	public void waitWhileAnimating(IAnimated toAnimate){//NOT THREADED
		
	}
	public void animate(EndlessWorlds ed,IAnimated toAnimate,float timeBetweenChange,boolean continuous){//execute getFrameList using toAnimate.getFolderPath();  THREADED
			//Anti lag thingy    We could try to replace the frameList instead of the whole 
		
			/*if(toAnimate.getCurrentAnimation()==null){
				ArrayList<BufferedImage> frameList = this.getFrameList(toAnimate);
				AnimationThread at = new AnimationThread(ed,frameList,toAnimate,timeBetweenChange,continuous,this.getName());
				toAnimate.setCurrentAnimation(at);
			}else{
				if(!toAnimate.getCurrentAnimation().getAnimationName().equals(this.getName())){
					ArrayList<BufferedImage> frameList = this.getFrameList(toAnimate);
					toAnimate.getCurrentAnimation().setFrameList(frameList);
					toAnimate.getCurrentAnimation().setAnimationName(this.getName());
					toAnimate.getCurrentAnimation().setDelayBetweenFrame(timeBetweenChange);
					toAnimate.getCurrentAnimation().setContinuous(continuous);
				}
			}*/
	}
	//public void animateContinuously(){}
	
	public ArrayList<BufferedImage> getFrameList(IAnimated animation){
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();
		int filesInDirectory = new File(animation.getTextureFolderLocation()).listFiles().length;
		int frameNumber = 0;
		try{
			for(int i=0;i<filesInDirectory;i++){
				if(new File(animation.getTextureFolderLocation()).listFiles()[i].getName().contains(this.getName())){
					list.add(ImageIO.read(new File(animation.getTextureFolderLocation()).listFiles()[i]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(frameNumber+animation.getTextureFolderLocation());
		return list;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void update(float arg0) {
		
	}
	
}
