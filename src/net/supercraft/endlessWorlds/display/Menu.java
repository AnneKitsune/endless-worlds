package net.supercraft.endlessWorlds.display;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.IUpdating;
import net.supercraft.jojoleproUtils.module.model.IKeyControllable;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;
import net.supercraft.jojoleproUtils.module.model.KeyState;

public class Menu extends JPanel implements IKeyControllable, IUpdatable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	private static final int spacingSize = 25;
	protected EndlessWorlds ed;
	protected String name = "DEFAULT_MENU_NAME";
	protected BufferedImage background = this.loadImage("assets/textures/MenuBackground.png");
	private AffineTransform backgroundAT = new AffineTransform();
	private Dimension dim;
	public Menu(EndlessWorlds ed){
		this.ed = ed;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		dim = new Dimension(this.getWidth(),this.getHeight());
	}
	
	@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    backgroundAT = new AffineTransform();
		backgroundAT.scale((double)this.getWidth()/600, (double)this.getHeight()/450);
		dim.setSize(this.getWidth(), this.getHeight());
	    
		/*for(int i=0;i<this.getComponentCount();i++){
			this.getComponent(i).setPreferredSize(new Dimension((int)(Math.sqrt(Math.pow(this.getWidth(), 2)+Math.pow(this.getHeight(), 2))/10),(int)(Math.sqrt(Math.pow(this.getWidth(), 2)+Math.pow(this.getHeight(), 2))/20)));
			this.getComponent(i).setMinimumSize(new Dimension((int)(Math.sqrt(Math.pow(this.getWidth(), 2)+Math.pow(this.getHeight(), 2))/10),(int)(Math.sqrt(Math.pow(this.getWidth(), 2)+Math.pow(this.getHeight(), 2))/20)));
			this.getComponent(i).setMaximumSize(new Dimension((int)(Math.sqrt(Math.pow(this.getWidth(), 2)+Math.pow(this.getHeight(), 2))/10),(int)(Math.sqrt(Math.pow(this.getWidth(), 2)+Math.pow(this.getHeight(), 2))/20)));
		}*/
		
		for(int i=0;i<this.getComponentCount();i++){
			this.getComponent(i).setPreferredSize(new Dimension(200,(int)((this.getHeight()-(spacingSize*this.getComponentCount()/2))/this.getComponentCount()/2)));
		}
		
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.drawImage(background, backgroundAT,this);
	}
	
	public BufferedImage loadImage(String filePath){
		try {
			return ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void addComponent(Component component){
		this.add(component);
	}
	public void addButtonWithLayout(JComponent comp){
		this.add(Box.createRigidArea(new Dimension(150,30)));
		this.addComponent(comp);
	}
	public static void setupButtonLayout(JButton button){
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setPreferredSize(new Dimension(200,100));
		button.setMinimumSize(new Dimension(50,25));
		button.setMaximumSize(new Dimension(200,100));
		
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.setBorderPainted(true);
	}
	public static void setupComponentLayout(JComponent comp){
		comp.setAlignmentX(Component.CENTER_ALIGNMENT);
		comp.setPreferredSize(new Dimension(200,100));
		comp.setMinimumSize(new Dimension(50,25));
		comp.setMaximumSize(new Dimension(200,100));
		
		comp.setOpaque(true);
		comp.setVisible(true);
	}
	
	public void updatedKeyState(KeyEvent arg0, KeyState arg1) {}
	public void update(float arg0) {}
}
