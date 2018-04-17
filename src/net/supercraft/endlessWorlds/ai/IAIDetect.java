package net.supercraft.endlessWorlds.ai;

import net.supercraft.jojoleproUtils.math.PointXY;

public interface IAIDetect {
	public void detected(PointXY pos);
	public void notDetected(PointXY pos);
}
