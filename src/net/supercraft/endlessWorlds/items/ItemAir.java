package net.supercraft.endlessWorlds.items;

import java.awt.image.BufferedImage;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.math.PointXY;

public class ItemAir extends Item{
	public ItemAir(EndlessWorlds ed){
		super(ed);
	}
	public ItemAir(EndlessWorlds ed, PointXY pos, PointXY size,
			BufferedImage texture) {
		super(ed, pos, size, texture);
	}
	
}
