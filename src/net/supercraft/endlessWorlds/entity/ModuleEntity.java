package net.supercraft.endlessWorlds.entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import net.supercraft.jojoleproUtils.module.control.Module;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.model.IDrawable;
import net.supercraft.jojoleproUtils.module.model.IKeyControllable;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;
import net.supercraft.jojoleproUtils.module.model.KeyState;

public class ModuleEntity extends Module implements IUpdatable,IDrawable,IKeyControllable{
	protected ArrayList<Entity> entityList = new ArrayList<Entity>();
	public ModuleEntity(ModuleManager moduleManager) {
		super(moduleManager);
	}

	@Override
	public void draw(Graphics2D g2d) {
		for(int i=0;i<entityList.size();i++){
			g2d.drawImage(entityList.get(i).getDisplayedImage(),(int)entityList.get(i).getPos().getX()/*-(int)camera.getX()*/,
					(int)entityList.get(i).getPos().getY()/*-(int)camera.getY()*/,null);
		}
	}

	@Override
	public void update(float tpf) {
		for(int i=0;i<entityList.size();i++){
			entityList.get(i).update(tpf);
		}
	}
	public void addEntity(Entity entity){
		entityList.add(entity);
	}
	public void removeEntity(Entity entity){
		for(int i=0;i<entityList.size();i++){
			if(entityList.get(i).equals(entity)){
				entityList.remove(i);
				return;
			}
		}
	}
	public ArrayList<Entity> getEntityList(){
		return entityList;
	}
	public Player getPlayer(){
		for(int i=0;i<entityList.size();i++){
			if(entityList.get(i).getClass().equals(Player.class)){
				return (Player)entityList.get(i);
			}
		}
		return null;
	}

	@Override
	public void updatedKeyState(KeyEvent arg0, KeyState arg1) {
		this.getPlayer().updatedKeyState(arg0, arg1);
	}
	
}
