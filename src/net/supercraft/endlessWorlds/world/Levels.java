package net.supercraft.endlessWorlds.world;

import net.supercraft.endlessWorlds.EndlessWorlds;


public enum Levels {
	L001(Level.fromXML(EndlessWorlds.getGame(),"assets/levels/001.xml")),
	L002(Level.fromXML(EndlessWorlds.getGame(),"assets/levels/002.xml")),
	L003(Level.fromXML(EndlessWorlds.getGame(),"assets/levels/003.xml"));
	private Level level;
	public EndlessWorlds ed;
	private Levels(Level level){
		this.level = level;
	}
	public Level getLevel(){
		return this.level;
	}
	public static Level getLevelByNumber(int nbr){
		if(nbr<=Levels.values().length &&nbr>0){//Level 1 : index 0
			return Levels.values()[nbr-1].getLevel();
		}
		System.err.println("Trying to load level from incorrect number: "+nbr);
		return Level.fromXML(EndlessWorlds.getGame(),"assets/levels/001.xml");//Return default if input nbr is incorrect
	}
}
