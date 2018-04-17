package net.supercraft.endlessWorlds.engine;

import java.awt.event.KeyEvent;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.display.ModuleMenuGameInterface;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.model.IKeyControllable;
import net.supercraft.jojoleproUtils.module.model.KeyState;

public class Engine extends ModuleManager{
	
	private EndlessWorlds ed;
	private int fps = 0;
	private int frameCount = 0;
	private boolean paused = false;
	
	public Engine(EndlessWorlds endlessWorlds){
		this.ed = endlessWorlds;
	}
	   protected void loop(){//CODE FROM http://www.java-gaming.org/index.php?topic=24220.0
	      //This value would probably be stored elsewhere.
	      final double GAME_HERTZ = 20.0;
	      //Calculate how many ns each frame should take for our target game hertz.
	      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	      //At the very most we will update the game this many times before a new render.
	      //If you're worried about visual hitches more than perfect timing, set this to 1.
	      final int MAX_UPDATES_BEFORE_RENDER = 4;
	      //We will need the last update time.
	      double lastUpdateTime = System.nanoTime();
	      //Store the last time we rendered.
	      double lastRenderTime = System.nanoTime();
	      
	      //If we are able to get as high as this FPS, don't render again.
	      final double TARGET_FPS = 60;
	      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
	      
	      //Simple way of finding FPS.
	      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
	      
	      while (isRunning)
	      {
	         double now = System.nanoTime();
	         int updateCount = 0;
	         
	         if (!paused)
	         {
	             //Do as many game updates as we need to, potentially playing catchup.
	            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
	            {
	               this.updateModules((float)TIME_BETWEEN_UPDATES/1000000);
	               lastUpdateTime += TIME_BETWEEN_UPDATES;
	               updateCount++;
	            }
	   
	            //If for some reason an update takes forever, we don't want to do an insane number of catchups.
	            //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
	            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
	            {
	               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
	            }
	         
	            //Render. To do so, we need to calculate interpolation for a smooth render.
	            /**float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );*/
	            //drawGame(interpolation);
	            //this.updateAllGraphics((float)TARGET_TIME_BETWEEN_RENDERS/1000000);
	            lastRenderTime = now;
	         
	            //Update the frames we got.
	            int thisSecond = (int) (lastUpdateTime / 1000000000);
	            if (thisSecond > lastSecondTime)
	            {
	               fps = frameCount;
	               frameCount = 0;
	               lastSecondTime = thisSecond;
	            }
	         
	            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
	            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
	            {
	               Thread.yield();
	            
	               //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
	               //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
	               //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
	               try {Thread.sleep(1);} catch(Exception e) {} 
	            
	               now = System.nanoTime();
	            }
	         }
	      }
	   }

	/**
	 * Called when a ModuleKeyListener has been added and it is detecting key events in a ModuleWindow that has been added.
	 * @param KeyEvent
	 * @param KeyState (this is a custom only containing 3 fields: KEY_PRESSED,KEY_RELEASED,KEY_TYPED)
	 */
	public void updateKeyState(KeyEvent arg0, KeyState state){
		for(int i=0;i<loadedModules.size();i++){
			if(loadedModules.get(i) instanceof IKeyControllable && loadedModules.get(i).isEnabled()){
				((IKeyControllable)loadedModules.get(i)).updatedKeyState(arg0,state);
			}
		}
	}
	@Deprecated
	public void levelChanged(){//Triggered by World.java
		if(ed.getDisplay().getCurrentMenu().getClass().equals(ModuleMenuGameInterface.class)){
			((ModuleMenuGameInterface)ed.getDisplay().getCurrentMenu()).levelChanged();
		}
	}
	
}
