package net.supercraft.endlessWorlds.items;

import java.awt.image.BufferedImage;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.math.PointXY;

public class ItemCoin extends Item{

	public ItemCoin(EndlessWorlds ed) {
		super(ed);
		this.setTexture("assets/textures/Coin.png");
	}
	public ItemCoin(EndlessWorlds ed, PointXY pos, PointXY size,BufferedImage texture) {
		super(ed, pos, size, texture);
	}
	public void onPickup(){
		ed.getModuleEntity().getPlayer().setCoin(ed.getModuleEntity().getPlayer().getCoin()+1);
	}
}
