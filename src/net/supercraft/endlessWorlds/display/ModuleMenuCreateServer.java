package net.supercraft.endlessWorlds.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuCreateServer extends ModuleMenu{

	private static final long serialVersionUID = 1L;
	private JTextField ip,port;
	private JLabel currentGamemode;//change to set of images later
	private String gamemodes[] = {"Arcade","EndlessWorlds","Survival","Race","PvP","Tournament"};
	private int currentGamemodeNumber = 0;
	private JButton gamemode,create,back;
	
	public ModuleMenuCreateServer(ModuleManager moduleManager) {
		super(moduleManager);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		ip = new JTextField("IP");
		Menu.setupComponentLayout(ip);
		ip.setOpaque(false);
		ip.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		panel.add(ip);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		port = new JTextField("PORT");
		Menu.setupComponentLayout(port);
		port.setOpaque(false);
		port.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		panel.add(port);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		gamemode = new JButton("Change Gamemode");
		Menu.setupButtonLayout(gamemode);
		gamemode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(currentGamemodeNumber+1<gamemodes.length){
					currentGamemodeNumber++;
				}else{
					currentGamemodeNumber = 0;
				}
				currentGamemode.setText(gamemodes[currentGamemodeNumber]);
			}
		});
		panel.add(gamemode);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		currentGamemode = new JLabel("Arcade");
		Menu.setupComponentLayout(currentGamemode);
		currentGamemode.setOpaque(false);
		currentGamemode.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		panel.add(currentGamemode);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		create = new JButton("Create Server");
		Menu.setupButtonLayout(create);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		create.setEnabled(false);///////////////////////////
		panel.add(create);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		back = new JButton("Back");
		Menu.setupButtonLayout(back);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuMultiPlayer(mm));
			}
		});
		panel.add(back);
		
	}
	
}
