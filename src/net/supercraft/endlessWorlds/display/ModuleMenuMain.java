package net.supercraft.endlessWorlds.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuMain extends ModuleMenu{
	private JButton sp,mp,settings,extra,exit;
	public ModuleMenuMain(ModuleManager moduleManager) {
		super(moduleManager);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		
		sp = new JButton("Single Player");
		Menu.setupButtonLayout(sp);
		sp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuSinglePlayer(mm));
			}
		});
		this.getPanel().add(sp);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		
		mp = new JButton("Multi Player");
		Menu.setupButtonLayout(mp);
		mp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuMultiPlayer(mm));
			}
		});
		this.getPanel().add(mp);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		
		settings = new JButton("Settings");
		Menu.setupButtonLayout(settings);
		settings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuSettings(mm));
			}
		});
		this.getPanel().add(settings);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		
		extra = new JButton("Extra");
		Menu.setupButtonLayout(extra);
		extra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuLevelEditor(mm));
			}
		});
		this.getPanel().add(extra);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		
		exit = new JButton("Leave Game");
		Menu.setupButtonLayout(exit);
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EndlessWorlds.getGame().shutdown();
			}
		});
		exit.setEnabled(false);
		this.getPanel().add(exit);
		
	}

}
