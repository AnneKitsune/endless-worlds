package net.supercraft.endlessWorlds.display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.world.Level;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuLevelChooser extends ModuleMenu{
	private static final long serialVersionUID = 1L;
	private JButton back,start;
	private JPanel content;
	private JViewport viewport;
	private JScrollPane slider;
	public ModuleMenuLevelChooser(ModuleManager moduleManager){
		super(moduleManager);
		
		File folder = new File("./assets/levels/");
		File[] listOfFiles = folder.listFiles();
		final ArrayList<File> listOfLevels = new ArrayList<File>();
		for(int i=0;i<listOfFiles.length;i++){
			if(listOfFiles[i].isFile()){
				listOfLevels.add(listOfFiles[i]);
			}
		}
		
		content = new JPanel();
		viewport = new JViewport();
        viewport.setView(content);
		slider = new JScrollPane(){
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
			    super.paintComponent(g);
			    //paint here
			}
		};
		slider.setViewport(viewport);
		slider.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Menu.setupComponentLayout(slider);
		slider.setMinimumSize(new Dimension(600,1200));
		slider.setMaximumSize(new Dimension(600,1200));
		
		slider.setOpaque(false);
		slider.setBorder(null);
		viewport.setOpaque(false);
		content.setOpaque(false);
		content.setBorder(null);
		content.setLayout(null);
		content.setPreferredSize(new Dimension(slider.getWidth(),125*listOfLevels.size()+50+100));
		panel.add(slider);
		
		for(int i=0;i<listOfLevels.size();i++){
			JLabel label = new JLabel(listOfLevels.get(i).getPath().substring(8));
			label.setBounds(0, 125*i+50, 200, 100);
			content.add(label);
			final JButton button = new JButton("Load this level.");
			button.setBounds(400, 125*i+50, 200, 100);
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					EndlessWorlds.getGame().getWorld().loadLevel(Level.fromXML(EndlessWorlds.getGame(), listOfLevels.get((int)(button.getBounds().getY()-50)/125).getPath()));
					EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuGameInterface(mm));
					EndlessWorlds.getGame().unpause();
				}
			});
			//button.getInputMap().put(KeyStroke.getKeyStroke(), arg1)
			content.add(button);
		}
		
		/*start = new JButton("Start!");
		Menu.setupButtonLayout(start);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EndlessWorlds.getGame().getDisplay().changeMenu(new MenuGameInterface(EndlessWorlds.getGame()));
				EndlessWorlds.getGame().getEngine().setInGame(true);
				EndlessWorlds.getGame().getWorld().loadLevel(Level.fromXML(EndlessWorlds.getGame(), "assets/levels/"+levelName.getText()));
			}
		});
		this.addButtonWithLayout(start);*/
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		back = new JButton("Back");
		Menu.setupButtonLayout(back);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuNewGame(mm));
			}
		});
		panel.add(back);
	}
}
