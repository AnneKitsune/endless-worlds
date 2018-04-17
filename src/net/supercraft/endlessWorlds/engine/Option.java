package net.supercraft.endlessWorlds.engine;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.jojoleproUtils.io.XMLReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public enum Option {
	MASTERVOLUME("MasterVolume","100"),
	MUSICVOLUME("MusicVolume","100"),
	EFFECTVOLUME("EffectVolume","100"),
	MONSTERVOLUME("MonsterVolume","100");
	private String name="DEFAULT_OPTION_NAME";
	private String value="DEFAULT_VALUE";
	private Option(String name, String value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static void refreshConfigurableObjects(){
		for(int i=0;i<EndlessWorlds.getGame().getModuleAudio().getAudioList().size();i++){
			EndlessWorlds.getGame().getModuleAudio().getAudioList().get(i).setVolume(Integer.valueOf(MASTERVOLUME.getValue()));
		}
	}
	public static void importOptionFromFile(String filepath){
		System.out.println("Importing options from file "+filepath);
		try{
		Document doc = XMLReader.getDocument(filepath);
		doc.normalize();
		
		NodeList optionList = doc.getElementsByTagName("Option");
		
		for(int i=0;i<optionList.getLength();i++){
			Node node = optionList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element elem = (Element) node;
				
				String data = "";
				for(int j=0;j<Option.values().length;j++){
					if(elem.getElementsByTagName(Option.values()[j].getName()).getLength()>0){
						data=elem.getElementsByTagName(Option.values()[j].getName()).item(0).getTextContent();
						Option.values()[j].setValue(data);
					}
				}
			}
		}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	public static void exportOptionToFile(String filepath){
		System.out.println("Exporting options to file "+filepath);
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Option");
			doc.appendChild(rootElement);
			
			for(int i=0;i<Option.values().length;i++){
				Element option = doc.createElement(Option.values()[i].getName());
				option.appendChild(doc.createTextNode(Option.values()[i].getValue()));
				rootElement.appendChild(option);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
}
