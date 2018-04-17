package net.supercraft.endlessWorlds.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.blocks.Blocks;
import net.supercraft.endlessWorlds.entity.Mobs;
import net.supercraft.endlessWorlds.world.Level;
import net.supercraft.jojoleproUtils.graphicals.PositionalString;
import net.supercraft.jojoleproUtils.math.PointXY;
import net.supercraft.jojoleproUtils.module.control.ConfigNotFoundException;
import net.supercraft.jojoleproUtils.module.control.ModuleManager;
import net.supercraft.jojoleproUtils.module.model.KeyState;

public class ModuleMenuLevelEditor extends ModuleMenu implements MouseMotionListener{
	private JTextField tPosX,tSizeX,tRot,tSizeY,tPosY;
	private JButton exit,buttonApply;
	private JScrollPane blocks,mobs,items,levelEditing;
	private JPanel blocksP,mobsP,itemsP;
	private JPanel levelEditingP;
	private JCheckBox cCollide;
	private LevelEditorItem selectedType;//The one use to copy
	private LevelEditorItem selectedItem;//The one IN the Level Editor Panel
	private CustomMouseAdapter cma;
	private static final PointXY DEFAULT_ICON_SIZE = new PointXY(40,40);
	private JFileChooser importExport = new JFileChooser();
	private JPanel levelInfo;
	private JTextField tSpawnpointX;
	private JTextField tSpawnpointY;
	private JTextField tLevelName;
	private JTextField tCreator;
	private Font font;
	private JPanel addTextP;
	private JTextField textF;
	private JLabel textL;
	private boolean up=false,down=false,left=false,right=false,up2=false,down2=false,left2=false,right2=false,rotate=false,scaleDown=false,shift=false,control=false;
	
