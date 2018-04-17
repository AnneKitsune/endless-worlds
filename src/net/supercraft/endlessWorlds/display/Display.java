package net.supercraft.endlessWorlds.display;

import java.awt.event.KeyEvent;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.graphicals.Window;
import net.supercraft.jojoleproUtils.math.PointXY;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.control.ModuleNotFoundException;
import net.supercraft.jojoleproUtils.module.model.IKeyControllable;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;
import net.supercraft.jojoleproUtils.module.model.KeyState;
import net.supercraft.jojoleproUtils.module.view.ModuleWindow;

public class Display extends ModuleWindow implements IKeyControllable, IUpdatable{
	protected EndlessWorlds ed;
	
	protected boolean displayDamageScreen = false;
	protected float damageScreenTime = 0;
	protected boolean isInventory = false;
	protected ModuleMenu currentMenu;
	public Display(EndlessWorlds ed, ModuleManager mm){
		super(mm,EndlessWorlds.getDisplayName(),new PointXY(600,400));
		this.ed = ed;
		//this.currentMenu = new MenuGameInterface(ed);
		currentMenu = new ModuleMenuMain(mm);
		setPanel(currentMenu.getPanel());
		mm.addModule(currentMenu);
		currentMenu.getPanel().repaint();
		/*window.setContentPane(currentMenu);
		window.revalidate();
		window.repaint();*/
	}
	
	public void update(float tpf){
		if(displayDamageScreen){
			damageScreenTime+=tpf;
			if(damageScreenTime>=100){
				displayDamageScreen = false;
				damageScreenTime = 0;
			}
		}
	}
	public void changeMenu(ModuleMenu newMenu){
		mm.removeModule(currentMenu);
		currentMenu = newMenu;
		setPanel(newMenu.getPanel());
		mm.addModule(currentMenu);
		currentMenu.getPanel().repaint();
		currentMenu.getPanel().setVisible(true);
	}
	public boolean isDisplayDamageScreen() {
		return displayDamageScreen;
	}

	public void setDisplayDamageScreen(boolean displayDamageScreen) {
		this.displayDamageScreen = displayDamageScreen;
	}

	public float getDamageScreenTime() {
		return damageScreenTime;
	}

	public void setDamageScreenTime(float damageScreenTime) {
		this.damageScreenTime = damageScreenTime;
	}
	public void setIsInventoryOpen(boolean bool){//Change to showInventory(Graphics2D g2d)<-Implements IDrawable
		this.isInventory = bool;
	}
	public boolean getIsInventoryOpen(){
		return isInventory;
	}
	
	public ModuleMenu getCurrentMenu() {
		return currentMenu;
	}

	public void updatedKeyState(KeyEvent arg0, KeyState arg1) {
	}
}
