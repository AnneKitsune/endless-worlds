package net.supercraft.endlessWorlds.display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import net.supercraft.jojoleproUtils.NameValuePair;
import net.supercraft.jojoleproUtils.module.control.ConfigNotFoundException;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;

public class ModuleMenuControls extends ModuleMenu{

	private static final long serialVersionUID = 1L;
	private JButton back, defaultButton;
	private JScrollPane  slider;
	private JViewport viewport;
	private JPanel content;
	private ArrayList<NameValuePair> keybinds= new ArrayList<NameValuePair>();
	
	public ModuleMenuControls(ModuleManager moduleManager) {
		super(moduleManager);
		
		regenerateKeybindsList();
		
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
		content.setPreferredSize(new Dimension(slider.getWidth(),125*keybinds.size()+50+100));
		panel.add(slider);
		
		for(int i=0;i<keybinds.size();i++){
			JLabel label = new JLabel(keybinds.get(i).getName());
			label.setBounds(0, 125*i+50, 200, 100);
			content.add(label);
			final JButton button = new JButton(KeyEvent.getKeyText((int)keybinds.get(i).getValue()));
			button.setBounds(400, 125*i+50, 200, 100);
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					button.setText("Press a key!");
				}
			});
			//button.getInputMap().put(KeyStroke.getKeyStroke(), arg1)
			content.add(button);
		}
		final KeyEventDispatcher keyEvent = new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  int buttonCounter = 0;
		    	  boolean isCancelled = false;
		    	  if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
		    		  isCancelled = true;
		    	  }
		        for(int i=0;i<content.getComponentCount();i++){
		        	if(content.getComponent(i).getClass()==JButton.class){
		        		buttonCounter++;
		        	}
		        	if(content.getComponent(i).hasFocus()&&content.getComponent(i).getClass()==JButton.class){
		        		JButton button = (JButton)content.getComponent(i);
		        		if(button.getText().equals("Press a key!")){
		        			if(isCancelled){
		        				button.setText(KeyEvent.getKeyText((int)keybinds.get(buttonCounter-1).getValue()));
		        				return false;
		        			}
		        			button.setText(KeyEvent.getKeyText(e.getKeyCode()));
		        			try {
								EndlessWorlds.getGame().getModuleConfiguration().getConfig(keybinds.get(buttonCounter-1).getName()).setValue(e.getKeyCode());
							} catch (ConfigNotFoundException e1) {
								e1.printStackTrace();
							}
		        		}
		        	}
		        }
		        return false;
		      }
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(keyEvent);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		defaultButton = new JButton("Default");
		Menu.setupButtonLayout(defaultButton);
		defaultButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEvent);
				EndlessWorlds.getGame().getModuleConfiguration().getConfig().clear();
				EndlessWorlds.getGame().getModuleConfiguration().checkAndRegenerateConfig();
				regenerateKeybindsList();
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuControls(mm));
			}
		});
		panel.add(defaultButton);
		
		this.getPanel().add(Box.createRigidArea(tempBufferSize));
		back = new JButton("Back");
		Menu.setupButtonLayout(back);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEvent);
				EndlessWorlds.getGame().getModuleConfiguration().exportConfigToFile(new File(EndlessWorlds.getGame().getModuleConfiguration().getConfigFilePath()));
				((Display)EndlessWorlds.getGame().getDisplay()).changeMenu(new ModuleMenuSettings(mm));
			}
		});
		panel.add(back);
		
	}
	private void regenerateKeybindsList(){
		for(int i=0;i<EndlessWorlds.getGame().getModuleConfiguration().getConfig().size();i++){
			if(EndlessWorlds.getGame().getModuleConfiguration().getConfig().get(i).getName().endsWith("Key")){
				keybinds.add(EndlessWorlds.getGame().getModuleConfiguration().getConfig().get(i));
			}
		}
	}
}
