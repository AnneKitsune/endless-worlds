package net.supercraft.endlessWorlds.blocks;

import java.lang.reflect.InvocationTargetException;

import net.supercraft.endlessWorlds.EndlessWorlds;

public enum Blocks {
	GRASS(new BlockGrass(EndlessWorlds.getGame())),
	PORTAL(new BlockPortal(EndlessWorlds.getGame())),
	BOUNCING(new BlockBouncing(EndlessWorlds.getGame())),
	LAVA(new BlockLava(EndlessWorlds.getGame())),
	ROCK(new BlockRock(EndlessWorlds.getGame())),
	ICE(new BlockIce(EndlessWorlds.getGame()));
	private Block block;
	private Blocks(Block block){
		this.block = block;
	}
	public Block getBlock(){
		return this.block;
	}
	public static Block createInstanceForName(String name){
		for(int i=0;i<Blocks.values().length;i++){
			if(Blocks.values()[i].getBlock().getClass().getSimpleName().substring(5).toLowerCase().equals(name.toLowerCase())){
				try {
					//return Blocks.values()[i].getBlock().getClass().newInstance();
					return Blocks.values()[i].getBlock().getClass().getDeclaredConstructor(EndlessWorlds.class).newInstance(EndlessWorlds.getGame());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		System.err.println("BLOCK NOT FOUNDED IN BLOCK ENUM!"+name);
		return new BlockGrass(EndlessWorlds.getGame());
	}
}
