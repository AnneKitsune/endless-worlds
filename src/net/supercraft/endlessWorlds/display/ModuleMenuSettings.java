package net.supercraft.endlessWorlds.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuSettings extends ModuleMenu{
	private static final long serialVersionUID = 1L;
	private JButton audio,controls,video,back;
	
	public ModuleMenuSettings(ModuleManager moduleManager) {
		super(moduleManager);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		audio = new JButton("Audio");
		Menu.setupButtonLayout(audio);
		audio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuAudio(mm));
			}
		});
		panel.add(audio);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		controls = new JButton("Controls");
		Menu.setupButtonLayout(controls);
		controls.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuControls(mm));
			}
		});
		panel.add(controls);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		video = new JButton("Video");
		Menu.setupButtonLayout(video);
		video.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		video.setEnabled(false);//////////////////////////////
		panel.add(video);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		back = new JButton("Back");
		Menu.setupButtonLayout(back);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuMain(mm));
			}
		});
		panel.add(back);
		
	}
}
