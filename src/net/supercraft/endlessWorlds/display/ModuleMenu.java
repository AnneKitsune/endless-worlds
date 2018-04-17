package net.supercraft.endlessWorlds.display;

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
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.supercraft.jojoleproUtils.module.control.Module;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.model.IDrawable;
import net.supercraft.jojoleproUtils.module.model.IKeyControllable;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;
import net.supercraft.jojoleproUtils.module.model.KeyState;

public class ModuleMenu extends Module implements IDrawable,IUpdatable,IKeyControllable{
	public static final Dimension tempBufferSize = new Dimension(150,30);
	protected BufferedImage background = this.loadImage("assets/textures/MenuBackground.png");
	protected AffineTransform backgroundAT = new AffineTransform();
	protected JPanel panel;
	protected Dimension dim = new Dimension(800,600);
	public static final int spacingSize = 25;
	
	//this.getPanel().add(Box.createRigidArea(tempBufferSize)); to set space between buttons
	
	public ModuleMenu(ModuleManager moduleManager) {
		super(moduleManager);
		panel = new JPanel(){
			private static final long serialVersionUID = 3869556588473591734L;

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
			
		};
		dim = new Dimension(panel.getWidth(),panel.getHeight());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}

	@Override
	public void updatedKeyState(KeyEvent arg0, KeyState arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(float arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics2D arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public BufferedImage loadImage(String filePath){//Move this later to ModuleIO
		try {
			return ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
