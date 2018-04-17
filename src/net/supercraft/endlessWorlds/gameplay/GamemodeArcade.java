package net.supercraft.endlessWorlds.gameplay;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.display.ModuleMenuLevelChooser;

public class GamemodeArcade extends Gamemode{
	
	public GamemodeArcade(EndlessWorlds ed){
		super(ed);
		this.name = "Arcade";
		this.levelChoosingAllowed = true;
	}
	public void onLevelChangeRequest() {
		EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuLevelChooser(EndlessWorlds.getGame().getEngine()));
		EndlessWorlds.getGame().pause();
	}
	@Override
	public void onGameStart() {
		// TODO Auto-generated method stub
		
	}
	
}
