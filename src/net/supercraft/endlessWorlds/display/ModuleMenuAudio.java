package net.supercraft.endlessWorlds.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.engine.ModuleConfig;
import net.supercraft.jojoleproUtils.NameValuePair;
import net.supercraft.jojoleproUtils.module.control.ConfigNotFoundException;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuAudio extends ModuleMenu{
	private JLabel masterL,musicL,effectL,monstersL;
	private JSlider master,music,effect,monsters;
	private JButton back;
	private final static int sliderMin = 0;
	private final static int sliderMax = 100;
	public ModuleMenuAudio(ModuleManager moduleManager) {
		super(moduleManager);
		
		AffineTransform textAt = new AffineTransform();
		textAt.scale(2, 2);
		
		
		//////////////////////////////////////
		masterL = new JLabel("Master Volume");
		Menu.setupComponentLayout(masterL);
		masterL.setOpaque(false);
		masterL.setFont(masterL.getFont().deriveFont(textAt));
		masterL.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.getPanel().add(masterL);
		
		master = new JSlider(JSlider.HORIZONTAL,sliderMin,sliderMax,sliderMax);
		Menu.setupComponentLayout(master);
		master.setMajorTickSpacing(25);
		master.setMinorTickSpacing(5);
		master.setPaintTicks(true);
		master.setPaintLabels(true);
		master.setBackground(new Color(0f,0f,0f,0f));
		master.setOpaque(false);
		master.setFocusable(false);
		try {
			master.setValue((int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("MasterVolume").getValue());
		} catch (ConfigNotFoundException e) {
			e.printStackTrace();
		}
		this.getPanel().add(master);
		////////////////////////////////////////
		
		///////////////////////////////////////
		musicL = new JLabel("Music Volume");
		Menu.setupComponentLayout(musicL);
		musicL.setOpaque(false);
		musicL.setFont(masterL.getFont().deriveFont(textAt));
		musicL.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.getPanel().add(musicL);
		
		music = new JSlider(JSlider.HORIZONTAL,sliderMin,sliderMax,sliderMax);
		Menu.setupComponentLayout(music);
		music.setMajorTickSpacing(25);
		music.setMinorTickSpacing(5);
		music.setPaintTicks(true);
		music.setPaintLabels(true);
		music.setBackground(new Color(0f,0f,0f,0f));
		music.setOpaque(false);
		music.setFocusable(false);
		try {
			music.setValue((int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("MusicVolume").getValue());
		} catch (ConfigNotFoundException e) {
			e.printStackTrace();
		}
		this.getPanel().add(music);
		/////////////////////////////////////
		
		////////////////////////////////////
		effectL = new JLabel("Effects Volume");
		Menu.setupComponentLayout(effectL);
		effectL.setOpaque(false);
		effectL.setFont(masterL.getFont().deriveFont(textAt));
		effectL.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.getPanel().add(effectL);
		
		effect = new JSlider(JSlider.HORIZONTAL,sliderMin,sliderMax,sliderMax);
		Menu.setupComponentLayout(effect);
		effect.setMajorTickSpacing(25);
		effect.setMinorTickSpacing(5);
		effect.setPaintTicks(true);
		effect.setPaintLabels(true);
		effect.setBackground(new Color(0f,0f,0f,0f));
		effect.setOpaque(false);
		effect.setFocusable(false);
		try {
			effect.setValue((int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("EffectVolume").getValue());
		} catch (ConfigNotFoundException e) {
			e.printStackTrace();
		}
		this.getPanel().add(effect);
		/////////////////////////////////////
		
		///////////////////////////////////
		monstersL = new JLabel("Monsters Volume");
		Menu.setupComponentLayout(monstersL);
		monstersL.setOpaque(false);
		monstersL.setFont(masterL.getFont().deriveFont(textAt));
		monstersL.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.getPanel().add(monstersL);
		
		monsters = new JSlider(JSlider.HORIZONTAL,sliderMin,sliderMax,sliderMax);
		Menu.setupComponentLayout(monsters);
		monsters.setMajorTickSpacing(25);
		monsters.setMinorTickSpacing(5);
		monsters.setPaintTicks(true);
		monsters.setPaintLabels(true);
		monsters.setBackground(new Color(0f,0f,0f,0f));
		monsters.setOpaque(false);
		monsters.setFocusable(false);
		try {
			monsters.setValue((int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("MonsterVolume").getValue());
		} catch (ConfigNotFoundException e) {
			e.printStackTrace();
		}
		this.getPanel().add(monsters);
		/////////////////////////////////////
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		//////////////////////////////////
		back = new JButton("Back");
		Menu.setupButtonLayout(back);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuSettings(mm));
				for(int i=0;i<EndlessWorlds.getGame().getModuleAudio().getAudioList().size();i++){
					EndlessWorlds.getGame().getModuleAudio().getAudioList().get(i).setVolume(master.getValue());
				}
				
				EndlessWorlds.getGame().getModuleConfiguration().addConfig(new NameValuePair("MasterVolume",master.getValue()));
				EndlessWorlds.getGame().getModuleConfiguration().addConfig(new NameValuePair("MusicVolume",music.getValue()));
				EndlessWorlds.getGame().getModuleConfiguration().addConfig(new NameValuePair("EffectVolume",effect.getValue()));
				EndlessWorlds.getGame().getModuleConfiguration().addConfig(new NameValuePair("MonsterVolume",monsters.getValue()));
				EndlessWorlds.getGame().getModuleConfiguration().exportConfigToFile(new File(ModuleConfig.getConfigFilePath()));
				EndlessWorlds.getGame().getModuleConfiguration().refreshAudio();
				
			}
		});
		this.getPanel().add(back);
		
	}
	
}
