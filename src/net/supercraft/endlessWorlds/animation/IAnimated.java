package net.supercraft.endlessWorlds.animation;

import java.awt.image.BufferedImage;

public interface IAnimated {
	public AnimationSet[] getEnabledAnimationSets();
	public String getTextureFolderLocation();
	public void setDisplayedImage(BufferedImage image);
	public BufferedImage getDisplayedImage();
}
