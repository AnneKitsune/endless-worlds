package net.supercraft.endlessWorlds.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.ai.AI;
import net.supercraft.jojoleproUtils.math.PointXY;

public abstract class Mob extends Entity{
	protected ArrayList<AI> aiList = new ArrayList<AI>();
	public Mob(EndlessWorlds ed){
		super(ed);
		this.ed = ed;
	}
	public Mob(EndlessWorlds ed, float health, float speed, float jumpForce, float strengh,PointXY pos,PointXY size, BufferedImage texture, float meleeAttackRange, float rangedAttackRange){
		super(ed,health,speed,jumpForce,strengh,pos,size,texture);
		this.meleeAttackRange = meleeAttackRange;
		this.rangedAttackRange = rangedAttackRange;
	}
	
	protected void setTexture(){
		String filePath="";
		try {
			filePath = "assets/mob/"+this.getClass().getSimpleName().substring(3).toLowerCase()+"/normal.png";
			this.texture = ImageIO.read(new File(filePath));
			this.displayedImage = ImageIO.read(new File(filePath));
			this.textureFile = new File(filePath);
			this.setTextureFolderLocation("assets/mob/"+this.getClass().getSimpleName().substring(3).toLowerCase()+"/");
		} catch (IOException e) {
			System.err.println("Unable to load Texture image! FilePath:"+filePath);
			e.printStackTrace();
		}
	}
	public void update(float tpf) {
		for(int i=0;i<aiList.size();i++){
			aiList.get(i).update(tpf);
		}
		for(int j=0;j<getEffectList().size();j++){
			getEffectList().get(j).update(tpf);
			if(getEffectList().get(j).isFinish()){
				getEffectList().remove(j);
			}
		}
		//ath.update(tpf);Disabled old animation thread
	}
	public void addAI(AI ai){
		aiList.add(ai);
	}
	public ArrayList<AI> getAIList(){
		return aiList;
	}
}