	/**
	 * MenuLevelEditor, this is where all instances of displayable objects are setup
	 * @param ed
	 * THIS IS BROKEN RIGHT NOW
	 */
	@Deprecated
	public ModuleMenuLevelEditor(ModuleManager moduleManager) {
		super(moduleManager);
		/////////////////////////////////////////////////Custom formlayout
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("70px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(2dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(180dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("70px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(0dlu;default)"),},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("70px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(124dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("70px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		///////////////////////////////////////////////////Custom formlayout END
		
		//////////////////////////////////////////////////Instances setup
		cma = new CustomMouseAdapter();
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("assets/font/Anarchy.ttf")).deriveFont(Font.PLAIN, 16);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		panel.addMouseMotionListener(this);
        //Mouse motion listener = drag?
		importExport.setCurrentDirectory(new File("assets"+System.getProperty("file.separator")+"levels"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "EndlessWorlds Map Files .xml", "xml");
		importExport.addChoosableFileFilter(filter);
		importExport.setAcceptAllFileFilterUsed(false);
		importExport.setFileFilter(filter);
		/////////////////////////////////////////////////Instances setup END
		
		//////////////////////////////////////////Blocks JScollPane
		blocksP = new JPanel();
		blocksP.addMouseMotionListener(this);
		blocks = new JScrollPane();
		blocks.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		blocks.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		blocks.setViewportView(blocksP);
		panel.add(blocks, "6, 2, fill, fill");
		for(int i=0;i<Blocks.values().length;i++){
			BufferedImage after = new BufferedImage((int)DEFAULT_ICON_SIZE.getX(),(int)DEFAULT_ICON_SIZE.getY(), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale((double)DEFAULT_ICON_SIZE.getX()/Blocks.values()[i].getBlock().getTexture().getWidth(), (double)DEFAULT_ICON_SIZE.getY()/Blocks.values()[i].getBlock().getTexture().getHeight());
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(Blocks.values()[i].getBlock().getTexture(), after);
			LevelEditorItem im = new LevelEditorItem();
			im.setIcon(new ImageIcon(after));
			im.setOriginalTexture(Blocks.values()[i].getBlock().getTexture());
			im.setName(Blocks.values()[i].getBlock().getClass().getSimpleName().substring(5));
	        im.addMouseListener(cma);
	        im.addKeyListener(EndlessWorlds.getGame().getModuleKeyListener());
			blocksP.add(im);
		}
		//////////////////////////////////////////Blocks JScollPane END
		
		//////////////////////////////////////////Items JScollPane
		itemsP = new JPanel();
		itemsP.setLayout(new BoxLayout(itemsP,BoxLayout.Y_AXIS));
		itemsP.addMouseMotionListener(this);
		
		levelInfo = new JPanel();
		panel.add(levelInfo, "8, 2, fill, fill");
		levelInfo.setLayout(null);
		
		JLabel lSpawnPoint = new JLabel("SP");
		lSpawnPoint.setBounds(0, 20, 12, 14);
		levelInfo.add(lSpawnPoint);
		
		JLabel lLevelInfo = new JLabel("Level Info");
		lLevelInfo.setBounds(10, 1, 48, 14);
		levelInfo.add(lLevelInfo);
		
		tSpawnpointX = new JTextField();
		tSpawnpointX.setBounds(15, 18, 30, 20);
		levelInfo.add(tSpawnpointX);
		tSpawnpointX.setColumns(10);
		
		tSpawnpointY = new JTextField();
		tSpawnpointY.setBounds(45, 18, 30, 20);
		levelInfo.add(tSpawnpointY);
		tSpawnpointY.setColumns(10);
		
		JLabel lName = new JLabel("Name");
		lName.setBounds(0, 40, 30, 14);
		levelInfo.add(lName);
		
		tLevelName = new JTextField();
		tLevelName.setBounds(25, 37, 45, 20);
		levelInfo.add(tLevelName);
		tLevelName.setColumns(10);
		
		JLabel lCreator = new JLabel("Creator");
		lCreator.setBounds(0, 56, 38, 14);
		levelInfo.add(lCreator);
		
		tCreator = new JTextField();
		tCreator.setBounds(40, 56, 30, 20);
		levelInfo.add(tCreator);
		tCreator.setColumns(10);
		
		
		items = new JScrollPane();
		items.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		items.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		items.setViewportView(itemsP);
		panel.add(items, "8, 4, fill, fill");
		for(int i=0;i<Blocks.values().length;i++){
			/*BufferedImage after = new BufferedImage((int)DEFAULT_ICON_SIZE.getX(),(int)DEFAULT_ICON_SIZE.getY(), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale((double)DEFAULT_ICON_SIZE.getX()/Blocks.values()[i].getBlock().getTexture().getWidth(), (double)DEFAULT_ICON_SIZE.getY()/Blocks.values()[i].getBlock().getTexture().getHeight());
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(Blocks.values()[i].getBlock().getTexture(), after);
			LevelEditorItem im = new LevelEditorItem(new ImageIcon(after));
			im.setOriginalTexture(Blocks.values()[i].getBlock().getTexture());
	        im.addMouseListener(cma);
			itemsP.add(im);
			im.addKeyListener(ed.getModuleKeyListener());*/
		}
		//////////////////////////////////////////Items JScollPane END
		
		//////////////////////////////////////////Mobs JScollPane
		mobsP = new JPanel();
		mobsP.addMouseMotionListener(this);
		mobs = new JScrollPane();
		mobs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		mobs.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		mobs.setViewportView(mobsP);
		panel.add(mobs, "6, 6, fill, fill");
		for(int i=0;i<Mobs.values().length;i++){
			BufferedImage after = new BufferedImage((int)DEFAULT_ICON_SIZE.getX(),(int)DEFAULT_ICON_SIZE.getY(), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale((double)DEFAULT_ICON_SIZE.getX()/Mobs.values()[i].getMob().getTexture().getWidth(), (double)DEFAULT_ICON_SIZE.getY()/Mobs.values()[i].getMob().getTexture().getHeight());
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(Mobs.values()[i].getMob().getTexture(), after);
			LevelEditorItem im = new LevelEditorItem();
			im.setIcon(new ImageIcon(after));
			im.setOriginalTexture(Mobs.values()[i].getMob().getTexture());
			im.setName(Mobs.values()[i].getMob().getClass().getSimpleName().substring(3));
	        im.addMouseListener(cma);
	        im.addKeyListener(EndlessWorlds.getGame().getModuleKeyListener());
			mobsP.add(im);
		}
		///////////////////////////////////////////Mobs JScollPane END
		
		///////////////////////////////////////////LevelEditing JScrollPane
		levelEditingP = new JPanel(){
			static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.clearRect(0, 0, WIDTH, HEIGHT);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setFont(font);
				for(int i=0;i<levelEditingP.getComponentCount();i++){
					if(levelEditingP.getComponent(i).getClass().equals(LevelEditorItem.class)){
						g2d.drawImage((BufferedImage)((LevelEditorItem)levelEditingP.getComponent(i)).getOriginalTexture(), (AffineTransform)((LevelEditorItem)levelEditingP.getComponent(i)).getAffineTransform(), this);
					}
				}
				
				if(tCreator.getText().length()==0){
					g2d.drawString(tLevelName.getText(), 40, 20);
				}else{
					g2d.drawString(tLevelName.getText()+" by "+tCreator.getText(), 40, 20);
				}
				/*if(selectedItem!=null){//Border of selected block
					g2d.setColor(Color.RED);
					g2d.rotate(selectedItem.getRotation(), this.getWidth()/2, this.getHeight()/2);
					g2d.drawRect(selectedItem.getX(), selectedItem.getY(), selectedItem.getWidth(), selectedItem.getHeight());
					g2d.drawRect(selectedItem.getX()+1, selectedItem.getY()+1, selectedItem.getWidth()-2, selectedItem.getHeight()-2);
					g2d.drawRect(selectedItem.getX()+2, selectedItem.getY()+2, selectedItem.getWidth()-4, selectedItem.getHeight()-4);
					g2d.rotate(-selectedItem.getRotation(), this.getWidth()/2, this.getHeight()/2);
					g2d.setColor(Color.BLACK);
				}*/
			}
		};
		levelEditingP.setPreferredSize(new Dimension(3000,3000));//We set a maximum size(needed for JScrollPane)
		levelEditingP.setBounds(0, 0, 3000, 3000);//We set a maximum size
		levelEditing = new JScrollPane();
		levelEditing.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		levelEditing.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		levelEditing.setViewportView(levelEditingP);
		levelEditingP.setLayout(null);
		levelEditingP.addMouseListener(cma);
		levelEditingP.addMouseMotionListener(this);
		levelEditing.addMouseMotionListener(this);
		panel.add(levelEditing, "6, 4, fill, fill");
		
		/**dropHandler = new DropHandler();*/
		/**dropTarget = new DropTarget(levelEditingP, DnDConstants.ACTION_COPY, dropHandler, true);*///DRAG AND DROP CODE FROM: http://stackoverflow.com/questions/11201734/java-how-to-drag-and-drop-jpanel-with-its-components
		///////////////////////////////////////////LevelEditing JScrollPane END
		
		///////////////////////////////////////////Block,Mob,Item Properties tab
		JPanel properties = new JPanel();
		panel.add(properties, "2, 4, 3, 1, fill, fill");
		properties.setLayout(null);
		
		JLabel lblProperties = new JLabel("Properties");
		lblProperties.setBounds(10, 0, 49, 14);
		lblProperties.setHorizontalAlignment(SwingConstants.CENTER);
		properties.add(lblProperties);
		
		JLabel lblPos = new JLabel("Pos");
		lblPos.setBounds(0, 59, 27, 14);
		properties.add(lblPos);
		
		JLabel lblSize = new JLabel("Size");
		lblSize.setBounds(0, 80, 27, 14);
		properties.add(lblSize);
		
		JLabel lblRot = new JLabel("Rot");
		lblRot.setBounds(0, 100, 17, 14);
		properties.add(lblRot);
		
		/*cType = new JComboBox<String>();//may contain Block,item or mob. int used in LevelEditorItem static final type fields
		cType.setMaximumRowCount(3);
		cType.addItem("Block");
		cType.addItem("Item");
		cType.addItem("Mob");
		cType.setBounds(30, 37, 49, 20);
		properties.add(cType);*/
		
		tPosX = new JTextField();
		tPosX.setColumns(10);
		tPosX.setBounds(30, 56, 30, 20);
		properties.add(tPosX);
		
		tPosY = new JTextField();
		tPosY.setColumns(10);
		tPosY.setBounds(60, 56, 30, 20);
		properties.add(tPosY);
		
		tSizeX = new JTextField();
		tSizeX.setColumns(10);
		tSizeX.setBounds(30, 77, 30, 20);
		properties.add(tSizeX);
		
		tSizeY = new JTextField();
		tSizeY.setColumns(10);
		tSizeY.setBounds(60, 77, 30, 20);
		properties.add(tSizeY);
		
		tRot = new JTextField();
		tRot.setColumns(10);
		tRot.setBounds(30, 97, 49, 20);
		properties.add(tRot);
		
		cCollide = new JCheckBox("Collide?");
		cCollide.setSelected(true);
		cCollide.setBounds(0, 121, 97, 23);
		cCollide.setFocusable(false);
		properties.add(cCollide);
		
		buttonApply = new JButton("Apply");
		buttonApply.setBounds(0, 167, 79, 23);
		buttonApply.setFocusable(false);
		buttonApply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(selectedItem!=null){
					repaintSelectedItem();
				}
				EndlessWorlds.getGame().getDisplay().getPanel().requestFocus();//We request the focus so people can use the key controls without having to do crazy things like pressing TAB 1000times
			}
		});
		properties.add(buttonApply);
		
		textL = new JLabel("Text");
		textL.setBounds(0, 151, 46, 14);
		properties.add(textL);
		textL.setVisible(false);
		
		textF = new JTextField();
		textF.setBounds(30, 145, 49, 20);
		properties.add(textF);
		textF.setColumns(10);
		textF.setVisible(false);
		////////////////////////////////////////////////////////End of properties
		
		
		exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {//from stackoverflow: http://stackoverflow.com/questions/8658576/joptionpane-with-multiple-buttons-on-each-line
				String[] buttons = {"Save", "Load", "Quit", "Cancel"};    
				int returnValue = JOptionPane.showOptionDialog(null, "Save Options", "Save Options",
				        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[0]);
				switch(returnValue){
				case 0:exportLevel();
					break;
				case 1:importLevel();
				break;
				case 2:EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuMain(mm));
					break;
				default:
					break;
				}
			}
		});
		panel.add(exit, "2, 2");
		
		addTextP = new JPanel();
		panel.add(addTextP, "8, 6, fill, fill");
		LevelEditorItem sampleText = new LevelEditorItem();
		sampleText.setText("Sample Text");
		sampleText.addMouseListener(cma);
		addTextP.add(sampleText);
	}
	public void update(float tpf){
		this.transformSelectedItem();
		levelEditingP.repaint();
		levelEditingP.revalidate();
	}
	@Override
	public void updatedKeyState(KeyEvent arg0, KeyState arg1) {
		try {
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("JumpKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					up=true;
				}else{//Key is released
					up=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("JumpArrowKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					up2=true;
				}else{//Key is released
					up2=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("DownKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					down=true;
				}else{//Key is released
					down=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("DownArrowKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					down2=true;
				}else{//Key is released
					down2=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("LeftKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					left=true;
				}else{//Key is released
					left=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("LeftArrowKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					left2=true;
				}else{//Key is released
					left2=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("RightKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					right=true;
				}else{//Key is released
					right=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("RightArrowKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					right2=true;
				}else{//Key is released
					right2=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("LevelEditorRotateKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					rotate=true;
				}else{//Key is released
					rotate=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("LevelEditorScaleDownKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					scaleDown=true;
				}else{//Key is released
					scaleDown=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("ControlKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					control=true;
				}else{//Key is released
					control=false;
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("SprintKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					shift=true;
				}else{//Key is released
					shift=false;
				}
			}
			
			
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("EscapeKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					this.deselectType();
					this.deselectItem();
				}
			}
			if(arg0.getKeyCode()==(int)EndlessWorlds.getGame().getModuleConfiguration().getConfig("DeleteKey").getValue()){
				if(arg1!=KeyState.KEY_RELEASED){//Key has been pressed
					if(selectedItem!=null){
						for(int i=0;i<levelEditingP.getComponentCount();i++){
							if(selectedItem.equals(levelEditingP.getComponent(i))){
								levelEditingP.remove(i);
								break;
							}
						}
					}
				}
			}
		} catch (ConfigNotFoundException e) {
			System.out.println("MenuLevelEditor: Key not founded in ModuleConfiguration");
		}
	}
	/**
	 * Import a level from a selected file
	 */
	public void importLevel(){
		importExport.setDialogTitle("Import EndlessWorlds Map Files");
		importExport.setApproveButtonText("Import");
		int returnVal = importExport.showOpenDialog(null);
		if(returnVal==0){
			//We import a map!
			levelEditingP.removeAll();
			File toLoad = new File(importExport.getSelectedFile().getAbsolutePath());
			if(!toLoad.getAbsolutePath().endsWith(".xml")){
				toLoad = new File(importExport.getSelectedFile().getAbsolutePath()+".xml");
			}
			Level l = Level.fromXML(EndlessWorlds.getGame(), toLoad.getAbsolutePath());
			if(l!=null){
				for(int i=0;i<l.getBlockList().size();i++){//Blocks loading
					LevelEditorItem j = new LevelEditorItem();
					j.setBounds(l.getBlockList().get(i).getPosX(),l.getBlockList().get(i).getPosY(),l.getBlockList().get(i).getSizeX(),l.getBlockList().get(i).getSizeY());
					j.setRotation(l.getBlockList().get(i).getRotation());
					j.setNewIcon(new PointXY(l.getBlockList().get(i).getSizeX(),l.getBlockList().get(i).getSizeY()), l.getBlockList().get(i).getTexture());
					j.setIcon(null);
					j.setBorder(BorderFactory.createEmptyBorder());
					j.setType(LevelEditorItem.TYPE_BLOCK);
					j.setName(l.getBlockList().get(i).getClass().getSimpleName().substring(5));
					j.setOriginalTexture(l.getBlockList().get(i).getTexture());
					j.addMouseListener(cma);
					j.addKeyListener(EndlessWorlds.getGame().getModuleKeyListener());
					levelEditingP.add(j);
					levelEditingP.repaint();
				}//Blocks loading END
				//Strings
				for(int i=0;i<l.getStrings().size();i++){
					LevelEditorItem j = new LevelEditorItem();
					j.setBounds((int)l.getStrings().get(i).getPosition().getX(), (int)l.getStrings().get(i).getPosition().getY(), 300, 40);
					j.setName(l.getStrings().get(i).getText());
					j.setText(l.getStrings().get(i).getText());
					j.setType(LevelEditorItem.TYPE_TEXT);
					j.addMouseListener(cma);
					j.addKeyListener(EndlessWorlds.getGame().getModuleKeyListener());
					j.setBorder(BorderFactory.createEmptyBorder());
					levelEditingP.add(j);
					levelEditingP.repaint();
				}
				//Strings END
				//Properties loading
				tSpawnpointX.setText(String.valueOf(l.getSpawnpoint().getX()));
				tSpawnpointY.setText(String.valueOf(l.getSpawnpoint().getY()));
				tLevelName.setText(l.getName());
				tCreator.setText(l.getCreator());
				//Properties loading END
			}
		}
		EndlessWorlds.getGame().getDisplay().getPanel().requestFocus();//We request the focus so people can use the key controls without having to do crazy things like pressing TAB 1000times
	}
	/**
	 * Export a level to a selected file
	 */
	public void exportLevel(){
		importExport.setDialogTitle("Export EndlessWorlds Map Files");
		importExport.setApproveButtonText("Export");
		int returnVal = importExport.showOpenDialog(null);
		if(returnVal==0){
			//We export the new map!
			Level level = new Level(EndlessWorlds.getGame());
			//level.setSpawnpoint();@TODO
			for(int i=0;i<levelEditingP.getComponentCount();i++){
				LevelEditorItem lei = (LevelEditorItem)levelEditingP.getComponent(i);
				if(lei.getType()==LevelEditorItem.TYPE_BLOCK){
					level.getBlockList().add(Blocks.createInstanceForName(((LevelEditorItem)levelEditingP.getComponent(i)).getName()));
					if(level.getBlockList().get(level.getBlockList().size()-1)==null){//Safety in case we can't create block because of an incorrect or null name
						System.err.println("Error when preparing level for exporting: Block is null because name is incorrect(not name of a known block):"+((LevelEditorItem)levelEditingP.getComponent(i)).getName());
						level.getBlockList().remove(level.getBlockList().size()-1);
						break;
					}
					level.getBlockList().get(level.getBlockList().size()-1).setRotation(((LevelEditorItem)levelEditingP.getComponent(i)).getRotation());//Rotation
					level.getBlockList().get(level.getBlockList().size()-1).setPosX((int)((LevelEditorItem)levelEditingP.getComponent(i)).getBounds().getX());//PosX
					level.getBlockList().get(level.getBlockList().size()-1).setPosY((int)((LevelEditorItem)levelEditingP.getComponent(i)).getBounds().getY());//PosY
					level.getBlockList().get(level.getBlockList().size()-1).setSizeX((int)((LevelEditorItem)levelEditingP.getComponent(i)).getBounds().getWidth());//SizeX
					level.getBlockList().get(level.getBlockList().size()-1).setSizeY((int)((LevelEditorItem)levelEditingP.getComponent(i)).getBounds().getHeight());//SizeY
				}else if(lei.getType()==LevelEditorItem.TYPE_ITEM){
					
				}else if(lei.getType()==LevelEditorItem.TYPE_ENTITY){
					
				}else if(lei.getType()==LevelEditorItem.TYPE_TEXT){
					level.getStrings().add(new PositionalString(new PointXY(levelEditingP.getComponent(i).getX(),levelEditingP.getComponent(i).getY()),levelEditingP.getComponent(i).getName()));
				}
			}
			//Properties export
			if(tSpawnpointX.getText()!=""&&tSpawnpointY.getText()!=""){
				level.setSpawnpoint(new PointXY(Double.valueOf(tSpawnpointX.getText()),Double.valueOf(tSpawnpointY.getText())));
			}
			level.setName(tLevelName.getText());
			level.setCreator(tCreator.getText());
			//Properties export END
			
			
			File file = importExport.getSelectedFile();
			if(file.getAbsolutePath().endsWith(".xml")){
				Level.exportAsXML(level, file.getAbsolutePath());
			}else{
				File afile = new File(file.getAbsoluteFile()+".xml");
				Level.exportAsXML(level, afile.getAbsolutePath());
			}
			
		}else{
			//We do nothing in the case a file has not been chosen
		}
		EndlessWorlds.getGame().getDisplay().getPanel().requestFocus();//We request the focus so people can use the key controls without having to do crazy things like pressing TAB 1000times
	}
	public void mouseDragged(MouseEvent e) {
		//System.out.println("Mouse dragged!");
	}
	public void mouseMoved(MouseEvent e) {//Fake drag method @TODO
		//System.out.println("Mouse moved!");
	}
	/**
	 * Deselect the selected Type item(the default ones in the 3 bars)
	 */
	private void deselectType(){//Deselect current selected Type(in selection scollpanes)
		if(selectedType!=null){//Avoid null when setting the first red border (we set border to see the one selected
			selectedType.setBorder(BorderFactory.createEmptyBorder());
		}
		selectedType=null;
	}
	/**
	 * Deselect the selected item(the one with a red square in the level editor main panel
	 */
	private void deselectItem(){//Deselect current selected item(in level editor panel)
		if(selectedItem!=null){//Avoid null when setting the first red border (we set border to see the one selected
			selectedItem.setBorder(BorderFactory.createEmptyBorder());
		}
		selectedItem=null;
	}
	/**
	 * Updates the left board with the current selectedItem's data
	 */
	private void updateItemStats(){
		if(selectedItem!=null){
			//cType.setSelectedIndex(selectedItem.getType());
			tPosX.setText(String.valueOf((int)selectedItem.getBounds().getX()));
			tPosY.setText(String.valueOf((int)selectedItem.getBounds().getY()));
			tSizeX.setText(String.valueOf((int)selectedItem.getBounds().getWidth()));
			tSizeY.setText(String.valueOf((int)selectedItem.getBounds().getHeight()));
			tRot.setText(String.valueOf(selectedItem.getRotation()));
			cCollide.setSelected(selectedItem.isCollide());
			if(selectedItem.getType()==LevelEditorItem.TYPE_TEXT){
				textL.setVisible(true);
				textF.setVisible(true);
				textF.setText(selectedItem.getName());
			}else{
				textL.setVisible(false);
				textF.setVisible(false);
			}
		}
	}
	/**
	 * Repaint the current selected item(the one in the main panel area) with the data in the left panel
	 */
	private void repaintSelectedItem(){
		try{
			if(!tPosX.getText().equals("")&&!tPosY.getText().equals("")&&!tSizeX.getText().equals("")&&!tSizeY.getText().equals("")){
				selectedItem.setBounds(Integer.parseInt(tPosX.getText()), Integer.parseInt(tPosY.getText()), Integer.parseInt(tSizeX.getText()), Integer.parseInt(tSizeY.getText()));
			}
			if(!tRot.getText().equals("")){
				selectedItem.setRotation(Float.parseFloat(tRot.getText()));
			}
		}catch(NumberFormatException ex){
			JOptionPane.showMessageDialog(null, "Please, do not put anything else than numbers in Position,Size and Rotation fields.");
		}
		selectedItem.setCollide(cCollide.isSelected());
		if(selectedItem.getType()!=LevelEditorItem.TYPE_TEXT){
			selectedItem.setNewIcon(new PointXY(selectedItem.getWidth(),selectedItem.getHeight()), selectedItem.getOriginalTexture());
		}
		if(textF.isVisible()&&textF.getText()!=""){
			selectedItem.setName(textF.getText());
			selectedItem.setText(textF.getText());
		}
	}
	/**
	 * Move,rotate and scale the selected item(the one in the main panel area with a red square) using the pressed keys
	 * 
	 * Move: Move in the pressed ARROW key in the correct direction, ctlr+arrow=speed up
	 * Rotate: Rotate if R is pressed and rotate the other way if ctrl+R is pressed
	 * Scale: Ctrl is inverting the key(descaling)
	 *     w:Scale UP
	 *     s:Scale ALL
	 *     a:Scale LEFT
	 *     d:Scale RIGHT
	 *     x:Scale DOWN
	 */
	private void transformSelectedItem(){
		if(!control&&selectedItem!=null){//Control not pressed
			//trans
			if(up2){
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY()-1, selectedItem.getWidth(), selectedItem.getHeight());
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(down2){
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY()+1, selectedItem.getWidth(), selectedItem.getHeight());
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(left2){
				selectedItem.setBounds(selectedItem.getX()-1, selectedItem.getY(), selectedItem.getWidth(), selectedItem.getHeight());
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(right2){
				selectedItem.setBounds(selectedItem.getX()+1, selectedItem.getY(), selectedItem.getWidth(), selectedItem.getHeight());
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			//rot
			if(rotate){
				selectedItem.setRotation(selectedItem.getRotation()+1);
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			//scale
			if(up){
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY()-1, selectedItem.getWidth(), selectedItem.getHeight()+1);//ResizeUp++
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(down){
				selectedItem.setBounds(selectedItem.getX()-1, selectedItem.getY()-1, selectedItem.getWidth()+2, selectedItem.getHeight()+2);//ResizeAll++
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(left){
				selectedItem.setBounds(selectedItem.getX()-1, selectedItem.getY(), selectedItem.getWidth()+1, selectedItem.getHeight());//ResizeLeft++
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(right){
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY(), selectedItem.getWidth()+1, selectedItem.getHeight());//ResizeRight++
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(scaleDown){//ResizeDown++
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY(), selectedItem.getWidth(), selectedItem.getHeight()+1);
				this.updateItemStats();
				this.repaintSelectedItem();
			}
		}else if(selectedItem!=null){//Control Pressed
			//trans
			if(up2){
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY()-5, selectedItem.getWidth(), selectedItem.getHeight());
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(down2){
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY()+5, selectedItem.getWidth(), selectedItem.getHeight());
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(left2){
				selectedItem.setBounds(selectedItem.getX()-5, selectedItem.getY(), selectedItem.getWidth(), selectedItem.getHeight());
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(right2){
				selectedItem.setBounds(selectedItem.getX()+5, selectedItem.getY(), selectedItem.getWidth(), selectedItem.getHeight());
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			//rot
			if(rotate){
				selectedItem.setRotation(selectedItem.getRotation()-1);
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			//scale
			if(up){
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY()+1, selectedItem.getWidth(), selectedItem.getHeight()-1);//ResizeUp++
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(down){
				selectedItem.setBounds(selectedItem.getX()+1, selectedItem.getY()+1, selectedItem.getWidth()-2, selectedItem.getHeight()-2);//ResizeAll++
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(left){
				selectedItem.setBounds(selectedItem.getX()+1, selectedItem.getY(), selectedItem.getWidth()-1, selectedItem.getHeight());//ResizeLeft++
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(right){
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY(), selectedItem.getWidth()-1, selectedItem.getHeight());//ResizeRight++
				this.updateItemStats();
				this.repaintSelectedItem();
			}
			if(scaleDown){//ResizeDown++
				selectedItem.setBounds(selectedItem.getX(), selectedItem.getY(), selectedItem.getWidth(), selectedItem.getHeight()-1);
				this.updateItemStats();
				this.repaintSelectedItem();
			}
		}
		if(selectedItem!=null){
			if(selectedItem.getBounds().width<=1){
				selectedItem.setSize(2,2);
			}
			if(selectedItem.getBounds().height<=1){
				selectedItem.setSize(2,2);
			}
		}
	}
	/**
	 *The custom mouse adapter used to detect clicks on the differents panels of the interface.
	 */
	private class CustomMouseAdapter extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			if(e.getButton()==MouseEvent.BUTTON1){//Left click
				if(e.getComponent().getParent().equals(blocksP)||e.getComponent().getParent().equals(itemsP)||e.getComponent().getParent().equals(mobsP)||e.getComponent().getParent().equals(addTextP)){
					deselectType();
					selectedType = (LevelEditorItem)e.getComponent();
					selectedType.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
					if(e.getComponent().getParent().equals(blocksP)){//We set the type of the object
						selectedType.setType(LevelEditorItem.TYPE_BLOCK);
					}else if(e.getComponent().getParent().equals(itemsP)){
						selectedType.setType(LevelEditorItem.TYPE_ITEM);
					}else if(e.getComponent().getParent().equals(mobsP)){
						selectedType.setType(LevelEditorItem.TYPE_ENTITY);
					}else if(e.getComponent().getParent().equals(addTextP)){
						selectedType.setType(LevelEditorItem.TYPE_TEXT);
					}
				}else if(e.getComponent().equals(levelEditingP)){//LEVEL EDITING PANEL SECTION----------------------------------------
					deselectItem();
					if(selectedType!=null){//ADD NEW ITEM
						LevelEditorItem j = (LevelEditorItem)selectedType.clone();
						j.setBorder(BorderFactory.createEmptyBorder());
						//j.setLocation(e.getX(), e.getY());
						j.setBounds(e.getX()-(int)DEFAULT_ICON_SIZE.getX()/2, e.getY()-(int)DEFAULT_ICON_SIZE.getY()/2, (int)DEFAULT_ICON_SIZE.getX(), (int)DEFAULT_ICON_SIZE.getY());
						if(selectedType.getType()!=LevelEditorItem.TYPE_TEXT){
							j.setNewIcon(new PointXY((int)DEFAULT_ICON_SIZE.getX(),(int)DEFAULT_ICON_SIZE.getX()),selectedType.getOriginalTexture());
						}
						j.setIcon(null);
						j.setName(((LevelEditorItem)selectedType).getName());
						levelEditingP.add(j);//Left click in the level editing square-> add item
						levelEditingP.repaint();
					}
					
				}else if(e.getComponent().getClass().equals(LevelEditorItem.class)&&e.getComponent().getParent().equals(levelEditingP)){//Click directly on a LevelEditorItem in the levelEditingPanel
					if(!e.getComponent().equals(selectedItem)){//SELECTING ITEM
						deselectItem();
					}
					deselectType();
					selectedItem = (LevelEditorItem)e.getComponent();
					selectedItem.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
					updateItemStats();
					EndlessWorlds.getGame().getDisplay().getFrame().requestFocus();//We request the focus so people can use the key controls without having to do crazy things like pressing TAB 1000times
				}//LEVEL EDITING PANEL SECTION END---------------------------------------------------------------------------------------
			}else if(e.getButton()==MouseEvent.BUTTON3){//Right click
				//Right click on levelEditorPanel->Open a menu to ask what to do(remove block , etc..)
			}
		}
		public void mouseMoved(MouseEvent e){
			if(selectedType!=null){
				System.out.println("MOUSE DRAGGED!"+e.getX()+":"+e.getY());
				//Draw ghost image
			}
		}
	}
}
