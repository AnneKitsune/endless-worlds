package net.supercraft.endlessWorlds.gameplay;

import net.supercraft.endlessWorlds.EndlessWorlds;

public class GamemodeEndlessWorlds extends Gamemode{
	
	public GamemodeEndlessWorlds(EndlessWorlds ed){
		super(ed);
		this.name = "Endless Worlds";
		this.levelChoosingAllowed = false;
	}
	public void onLevelChangeRequest() {
		ed.getWorld().loadLevel(ed.getWorld().getNextLevel());
	}
	@Override
	public void onGameStart() {
		// TODO Auto-generated method stub
		
	}
	
}
