package net.supercraft.endlessWorlds.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.blocks.Block;
import net.supercraft.endlessWorlds.entity.Player;
import net.supercraft.endlessWorlds.gameplay.GamemodeChrono;
import net.supercraft.endlessWorlds.gameplay.Gamemodes;
import net.supercraft.jojoleproUtils.math.PointXY;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuGameInterface extends ModuleMenu{
	protected static final long serialVersionUID = -9075012348121036827L;
	
	private BufferedImage background;
	private Graphics2D g2dGui;
	private Graphics2D g2dDisplay;
	private AffineTransform damageScreenT;
	
	private BufferedImage damageScreen;
	private BufferedImage heart;
	private BufferedImage coin;
	private BufferedImage bagIcon;
	private Font font;
	private Color guiColor = new Color(0,0,255,200);
	private Color inventoryColor = new Color(0,0,255,200);
	private Date currentTime;
	private long startTime = System.currentTimeMillis();
	
	private PointXY camera = new PointXY(0,0);
	
	public ModuleMenuGameInterface(ModuleManager mm){
		super(mm);
		
		EndlessWorlds.getGame().getDisplay().setDisplayDamageScreen(false);
		EndlessWorlds.getGame().getModuleEntity().getPlayer().respawn();
		//EndlessWorlds.getGame().getEngine().setRunning(true);
		
		
		
		panel.setVisible(true);
		this.changeBackground("assets/textures/fondEcranNew.jpg");
		
		try {
			damageScreen = ImageIO.read(new File("assets/textures/DamageScreen.png"));
			heart = ImageIO.read(new File("assets/textures/LittleHeart.png"));
			coin = ImageIO.read(new File("assets/textures/Coin.png"));
			bagIcon = ImageIO.read(new File("assets/textures/BagIcon.png"));
			
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("assets/font/Anarchy.ttf")).deriveFont(Font.PLAIN, 20);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		this.setPanel(new JPanel(){
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g2dGui = (Graphics2D) g.create();
				g2dDisplay = (Graphics2D) g.create();
				g2dDisplay.translate(-camera.getX(), -camera.getY());
				
				paintBackground(g);//Drawing Background
				/*for(int i=0;i<EndlessWorlds.getGame().getWorld().getLevel().getBlockList().size();i++){//Drawing Blocks
					g2dDisplay.drawImage(getBlock(i).getTexture(), getBlock(i).getDisplayAffineTransform(), panel);
				}*/
				/*for(int i=0;i<EndlessWorlds.getGame().getWorld().getLevel().getEntityList().size();i++){//Drawing Entities
					if(EndlessWorlds.getGame().getWorld().getLevel().getEntityList().get(i).getClass().equals(Player.class)){
						g2dGui.drawImage(EndlessWorlds.getGame().getModuleEntity().getPlayer().getDisplayedImage(),(int)EndlessWorlds.getGame().getModuleEntity().getPlayer().getPos().getX()-(int)camera.getX(),(int)EndlessWorlds.getGame().getModuleEntity().getPlayer().getPos().getY()-(int)camera.getY(),panel);
					}else{
						g2dDisplay.drawImage(EndlessWorlds.getGame().getWorld().getLevel().getEntityList().get(i).getTexture(),EndlessWorlds.getGame().getWorld().getLevel().getEntityList().get(i).getDisplayAffineTransform(),panel);
					}
				}*/
				//paintPlayer(g2dGui);//Drawing Player let gui do not put display
				g2dGui.setColor(Color.WHITE);
				paintGuiBackground(g2dGui);
				paintEnergyBar(g2dGui);
				paintLife(g2dGui);
				paintCoin(g2dGui);
				paintRemainingLife(g2dGui);
				paintBagIcon(g2dGui);
				paintPlayerEffects(g2dGui);
				
				
				paintInventory(g2dGui);
				paintLevelInfo(g2dGui);
				paintCurrentTime(g2dGui);
				paintLevelRecordTime(g2dGui);
				paintStrings(g2dGui);
				
				paintDamageScreen(g2dGui);//Always draw last or other things will overlap
				
				//temp
				EndlessWorlds.getGame().getDisplay().drawAllOtherComponents(g2dDisplay);
			}
		});
		EndlessWorlds.getGame().unpause();
	}
	
	public void update(float tpf){
		camera.setX(EndlessWorlds.getGame().getModuleEntity().getPlayer().getPos().getX()-panel.getSize().getWidth()/2);
		camera.setY(EndlessWorlds.getGame().getModuleEntity().getPlayer().getPos().getY()-panel.getSize().getHeight()/2);
		if(camera.getX()<0){
			camera.setX(0);
		}
		if(camera.getY()<0){
			camera.setY(0);
		}
		this.updateTimer();
	}
	public void levelChanged(){
		startTime = System.currentTimeMillis();
		currentTime = new Date(System.currentTimeMillis()-startTime);
	}
	
	public void changeBackground(String backgroundFilePath){
		try {
			this.background = ImageIO.read(new File(backgroundFilePath));
		} catch (IOException e) {
			System.err.println("Unable to load Background image!");
			e.printStackTrace();
		}
	}
	
	private void paintBackground(Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, panel.getWidth(), panel.getHeight());
		g.drawImage(this.background,0,0,panel);
		g.setColor(Color.BLACK);
		
	}
	
	private void paintEnergyBar(Graphics2D g2d){
		g2d.setPaint(new GradientPaint(g2d.getClipBounds().width/4,0,Color.BLUE,3*(g2d.getClipBounds().width/4), 0,Color.WHITE));
		g2d.fillRect(g2d.getClipBounds().width/4, 10, (int)((g2d.getClipBounds().width/2)*(EndlessWorlds.getGame().getModuleEntity().getPlayer().getEnergy()/100)), 30);
		g2d.setPaint(Color.BLACK);
		g2d.drawRect(g2d.getClipBounds().width/4, 10, (g2d.getClipBounds().width/2), 30);
		g2d.setPaint(Color.WHITE);
		g2d.setFont(font);
		g2d.drawString("ENERGY: "+(int)EndlessWorlds.getGame().getModuleEntity().getPlayer().getEnergy()+"%", (g2d.getClipBounds().width/2)-60, 30);
	}
	
	private void paintPlayer(Graphics g){
		
		g.drawImage(EndlessWorlds.getGame().getModuleEntity().getPlayer().getDisplayedImage(),(int)EndlessWorlds.getGame().getModuleEntity().getPlayer().getPos().getX()-(int)camera.getX(),(int)EndlessWorlds.getGame().getModuleEntity().getPlayer().getPos().getY()-(int)camera.getY(),panel);
		
	}
	
	public void paintDamageScreen(Graphics2D g2d){
		if(EndlessWorlds.getGame().getDisplay().isDisplayDamageScreen()){
			damageScreenT = new AffineTransform();
			damageScreenT.scale((float)g2d.getClipBounds().width/(float)1980, (float)g2d.getClipBounds().getHeight()/(float)1200);
			g2d.drawImage(damageScreen,damageScreenT,panel);
			
		}
	}
	public void paintLife(Graphics2D g2d){// 25
		g2d.drawImage(heart,25,12,panel);//(int)((float)g2d.getClipBounds().width/(float)32
		g2d.drawString(""+(int)EndlessWorlds.getGame().getModuleEntity().getPlayer().getHealth(), 50, 30);
	}
	public void paintCoin(Graphics2D g2d){//  1/8
		g2d.drawImage(coin,(int)((float)g2d.getClipBounds().width/(float)8),12,panel);
		g2d.drawString(""+(int)EndlessWorlds.getGame().getModuleEntity().getPlayer().getCoin(), (int)((float)g2d.getClipBounds().width/(float)8+25), 30);
	}
	public void paintGuiBackground(Graphics2D g2d){
		g2d.setColor(guiColor);
		g2d.fillRect(0, 0, g2d.getClipBounds().width,50);
	}
	public void paintRemainingLife(Graphics2D g2d){//  scr-25
		if(EndlessWorlds.getGame().getModuleEntity().getPlayer().getLifes()<=3){
			for(int i=0;i<EndlessWorlds.getGame().getModuleEntity().getPlayer().getLifes();i++){
				g2d.drawImage(heart,(int)((float)g2d.getClipBounds().width-(25*(i+1))),12,panel);
			}
		}else{
			g2d.drawImage(heart,(int)((float)g2d.getClipBounds().width-60),12,panel);
			g2d.drawString("X"+EndlessWorlds.getGame().getModuleEntity().getPlayer().getLifes(),(float)g2d.getClipBounds().width-40, 30);
		}
	}
	public void paintBagIcon(Graphics2D g2d){//  7/8
		g2d.drawImage(bagIcon,(int)(7*(float)g2d.getClipBounds().width/8),12,panel);
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
		g2d.drawString("Press B", (int)(7*(float)g2d.getClipBounds().width/8-8), 40);
		g2d.drawString("to open bag", (int)(7*(float)g2d.getClipBounds().width/8-18), 48);
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 20));
	}
	public void paintPlayerEffects(Graphics2D g2d){
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 15));
		for(int i=0;i<EndlessWorlds.getGame().getModuleEntity().getPlayer().getEffectList().size();i++){
			g2d.drawString(EndlessWorlds.getGame().getModuleEntity().getPlayer().getEffectList().get(i).getName()+" "+(int)EndlessWorlds.getGame().getModuleEntity().getPlayer().getEffectList().get(i).getTimeout()+" S", 10, 80+(i*28));
		}
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 20));
	}
	public void paintInventory(Graphics2D g2d){
		if(EndlessWorlds.getGame().getDisplay().getIsInventoryOpen()){
			g2d.setColor(inventoryColor);
			g2d.fillRect((int)g2d.getClipBounds().width/2-(5*60+5*10)/2, g2d.getClipBounds().height/2-(3*60+3*10)/2, 5*60+5*10, 3*60+3*10);//16 is gap between items
			g2d.setColor(Color.WHITE);
		}
	}
	public void paintLevelInfo(Graphics2D g2d){
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 16));
		if(EndlessWorlds.getGame().getWorld().getLevel().getCreator().length()==0){
			g2d.drawString(EndlessWorlds.getGame().getWorld().getLevel().getName(), 150, 80);
		}else{
			g2d.drawString(EndlessWorlds.getGame().getWorld().getLevel().getName()+" by "+EndlessWorlds.getGame().getWorld().getLevel().getCreator(), 150, 80);
		}
	}
	public void paintCurrentTime(Graphics2D g2d){
		if(currentTime!=null){
			String format = new SimpleDateFormat("mm:ss.SSS").format(currentTime);
			g2d.drawString(format, panel.getWidth()-(panel.getWidth()/10), 80);
		}
		//g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 20));
	}
	public void paintLevelRecordTime(Graphics2D g2d){
		if(EndlessWorlds.getGame().getWorld().getCurrentGamemode()==Gamemodes.CHRONO.getGamemode()){
			if(((GamemodeChrono)EndlessWorlds.getGame().getWorld().getCurrentGamemode()).getOldLevelTime()==-1){//No record set or missing file
				g2d.drawString("No record yet!", panel.getWidth()-(panel.getWidth()/10), 140);
			}else{//Record already set
				String format = new SimpleDateFormat("mm:ss.SSS").format(((GamemodeChrono)EndlessWorlds.getGame().getWorld().getCurrentGamemode()).getOldLevelTime());
				g2d.drawString("Record: "+format, panel.getWidth()-(panel.getWidth()/10), 140);
			}
		}
		//g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 20));
	}
	public void paintStrings(Graphics2D g2d){
		for(int i=0;i<EndlessWorlds.getGame().getWorld().getLevel().getStrings().size();i++){
			g2d.drawString(EndlessWorlds.getGame().getWorld().getLevel().getStrings().get(i).getText(), (int)EndlessWorlds.getGame().getWorld().getLevel().getStrings().get(i).getPosition().getX(), (int)EndlessWorlds.getGame().getWorld().getLevel().getStrings().get(i).getPosition().getY());
		}
	}
	
	public void updateTimer(){
		/*if(lastTime!=null){
			last2Time = (Date) lastTime.clone();
		}
		if(currentTime!=null){
			lastTime = (Date) currentTime.clone();
		}*/
		currentTime = new Date(System.currentTimeMillis()-startTime);
		/*
		
		if(last2Time!=null){
			if(last2Time.getTime()>lastTime.getTime() || lastTime.getTime()>currentTime.getTime()){
				System.err.println("ERROR: LastTime of game timer > CurrentTime (Time is going back now???)");
				JOptionPane.showMessageDialog(null, "LastGameTime>Time(time is going back???)");
				EndlessWorlds.getGame().shutdown();
			}
		}*/
	}

	public Date getCurrentTime() {
		return currentTime;
	}
	
}
