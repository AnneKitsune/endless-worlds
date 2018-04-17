package net.supercraft.endlessWorlds.blocks;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.IUpdating;
import net.supercraft.endlessWorlds.entity.Entity;
import net.supercraft.jojoleproUtils.math.PointXY;


public abstract class Block implements IUpdating,Cloneable{
	protected EndlessWorlds ed;
	public final static int DEFAULT_SIZE = 20;
	
	protected boolean isOpaque = true;
	protected double friction = 1.0;
	protected double bounce = 0.0;
	protected BufferedImage texture = this.setTexture("assets/blocks/FallBackBlock.png").getTexture();
	protected boolean isHardBlock = true;
	
	private Rectangle rectangle = new Rectangle();
	protected PointXY position = new PointXY(0,0);
	protected PointXY size = new PointXY(20,20);
	protected float rotation = 0.0f;
	protected AffineTransform at = new AffineTransform();
	protected AffineTransform displayAt = new AffineTransform();
	protected GeneralPath path = new GeneralPath();
	protected Area area = new Area();
	int temp = 0;
	
	public Block(EndlessWorlds ed){
		this.ed = ed;
		texture = this.setTexture("assets/blocks/FallBackBlock.png").getTexture();
	}
	public Block(EndlessWorlds ed, String textureName){
		this.ed = ed;
		texture = this.setTexture("assets/blocks/"+textureName+".png").getTexture();
	}
	
	public abstract void onWalk(Entity entity);
	public abstract void onCollision(Entity entity);
	public void update(float tpf){
		/*if(temp>100){
			this.resolveRectangle();
			temp=0;
		}
		temp++;*/
	}
	public void updateDisplay(){}
	
	public void resolveRectangle(){
		rectangle = new Rectangle((int)position.getX(),(int)position.getY(),(int)size.getX(),(int)size.getY());
		this.regenerateAffineTransform();
		path = new GeneralPath();
		path.append(rectangle.getPathIterator(at), true);
		area = new Area(path);
	}
	private void regenerateAffineTransform(){
		at = new AffineTransform();
		//at.translate(position.getX(), position.getY());
		at.rotate(Math.toRadians(rotation), rectangle.getX(), rectangle.getY());
		//at.scale(size.getX()/20, size.getY()/20);
		
		displayAt = new AffineTransform();
		displayAt.translate(position.getX()/*+Block.DEFAULT_SIZE*/, position.getY()/*+Block.DEFAULT_SIZE*/);
		displayAt.rotate(Math.toRadians(rotation)/*, rectangle.width / 2, rectangle.height / 2*/);
		displayAt.scale(size.getX()/texture.getWidth(), size.getY()/texture.getHeight());
		
	}
	public void generateRectangle(){
		this.rectangle = new Rectangle((int)position.getX(),(int)position.getY(),(int)size.getX(),(int)size.getY());
	}
	
	public Block setFriction(double friction){
		this.friction = friction;
		return this;
	}
	public Block setIsOpaque(boolean isOpaque){
		this.isOpaque = isOpaque;
		return this;
	}
	public Block setBounce(double bounce){
		this.bounce = bounce;
		return this;
	}
	public Block setTexture(String filePath){
		try {
			this.texture = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			System.err.println("UNABLE TO LOAD TEXTURE! FilePath:"+filePath);
			try {
				System.err.println("Loading Fallback Block @ assets/blocks/FallBackBlock.png");
				this.texture = ImageIO.read(new File("assets/blocks/FallBackBlock.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return this;
	}
	public Block setHardness(boolean isHardBlock){
		this.isHardBlock = isHardBlock;
		return this;
	}
	public double getFriction(){
		return friction;
	}
	public boolean getIsOpaque(){
		return isOpaque;
	}
	public double getBounce(){
		return bounce;
	}
	public BufferedImage getTexture(){
		return texture;
	}
	public boolean getHardness(){
		return isHardBlock;
	}
	public Block setPosX(int X){
		this.position.setX(X);
		return this;
	}
	public Block setPosY(int Y){
		this.position.setY(Y);
		return this;
	}
	public int getPosX(){
		return (int)position.getX();
	}
	public int getPosY(){
		return (int)position.getY();
	}
	public PointXY getPos(){
		return position;
	}
	public int getSizeX(){
		return (int)size.getX();
	}
	public int getSizeY(){
		return (int)size.getY();
	}
	public void setSizeX(int sizeX){
		this.size.setX(sizeX);
	}
	public void setSizeY(int sizeY){
		this.size.setY(sizeY);
	}
	public Rectangle getRectangle(){
		return rectangle;
	}
	public GeneralPath getPath(){
		return path;
	}
	public Area getArea(){
		return area;
	}
	public AffineTransform getAffineTransform(){
		return at;
	}
	public AffineTransform getDisplayAffineTransform(){
		return displayAt;
	}
	public Block setRotation(float rotation){
		/*AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(rotation-this.rotation), texture.getWidth()/2, texture.getHeight()/2);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		texture = scaleOp.filter(texture, null);*/
		this.rotation = rotation;
		return this;
	}
	public float getRotation(){
		return rotation;
	}
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
