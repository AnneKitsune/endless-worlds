/**


    ______          ____              _       __           __    __    _    ______   ____   _____ ___ 
   / ____/___  ____/ / /__  _________| |     / /___  _____/ /___/ /___| |  / / __ \ / __ \ |__  //   |
  / __/ / __ \/ __  / / _ \/ ___/ ___/ | /| / / __ \/ ___/ / __  / ___/ | / / / / // / / /  /_ </ /| |
 / /___/ / / / /_/ / /  __(__  |__  )| |/ |/ / /_/ / /  / / /_/ (__  )| |/ / /_/ // /_/ / ___/ / ___ |
/_____/_/_///\__,_/_/\___/____/____/ |__/|__/\____/_/__///\__,_/____/ |___/\____(_)____(_)____/_/  |_|
       / ___/__  ______  ___  ___________________ _/ __/ /_                                           
 ______\__ \/ / / / __ \/ _ \/ ___/ ___/ ___/ __ `/ /_/ __/                                           
/_____/__/ / /_/ / /_/ /  __/ /  / /__/ /  / /_/ / __/ /_                                             
     /____/\__,_/ .___/\___/__   \_____/   \__,_/_/  \__/                                             
               /_// /___    (_)___  / /__  ____  _________                                            
 ______________  / / __ \  / / __ \/ / _ \/ __ \/ ___/ __ \                                           
/_____/_____/ /_/ / /_/ / / / /_/ / /  __/ /_/ / /  / /_/ /                                           
            \____/\____/_/ /\____/_/\___/ .___/_/   \____/                                            
                      /____           ___/ __                _                                        
                        / /___  ___  / /  / /   __  ______  (_)__  ____                               
 ____________________  / / __ \/ _ \/ /  / /   / / / / __ \/ / _ \/ __ \                              
/_____/_____/_____/ /_/ / /_/ /  __/ /  / /___/ /_/ / /_/ / /  __/ / / /                              
                  \____/\____/\___/_/  /_____/\__,_/ .___/_/\___/_/ /_/                               
                                                  /_/                                                 

                                          
*/
package net.supercraft.endlessWorlds;

import java.io.File;
import java.sql.Connection;

import net.supercraft.endlessWorlds.animation.ModuleAnimation;
import net.supercraft.endlessWorlds.blocks.ModuleBlock;
import net.supercraft.endlessWorlds.display.Display;
import net.supercraft.endlessWorlds.engine.Engine;
import net.supercraft.endlessWorlds.engine.ModuleConfig;
import net.supercraft.endlessWorlds.engine.ModuleMovement;
import net.supercraft.endlessWorlds.entity.ModuleEntity;
import net.supercraft.endlessWorlds.entity.Player;
import net.supercraft.endlessWorlds.multiplayer.EndlessWorldsServer;
import net.supercraft.endlessWorlds.world.World;
import net.supercraft.jojoleproUtils.InitPhases;
import net.supercraft.jojoleproUtils.module.control.ConfigNotFoundException;
import net.supercraft.jojoleproUtils.module.model.AudioTrack;
import net.supercraft.jojoleproUtils.module.model.ModuleAudio;
import net.supercraft.jojoleproUtils.module.view.ModuleConsole;
import net.supercraft.jojoleproUtils.module.view.ModuleKeyListener;

public class EndlessWorlds extends InitPhases{
	private static EndlessWorlds game;
	public boolean serverMode = false;
	private Connection auth;
	private boolean running = true;
	private EndlessWorldsServer ewServ;
	private World world;
	private Engine engine;
	private static final String version = "0.2.0A";//Config,Keybind,Sound,LevelEditor controls,Menu fix,Tutorial maps
	private static final String displayName = "EndlessWorlds V"+version;
	
	private ModuleConsole mc;
	//Window extends ModuleConsole
	private Display display;//was set to volatile for some reason
	private ModuleConfig mconf;
	private ModuleAudio ma;
	private ModuleKeyListener mkl;
	private ModuleMovement mouv;
	private ModuleBlock mb;
	private ModuleEntity me;
	private ModuleAnimation manim;
	
	public EndlessWorlds(String args[]){
		/*auth = new Connection();
		for(int i=0;i<args.length;i++){
			if(args[i].startsWith("-username")){
				Connection.setUsername(args[i].substring(9));
			}else if(args[i].startsWith("-password")){
				Connection.setPassword(args[i].substring(9));
			}else if(args[i].startsWith("-server")){
				ewServ = new EndlessWorldsServer();
				serverMode = true;
			}else if(args[i].startsWith("-ip")){
				ewServ.setIP(args[i].substring(3));
			}else if(args[i].startsWith("-port")){
				ewServ.setPort(Integer.valueOf(args[i].substring(5)));
			}else{
				System.out.println("Unknow argument "+args[i]+", skipping.");
			}
		}*/
		EndlessWorlds.game = this;
		for(int i=0;i<3;i++){
			this.nextPhase();
		}
	}
	
