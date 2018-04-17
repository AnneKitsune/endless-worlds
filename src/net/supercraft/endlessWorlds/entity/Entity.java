package net.supercraft.endlessWorlds.entity;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.animation.AnimationThread;
import net.supercraft.endlessWorlds.animation.IAnimated;
import net.supercraft.endlessWorlds.effect.Effect;
import net.supercraft.endlessWorlds.engine.Direction;
import net.supercraft.endlessWorlds.engine.IUpdating;
import net.supercraft.jojoleproUtils.math.PointXY;

public abstract class Entity implements IUpdating, IAnimated{
	protected float hSpeed = 0;
	protected float vSpeed = 0;
	protected PointXY size = new PointXY(40,40);
	protected PointXY pos = new PointXY(0,0);
	protected float rotation = 0;
	protected EndlessWorlds ed;
	protected Rectangle rectangle = new Rectangle(0,0,40,40);
	protected AffineTransform at = new AffineTransform();
	protected AffineTransform displayAt = new AffineTransform();
	protected GeneralPath path = new GeneralPath();
	protected Area area = new Area();
	protected float health = 100;
	protected float speed = 100f;
	protected float jumpForce = 10f;
	protected float acceleration = 1f;
	protected float defaultJumpForce = 10f;
	protected float strengh = 1f;
	protected float defense = 1f;
	protected float meleeAttackRange = 1f;
	protected float rangedAttackRange = 1f;
	protected float attackSpeed = 1f;
	
	protected BufferedImage displayedImage;
	protected Direction facingDirection = Direction.RIGHT;
	protected BufferedImage texture;
	protected File textureFile;
	protected String textureFolderLocation = "assets/textures/mob/";
	protected AnimationThread ath = null;
	
	protected ArrayList<Effect> effectList = new ArrayList<Effect>();//Don't forget to update()
	
	public Entity(EndlessWorlds ed){
		this.ed = ed;
	}
	public Entity(EndlessWorlds ed, float health, float speed, float jumpForce, float strengh,PointXY pos,PointXY size, BufferedImage texture){
		this.ed = ed;
		this.health = health;
		this.speed = speed;
		this.jumpForce = jumpForce;
		this.strengh = strengh;
		this.pos = pos;
		this.size = size;
		this.texture = texture;
	}
	
	public void update(float tpf){
		updateCollisionPosition();
	}
	
	protected void setTexture(String filePath){
		try {
			this.texture = ImageIO.read(new File(filePath));
			this.displayedImage = ImageIO.read(new File(filePath));
			this.textureFile = new File(filePath);
		} catch (IOException e) {
			System.err.println("Unable to load Texture image! FilePath:"+filePath);
			e.printStackTrace();
		}
	}
	public void setDisplayedImage(BufferedImage image) {
		if(image!=null){
			this.displayedImage = image;
		}else{
			this.displayedImage = this.getTexture();
		}
	}
	public String getTextureFolderLocation() {
		return textureFolderLocation;
	}
	public void updateCollisionPosition(){
		//at = new AffineTransform();
		this.regenerateAffineTransform();
		//at.translate(pos.getX(), pos.getY());
		rectangle.x=(int)pos.getX();
		rectangle.y=(int)pos.getY();
		rectangle.setSize(new Dimension((int)this.getSize().getX(),(int)this.getSize().getY()));
		//at.rotate(Math.toRadians(rotation), rectangle.width / 2, rectangle.height / 2);
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
		displayAt.translate(pos.getX()/*+Block.DEFAULT_SIZE*/, pos.getY()/*+Block.DEFAULT_SIZE*/);
		displayAt.rotate(Math.toRadians(rotation)/*, rectangle.width / 2, rectangle.height / 2*/);
		displayAt.scale(size.getX()/20, size.getY()/20);
		
	}
	public void damage(float damageValue){
		this.health-=(damageValue/defense);
		if(this.health<=0){
			this.kill();
		}
	}
	public void kill(){
		this.health = 0;
		for(int i=0;i<ed.getModuleEntity().getEntityList().size();i++){//Remove entity from entity list of the level.
			if(ed.getModuleEntity().getEntityList().get(i)==this){
				ed.getModuleEntity().getEntityList().remove(i);
				return;
			}
		}
	}
	public void clearEffect(){
		while(!this.effectList.isEmpty()){
			this.effectList.get(0).onTimeout();
			this.effectList.remove(0);
			
		}
	}
	public void addEffect(Effect effect){
		this.effectList.add(effect);
	}
	public BufferedImage getTexture(){
		return texture;
	}
	
	public float gethSpeed() {
		return hSpeed;
	}

	public void sethSpeed(float hSpeed) {
		this.hSpeed = hSpeed;
	}

	public float getvSpeed() {
		return vSpeed;
	}

	public void setvSpeed(float vSpeed) {
		this.vSpeed = vSpeed;
	}

	public PointXY getPos() {
		return pos;
	}

	public void setPos(PointXY pos) {
		this.pos = pos;
		this.updateCollisionPosition();
		
	}
	public void setPos(float x,float y){
		this.pos = new PointXY(x,y);
		this.updateCollisionPosition();
	}
	public PointXY getSize(){
		return size;
	}
	public GeneralPath getPath(){
		return path;
	}
	public Area getArea(){
		return area;
	}
	public Rectangle getRectangle(){
		return rectangle;
	}
	public void setRotation(float rotation){
		this.rotation = rotation;
	}
	public float getRotation(){
		return rotation;
	}
	public void setHealth(float health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getJumpForce() {
		return jumpForce;
	}

	public void setJumpForce(float jumpForce) {
		this.jumpForce = jumpForce;
	}

	public float getStrengh() {
		return strengh;
	}

	public void setStrengh(float strengh) {
		this.strengh = strengh;
	}
	public float getHealth() {
		return health;
	}
	public void setSize(PointXY size){
		this.size = size;
	}
	public AffineTransform getAffineTransform(){
		return at;
	}
	public AffineTransform getDisplayAffineTransform(){
		return displayAt;
	}
	public float getMeleeAttackRange() {
		return meleeAttackRange;
	}
	public void setMeleeAttackRange(float meleeAttackRange) {
		this.meleeAttackRange = meleeAttackRange;
	}
	public float getRangedAttackRange() {
		return rangedAttackRange;
	}
	public void setRangedAttackRange(float rangedAttackRange) {
		this.rangedAttackRange = rangedAttackRange;
	}
	public float getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	public ArrayList<Effect> getEffectList() {
		return effectList;
	}
	public void setEffectList(ArrayList<Effect> effectList) {
		this.effectList = effectList;
	}
	public float getDefaultJumpForce(){
		return defaultJumpForce;
	}
	public float getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	public Direction getFacingDirection() {
		return facingDirection;
	}
	public void setFacingDirection(Direction facingDirection) {
		this.facingDirection = facingDirection;
	}
	public File getTextureFile() {
		return textureFile;
	}
	public BufferedImage getDisplayedImage(){
		return displayedImage;
	}
	public float getDefense() {
		return defense;
	}
	public void setDefense(float defense) {
		this.defense = defense;
	}
	public void setTextureFolderLocation(String textureFolderLocation) {
		this.textureFolderLocation = textureFolderLocation;
	}
	public AnimationThread getCurrentAnimation(){
		return ath;
	}
}
