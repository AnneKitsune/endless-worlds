package net.supercraft.endlessWorlds.world;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.entity.Mob;
import net.supercraft.endlessWorlds.entity.MobTurtle;
import net.supercraft.endlessWorlds.entity.Player;
import net.supercraft.endlessWorlds.gameplay.Gamemode;
import net.supercraft.jojoleproUtils.math.PointXY;
import net.supercraft.endlessWorlds.gameplay.Gamemodes;

public class World {
	private EndlessWorlds ed;
	private int levelNumber = 0;
	private Gamemode currentGamemode = Gamemodes.ENDLESSWORLDS.getGamemode();
	//private ArrayList<Block> blockList = new ArrayList<Block>();
	//private ArrayList<Item> itemList = new ArrayList<Item>();
	//private ArrayList<Mob> mobList = new ArrayList<Mob>();
	public Level level = new Level(ed);
	
	public World(EndlessWorlds ed){
		this.ed = ed;
		//currentGamemode = new GamemodeEndlessWorlds(ed);
	}
	
	public void loadLevel(Level level){
		this.level = level;
		EndlessWorlds.getGame().getModuleBlock().getBlockList().clear();
		EndlessWorlds.getGame().getModuleEntity().getEntityList().clear();
		EndlessWorlds.getGame().getModuleEntity().addEntity(new Player(EndlessWorlds.getGame()));
		for(int i=0;i<ed.getWorld().getLevel().getBlockList().size();i++){
			getLevel().getBlockList().get(i).resolveRectangle();
			EndlessWorlds.getGame().getModuleBlock().addBlock(getLevel().getBlockList().get(i));
		}
		for(int i=0;i<ed.getWorld().getLevel().getEntityList().size();i++){
			EndlessWorlds.getGame().getModuleEntity().addEntity(getLevel().getEntityList().get(i));
		}
		/**
		 * TEMP
		 */
		final Mob mob = new MobTurtle(EndlessWorlds.getGame());
		mob.setPos(new PointXY(100,0));
		final Mob mob2 = new MobTurtle(EndlessWorlds.getGame());
		mob.setPos(new PointXY(100,0));
		final Mob mob3 = new MobTurtle(EndlessWorlds.getGame());
		mob.setPos(new PointXY(100,0));
		final Mob mob4 = new MobTurtle(EndlessWorlds.getGame());
		mob.setPos(new PointXY(100,0));
		Thread t = new Thread(new Runnable(){
			public void run(){
				EndlessWorlds.getGame().getModuleEntity().addEntity(mob);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				EndlessWorlds.getGame().getModuleEntity().addEntity(mob2);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				EndlessWorlds.getGame().getModuleEntity().addEntity(mob3);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				EndlessWorlds.getGame().getModuleEntity().addEntity(mob4);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		
		
		ed.getModuleEntity().getPlayer().respawn();
		ed.getEngine().levelChanged();
		currentGamemode.onGameStart();
	}
	public Level getNextLevel(){
		levelNumber++;
		/*if(Levels.getLevelByNumber(levelNumber)!=null){
			return Levels.getLevelByNumber(levelNumber);
		}else{
			return Levels.L001.getLevel();
		}*/
		try{
			Level level = Level.fromXML(ed, "assets/levels/"+levelNumber+".xml");
			return level;
		}catch(Exception e){
			System.err.println("Unable to load next level, name:"+"assets/levels/"+levelNumber+".xml"+" Going on fall back level!");
			return Level.fromXML(ed, "assets/levels/FallBackDontReplace.xml");
		}
	}
	public void update(float tpf){
		
	}
	public Level getLevel(){
		return this.level;
	}
	public int getLevelNumber(){
		return levelNumber;
	}

	public Gamemode getCurrentGamemode() {
		return currentGamemode;
	}

	public void setCurrentGamemode(Gamemode currentGamemode) {
		this.currentGamemode = currentGamemode;
	}
	
}