	public void update(float tpf){
		
	}
	
	private void startClient(){
		/*if(!auth.connect("http://supercraft.net63.net/auth.php")){
			System.err.println("CANNOT LOGGING IN!");
			this.shutdown();
		}*/
		world.loadLevel(world.getNextLevel());
	}
	@SuppressWarnings("unused")
	private void startServer(){
		ewServ = new EndlessWorldsServer();
	}
	
	public ModuleKeyListener getModuleKeyListener(){
		return mkl;
	}
	
	public static String getDisplayName(){
		return displayName;
	}
	
	public Engine getEngine(){
		return engine;
	}
	
	public World getWorld(){
		return world;
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public void shutdown(){//Not working!
		
		System.out.println("Shutting down!");
		System.exit(0);
	}
	protected void preInit() {//Init modules
		world = new World(this);
		
		//MVC
		
		//C
		engine = new Engine(this);
		mconf = new ModuleConfig(engine);
		engine.addModule(mconf);
		//V
		mc = new ModuleConsole(engine);
		engine.addModule(mc);
		mkl = new ModuleKeyListener(engine);
		engine.addModule(mkl);
		display = new Display(this,engine);
		display.getFrame().addKeyListener(mkl);
		engine.addModule(display);
		//M
		ma = new ModuleAudio(engine);
		engine.addModule(ma);
		mb = new ModuleBlock(engine);
		engine.addModule(mb);
		me = new ModuleEntity(engine);
		engine.addModule(me);
		me.addEntity(new Player(this));
		mouv = new ModuleMovement(this,engine);
		engine.addModule(mouv);
		
		manim = new ModuleAnimation(engine);
		engine.addModule(manim);
		
	}
	protected void init() {
		//Loading screen here
		
		//Adding Sound Effects
		this.loadAudio();
		
		mc.printMessage("Importing configurations from file.", this);
		mconf.importConfigFromFile(new File(ModuleConfig.getConfigFilePath()));//Need export when shutdown()
		mconf.checkAndRegenerateConfig();
		
		this.startClient();
	}
	
	protected void postInit() {
		try {
			this.getModuleAudio().getAudioTrack("MUSIC").setVolume((int)mconf.getConfig("MasterVolume").getValue());
		} catch (ConfigNotFoundException e) {
			e.printStackTrace();
		}
		this.getModuleAudio().getAudioTrack("MUSIC").startAudio();
		this.pause();
	}
	public static EndlessWorlds getGame(){
		return EndlessWorlds.game;
	}
	public ModuleAudio getModuleAudio() {
		return ma;
	}
	public void loadAudio(){
		ma.addAudioTrack(new AudioTrack("assets/sound/kevinmacleod-cutandrun.wav","MUSIC"));
		ma.addAudioTrack(new AudioTrack("assets/sound/cartoon_boing_or_spring_jaw_harp_.wav","SUPERJUMP"));
		ma.addAudioTrack(new AudioTrack("assets/sound/relaxation_music.wav","MUSIC2"));
		ma.addAudioTrack(new AudioTrack("assets/sound/cartoon_mouse_laughter_version_1.wav","MOUSE"));
		ma.addAudioTrack(new AudioTrack("assets/sound/cartoon_siren_whistle_short_version_2.wav","SIREN"));
		ma.addAudioTrack(new AudioTrack("assets/sound/cartoon_fast_whoosh_good_for_karate_chop_other_fast_movement_or_swipe_006.wav","SWIPE"));
	}
	public void pause(){
		mb.setEnabled(false);
		me.setEnabled(false);
		mouv.setEnabled(false);
	}
	public void unpause(){
		mb.setEnabled(true);
		me.setEnabled(true);
		mouv.setEnabled(true);
	}
	public ModuleConsole getModuleConsole() {
		return mc;
	}

	public ModuleConfig getModuleConfiguration() {
		return mconf;
	}
	
	public ModuleMovement getModuleMovement() {
		return mouv;
	}
	public ModuleBlock getModuleBlock(){
		return mb;
	}
	public ModuleEntity getModuleEntity() {
		return me;
	}
	public ModuleAnimation getModuleAnimation(){
		return manim;
	}
}
