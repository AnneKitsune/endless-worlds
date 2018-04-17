package net.supercraft.endlessWorlds.display;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import net.supercraft.jojoleproUtils.math.PointXY;

public class LevelEditorItem extends JLabel implements Cloneable{
	private static final long serialVersionUID = 1L;
	public static final int TYPE_BLOCK = 0; 
	public static final int TYPE_ITEM = 1;
	public static final int TYPE_ENTITY = 2;
	public static final int TYPE_TEXT = 3;
	
	private BufferedImage originalTexture;
	private AffineTransform at = new AffineTransform();
	private String name = "DEFAULT_LEVEL_EDITOR_NAME";
	private int type = 0;
	//private PointXY position = new PointXY(0,0);
	//private PointXY size = new PointXY(40,40);
	private float rotation = 0;
	private boolean collide = true;
	
	/**
	 * call setType Object at least one time if you want to export at some point
	 */
	public LevelEditorItem(){
		
	}
	public void setNewIcon(PointXY newSize, BufferedImage originalImage){
		this.originalTexture = originalImage;
		at = new AffineTransform();
		at.translate(this.getBounds().getX(), this.getBounds().getY());
		at.rotate(Math.toRadians(rotation));
		at.scale((double)newSize.getX()/this.originalTexture.getWidth(), (double)newSize.getY()/this.originalTexture.getHeight());
		
        //this.setIcon(new ImageIcon(originalImage));
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public boolean isCollide() {
		return collide;
	}
	public void setCollide(boolean collide) {
		this.collide = collide;
	}
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void setOriginalTexture(BufferedImage original){
		this.originalTexture = original;
	}
	public BufferedImage getOriginalTexture(){
		return this.originalTexture;
	}

	public AffineTransform getAffineTransform() {
		return at;
	}
}
