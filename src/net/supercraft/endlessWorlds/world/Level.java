package net.supercraft.endlessWorlds.world;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.blocks.Block;
import net.supercraft.endlessWorlds.blocks.BlockGrass;
import net.supercraft.endlessWorlds.blocks.Blocks;
import net.supercraft.endlessWorlds.entity.Entity;
import net.supercraft.endlessWorlds.items.Item;
import net.supercraft.jojoleproUtils.graphicals.PositionalString;
import net.supercraft.jojoleproUtils.io.XMLReader;
import net.supercraft.jojoleproUtils.math.PointXY;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Level {
	private EndlessWorlds ed;
	private File file;
	private ArrayList<Block> block = new ArrayList<Block>();
	private ArrayList<Item> item = new ArrayList<Item>();
	private ArrayList<Entity> entity = new ArrayList<Entity>();
	private PointXY spawnPoint = new PointXY(20,20);
	private String name = "";
	private String creator = "";
	private ArrayList<PositionalString> strings = new ArrayList<PositionalString>();
	public Level(EndlessWorlds ed,ArrayList<Block> blocks, ArrayList<Item> items, ArrayList<Entity> mobs, PointXY spawnPoint){
		this.block = blocks;
		this.item = items;
		this.entity = mobs;
		this.spawnPoint = spawnPoint;
	}
	
	public Level(EndlessWorlds ed){
		this.ed = ed;
	}
	
	public static Level fromXML(EndlessWorlds ed, String filename){
		File file = new File(filename);
		ArrayList<Block> block = new ArrayList<Block>();
		ArrayList<Item> item = new ArrayList<Item>();
		ArrayList<Entity> mob = new ArrayList<Entity>();
		PointXY spawnPoint = new PointXY(0,0);
		ArrayList<PositionalString> tempString = new ArrayList<PositionalString>();
		String name = "";
		String creator = "";
		Document doc = XMLReader.getDocument(filename);
		doc.normalize();
		
		NodeList properties = doc.getElementsByTagName("Properties");
		for(int i=0;i<properties.getLength();i++){
			Node node = properties.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element elem = (Element) node;
				String data = "";
				//SpawnPointX
				if(elem.getElementsByTagName("SpawnPointX").getLength()>0){
					data=elem.getElementsByTagName("SpawnPointX").item(0).getTextContent();
					spawnPoint.setX((int)(float)Float.valueOf(data));
				}
				//SpawnPointY
				if(elem.getElementsByTagName("SpawnPointY").getLength()>0){
					data=elem.getElementsByTagName("SpawnPointY").item(0).getTextContent();
					spawnPoint.setY((int)(float)Float.valueOf(data));
				}
				//Name
				if(elem.getElementsByTagName("Name").getLength()>0){
					data=elem.getElementsByTagName("Name").item(0).getTextContent();
					name=data;
				}
				//Creator
				if(elem.getElementsByTagName("Creator").getLength()>0){
					data=elem.getElementsByTagName("Creator").item(0).getTextContent();
					creator = data;
				}
			}
		}
		//BLOCKS
		//NodeList nodeList = doc.getElementsByTagName("Blocks");
		NodeList blockList = doc.getElementsByTagName("Block");
		for(int i=0;i<blockList.getLength();i++){
			Node node = blockList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element elem = (Element) node;
				Block tempBlock = new BlockGrass(ed);
				
				String data = "";
				//TYPE Block
				if(elem.getElementsByTagName("Type").getLength()>0){
					data=elem.getElementsByTagName("Type").item(0).getTextContent();
					tempBlock = Blocks.createInstanceForName(data);
				}
				//Rotation float
				if(elem.getElementsByTagName("Rotation").getLength()>0){
					data=elem.getElementsByTagName("Rotation").item(0).getTextContent();
					tempBlock.setRotation(Float.valueOf(data));
				}
				//Bounce float
				if(elem.getElementsByTagName("Bounce").getLength()>0){
					data=elem.getElementsByTagName("Bounce").item(0).getTextContent();
					tempBlock.setBounce(Float.valueOf(data));
				}
				//Friction float
				if(elem.getElementsByTagName("Friction").getLength()>0){
					data=elem.getElementsByTagName("Friction").item(0).getTextContent();
					tempBlock.setFriction(Float.valueOf(data));
				}
				//Hardness bool
				if(elem.getElementsByTagName("Hardness").getLength()>0){
					data=elem.getElementsByTagName("Hardness").item(0).getTextContent();
					tempBlock.setHardness(Boolean.valueOf(data));
				}
				//Opaque bool
				if(elem.getElementsByTagName("Opaque").getLength()>0){
					data=elem.getElementsByTagName("Opaque").item(0).getTextContent();
					tempBlock.setIsOpaque(Boolean.valueOf(data));
				}
				//PosX int
				if(elem.getElementsByTagName("PositionX").getLength()>0){
					data=elem.getElementsByTagName("PositionX").item(0).getTextContent();
					tempBlock.setPosX((int)(float)Float.valueOf(data));
				}
				//PosY int
				if(elem.getElementsByTagName("PositionY").getLength()>0){
					data=elem.getElementsByTagName("PositionY").item(0).getTextContent();
					tempBlock.setPosY((int)(float)Float.valueOf(data));
				}
				//SizeX
				if(elem.getElementsByTagName("SizeX").getLength()>0){
					data=elem.getElementsByTagName("SizeX").item(0).getTextContent();
					tempBlock.setSizeX((int)(float)Float.valueOf(data));
				}
				//SizeY
				if(elem.getElementsByTagName("SizeY").getLength()>0){
					data=elem.getElementsByTagName("SizeY").item(0).getTextContent();
					tempBlock.setSizeY((int)(float)Float.valueOf(data));
				}
				tempBlock.generateRectangle();
				block.add(tempBlock);
			}
		}
		//END BLOCKS
		//ITEMS
		//END ITEMS
		//MOBS
		//END ITEMS
		//Strings
		NodeList stringList = doc.getElementsByTagName("String");
		for(int i=0;i<stringList.getLength();i++){
			Node node = stringList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element elem = (Element) node;
				String data = "";
				String text = "";
				PointXY tempPos = new PointXY();
				//String
				if(elem.getElementsByTagName("Text").getLength()>0){
					data=elem.getElementsByTagName("Text").item(0).getTextContent();
					text=data;
				}
				//PositionX
				if(elem.getElementsByTagName("PositionX").getLength()>0){
					data=elem.getElementsByTagName("PositionX").item(0).getTextContent();
					tempPos.setX(Double.valueOf(data));
				}
				//PositionY
				if(elem.getElementsByTagName("PositionY").getLength()>0){
					data=elem.getElementsByTagName("PositionY").item(0).getTextContent();
					tempPos.setY(Double.valueOf(data));
				}
				
				if(text!=""&&tempPos.getX()>=0&&tempPos.getY()>=0){
					tempString.add(new PositionalString(tempPos,text));
				}
			}
		}
		//END Strings
		Level l = new Level(EndlessWorlds.getGame(),block,item,mob,spawnPoint);
		l.setName(name);
		l.setCreator(creator);
		l.setStrings(tempString);
		l.setFile(file);
		return l;
	}
	public static void exportAsXML(Level toExport, String filePath){//http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Level");
			doc.appendChild(rootElement);
			
			//Properties
			Element properties = doc.createElement("Properties");
			rootElement.appendChild(properties);
			
			Element spawnpointX = doc.createElement("SpawnPointX");
			spawnpointX.appendChild(doc.createTextNode(String.valueOf(toExport.getSpawnpoint().getX())));
			properties.appendChild(spawnpointX);
			
			Element spawnpointY = doc.createElement("SpawnPointY");
			spawnpointY.appendChild(doc.createTextNode(String.valueOf(toExport.getSpawnpoint().getY())));
			properties.appendChild(spawnpointY);
			
			Element ename = doc.createElement("Name");
			ename.appendChild(doc.createTextNode(toExport.getName()));
			properties.appendChild(ename);
			
			Element ecreator = doc.createElement("Creator");
			ecreator.appendChild(doc.createTextNode(toExport.getCreator()));
			properties.appendChild(ecreator);
			//Properties END
			
			//Blocks
			Element blocks = doc.createElement("Blocks");
			rootElement.appendChild(blocks);
			
			for(int i=0;i<toExport.getBlockList().size();i++){
				Element block = doc.createElement("Block");
				blocks.appendChild(block);
				
				Element type = doc.createElement("Type");
				type.appendChild(doc.createTextNode(String.valueOf(toExport.getBlockList().get(i).getClass().getSimpleName().substring(5))));
				block.appendChild(type);
				
				Element rotation = doc.createElement("Rotation");
				rotation.appendChild(doc.createTextNode(String.valueOf(toExport.getBlockList().get(i).getRotation())));
				block.appendChild(rotation);
				
				Element posX = doc.createElement("PositionX");
				posX.appendChild(doc.createTextNode(String.valueOf(toExport.getBlockList().get(i).getPosX())));
				block.appendChild(posX);
				
				System.out.println(toExport.getBlockList().get(i).getRotation());//Wut?
				
				Element posY = doc.createElement("PositionY");
				posY.appendChild(doc.createTextNode(String.valueOf(toExport.getBlockList().get(i).getPosY())));
				block.appendChild(posY);
				
				Element sizeX = doc.createElement("SizeX");
				sizeX.appendChild(doc.createTextNode(String.valueOf(toExport.getBlockList().get(i).getSizeX())));
				block.appendChild(sizeX);
				
				Element sizeY = doc.createElement("SizeY");
				sizeY.appendChild(doc.createTextNode(String.valueOf(toExport.getBlockList().get(i).getSizeY())));
				block.appendChild(sizeY);
			}
			//Blocks END
			//String
			for(int i=0;i<toExport.getStrings().size();i++){
				Element string = doc.createElement("String");
				rootElement.appendChild(string);
				
				Element text = doc.createElement("Text");
				text.appendChild(doc.createTextNode(String.valueOf(toExport.getStrings().get(i).getText())));
				string.appendChild(text);
				
				Element posX = doc.createElement("PositionX");
				posX.appendChild(doc.createTextNode(String.valueOf(toExport.getStrings().get(i).getPosition().getX())));
				string.appendChild(posX);
				
				Element posY = doc.createElement("PositionY");
				posY.appendChild(doc.createTextNode(String.valueOf(toExport.getStrings().get(i).getPosition().getY())));
				string.appendChild(posY);
			}
			//END String
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	public PointXY getSpawnpoint(){
		return this.spawnPoint;
	}
	public ArrayList<Block> getBlockList() {
		return block;
	}
	public ArrayList<Entity> getEntityList() {
		return entity;
	}
	public void setSpawnpoint(PointXY spawnpoint){
		this.spawnPoint=spawnpoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public ArrayList<PositionalString> getStrings() {
		return strings;
	}

	public void setStrings(ArrayList<PositionalString> strings) {
		this.strings = strings;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
