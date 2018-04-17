package net.supercraft.endlessWorlds.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.gameplay.Gamemodes;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuNewGame extends ModuleMenu{
	private static final long serialVersionUID = 1L;
	private JButton gamemode,start,back;
	private JTextField saveName;
	private JLabel currentGamemode;//change to set of images later
	private int currentGamemodeNumber = 0;
	
	public ModuleMenuNewGame(ModuleManager moduleManager) {
		super(moduleManager);
		//Default gamemode
		EndlessWorlds.getGame().getWorld().setCurrentGamemode(Gamemodes.values()[currentGamemodeNumber].getGamemode());
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		saveName = new JTextField("Game Name");
		Menu.setupComponentLayout(saveName);
		saveName.setOpaque(false);
		saveName.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		panel.add(saveName);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		gamemode = new JButton("Change Gamemode");
		Menu.setupButtonLayout(gamemode);
		gamemode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(currentGamemodeNumber+1<Gamemodes.values().length){
					currentGamemodeNumber++;
				}else{
					currentGamemodeNumber = 0;
				}
				currentGamemode.setText(Gamemodes.values()[currentGamemodeNumber].getGamemode().getName());
				EndlessWorlds.getGame().getWorld().setCurrentGamemode(Gamemodes.values()[currentGamemodeNumber].getGamemode());
			}
		});
		panel.add(gamemode);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		currentGamemode = new JLabel(Gamemodes.values()[0].getGamemode().getName());
		Menu.setupComponentLayout(currentGamemode);
		currentGamemode.setOpaque(false);
		currentGamemode.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		panel.add(currentGamemode);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		start = new JButton("Start Playing!");
		Menu.setupButtonLayout(start);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(EndlessWorlds.getGame().getWorld().getCurrentGamemode().isLevelChoosingAllowed()){
					((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuLevelChooser(mm));
				}else{
					((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuGameInterface(mm));
					EndlessWorlds.getGame().unpause();
				}
			}
		});
		panel.add(start);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		back = new JButton("Back");
		Menu.setupButtonLayout(back);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuSinglePlayer(mm));
			}
		});
		panel.add(back);
		
	}
	public JLabel getCurrentGamemodeLabel(){
		return currentGamemode;
	}
}
