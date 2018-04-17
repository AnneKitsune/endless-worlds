package net.supercraft.endlessWorlds.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuMultiPlayer extends ModuleMenu{

	private static final long serialVersionUID = 1L;
	private JButton create,join,back;
	
	public ModuleMenuMultiPlayer(ModuleManager moduleManager) {
		super(moduleManager);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		create = new JButton("Create Server");
		Menu.setupButtonLayout(create);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuCreateServer(mm));
			}
		});
		create.setEnabled(false);
		panel.add(create);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		join = new JButton("Join Server");
		Menu.setupButtonLayout(join);
		join.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		join.setEnabled(false);/////////////////////////////////
		panel.add(join);
		
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
