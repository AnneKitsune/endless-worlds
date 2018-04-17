package net.supercraft.endlessWorlds.engine;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.NameValuePair;
import net.supercraft.jojoleproUtils.module.control.ConfigNotFoundException;
import net.supercraft.jojoleproUtils.module.control.ModuleConfiguration;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.model.ModuleIO;

public class ModuleConfig extends ModuleConfiguration{
	private static final String configFilePath = "assets/config/config.ed";
	public ModuleConfig(ModuleManager moduleManager) {
		super(moduleManager);
	}
	public void checkAndRegenerateConfig(){//Need to be changed at some point if that start causing lag when loading the game
		ArrayList<NameValuePair> defaultConfigList = new ArrayList<NameValuePair>();
		//KEYBINDS
		defaultConfigList.add(new NameValuePair("AttackKey",KeyEvent.VK_Q));
		defaultConfigList.add(new NameValuePair("JumpKey",KeyEvent.VK_W));
		defaultConfigList.add(new NameValuePair("JumpArrowKey",KeyEvent.VK_UP));
		defaultConfigList.add(new NameValuePair("DownKey",KeyEvent.VK_S));
		defaultConfigList.add(new NameValuePair("DownArrowKey",KeyEvent.VK_DOWN));
		defaultConfigList.add(new NameValuePair("LeftKey",KeyEvent.VK_A));
		defaultConfigList.add(new NameValuePair("LeftArrowKey",KeyEvent.VK_LEFT));
		defaultConfigList.add(new NameValuePair("RightKey",KeyEvent.VK_D));
		defaultConfigList.add(new NameValuePair("RightArrowKey",KeyEvent.VK_RIGHT));
		defaultConfigList.add(new NameValuePair("SuperJumpKey",KeyEvent.VK_SPACE));
		defaultConfigList.add(new NameValuePair("InventoryKey",KeyEvent.VK_B));
		defaultConfigList.add(new NameValuePair("SprintKey",KeyEvent.VK_SHIFT));
		defaultConfigList.add(new NameValuePair("ControlKey",KeyEvent.VK_CONTROL));
		defaultConfigList.add(new NameValuePair("EscapeKey",KeyEvent.VK_ESCAPE));
		defaultConfigList.add(new NameValuePair("DeleteKey",KeyEvent.VK_DELETE));
		defaultConfigList.add(new NameValuePair("LevelEditorRotateKey",KeyEvent.VK_R));
		defaultConfigList.add(new NameValuePair("LevelEditorScaleDownKey",KeyEvent.VK_X));
		//KEYBINDS END
		
		//AUDIO
		defaultConfigList.add(new NameValuePair("MasterVolume",100));
		defaultConfigList.add(new NameValuePair("MusicVolume",100));
		defaultConfigList.add(new NameValuePair("EffectVolume",100));
		defaultConfigList.add(new NameValuePair("MonsterVolume",100));
		
		boolean configOK = true;
		for(int i=0;i<defaultConfigList.size();i++){
			try {
				getConfig(defaultConfigList.get(i).getName());
			} catch (ConfigNotFoundException e) {
				EndlessWorlds.getGame().getModuleConsole().printMessage("Creating a new config field for "+defaultConfigList.get(i).getName()+" with value: "+defaultConfigList.get(i).getValue().toString(), this);
				addConfig(defaultConfigList.get(i));
				configOK = false;
			}
		}
		if(configOK == false){
			ModuleIO.exportObjectToFile(new File(getConfigFilePath()), getConfig());
		}
	}
	public void refreshAudio(){
		int value;
		try {
			value = (int)this.getConfig("MasterVolume").getValue();
			for(int i=0;i<EndlessWorlds.getGame().getModuleAudio().getAudioList().size();i++){
				EndlessWorlds.getGame().getModuleAudio().getAudioList().get(i).setVolume(value);
			}
		} catch (ConfigNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static String getConfigFilePath() {
		return configFilePath;
	}
	
}
