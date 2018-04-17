package net.supercraft.endlessWorlds.items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.IUpdating;
import net.supercraft.jojoleproUtils.math.PointXY;

public class Item implements IUpdating{
	protected EndlessWorlds ed;
	protected String name = "DEFAULT_NAME";
	protected PointXY size = new PointXY(20,20);
	protected BufferedImage texture;
	protected PointXY pos = new PointXY(0,0);
	protected int maximumStack = 128;
	
	public Item(EndlessWorlds ed){
		this.ed = ed;
	}
	public Item(EndlessWorlds ed,PointXY pos,PointXY size, BufferedImage texture){
		this.ed = ed;
		this.pos = pos;
		this.size = size;
		this.texture = texture;
	}
	public void onPickup(){
		
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PointXY getSize() {
		return size;
	}

	public void setSize(PointXY size) {
		this.size = size;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	protected void setTexture(String filePath){
		try {
			this.texture = ImageIO.read(new File(filePath));
			//this.displayedImage = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			System.err.println("Unable to load Texture image! FilePath:"+filePath);
			e.printStackTrace();
		}
	}

	public PointXY getPos() {
		return pos;
	}

	public void setPos(PointXY pos) {
		this.pos = pos;
	}

	public int getMaximumStack() {
		return maximumStack;
	}

	public void setMaximumStack(int maximumStack) {
		this.maximumStack = maximumStack;
	}
	@Override
	public void update(float tpf) {
	}
	
	
}
