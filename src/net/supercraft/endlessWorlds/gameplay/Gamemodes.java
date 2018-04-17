package net.supercraft.endlessWorlds.gameplay;

import net.supercraft.endlessWorlds.EndlessWorlds;

public enum Gamemodes {
	Arcade(new GamemodeArcade(EndlessWorlds.getGame())),
	CHRONO(new GamemodeChrono(EndlessWorlds.getGame())),
	ENDLESSWORLDS(new GamemodeEndlessWorlds(EndlessWorlds.getGame()));
	private Gamemode gamemode;
	private Gamemodes(Gamemode gamemode){
		this.gamemode = gamemode;
	}
	public Gamemode getGamemode() {
		return gamemode;
	}
	
}
