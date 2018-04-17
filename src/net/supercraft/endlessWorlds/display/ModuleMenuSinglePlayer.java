package net.supercraft.endlessWorlds.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuSinglePlayer extends ModuleMenu{
	private static final long serialVersionUID = 1L;
	private JButton newGame,loadGame,back;
	
	public ModuleMenuSinglePlayer(ModuleManager moduleManager) {
		super(moduleManager);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		newGame = new JButton("New Game");
		Menu.setupButtonLayout(newGame);
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuNewGame(mm));
			}
		});
		panel.add(newGame);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		loadGame = new JButton("Load Game");
		Menu.setupButtonLayout(loadGame);
		loadGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//EndlessWorlds.getGame().getDisplay().changeMenu(Menus.*.getMenu());
			}
		});
		loadGame.setEnabled(false);//////////////////////////////////
		panel.add(loadGame);
		
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
