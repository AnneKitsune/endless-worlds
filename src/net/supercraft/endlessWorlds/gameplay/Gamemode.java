package net.supercraft.endlessWorlds.gameplay;

import net.supercraft.endlessWorlds.EndlessWorlds;

public abstract class Gamemode {
	protected String name="DEFAULT_GAMEMODE";
	protected EndlessWorlds ed;
	protected boolean levelChoosingAllowed = true;
	public Gamemode(EndlessWorlds ed){
		this.ed = ed;
	}
	public abstract void onGameStart();
	public abstract void onLevelChangeRequest();
	public String getName() {
		return name;
	}
	public boolean isLevelChoosingAllowed() {
		return levelChoosingAllowed;
	}
	
}
