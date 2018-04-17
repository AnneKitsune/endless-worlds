package net.supercraft.endlessWorlds.blocks;

import java.awt.Graphics2D;
import java.util.ArrayList;

import net.supercraft.jojoleproUtils.module.control.Module;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.model.IDrawable;
import net.supercraft.jojoleproUtils.module.model.IUpdatable;

public class ModuleBlock extends Module implements IUpdatable,IDrawable{
	protected ArrayList<Block> blockList = new ArrayList<Block>();
	public ModuleBlock(ModuleManager moduleManager) {
		super(moduleManager);
	}

	@Override
	public void draw(Graphics2D g2d) {
		for(int i=0;i<blockList.size();i++){
			g2d.drawImage(blockList.get(i).getTexture(),blockList.get(i).getDisplayAffineTransform(),null);
		}
	}
	@Override
	public void update(float tpf) {
		for(int i=0;i<blockList.size();i++){
			blockList.get(i).update(tpf);
		}
	}
	public void addBlock(Block block){
		blockList.add(block);
	}
	public void removeBlock(Block block){
		for(int i=0;i<blockList.size();i++){
			if(blockList.get(i).equals(block)){
				blockList.remove(i);
				return;
			}
		}
	}
	public ArrayList<Block> getBlockList(){
		return blockList;
	}
	
}
