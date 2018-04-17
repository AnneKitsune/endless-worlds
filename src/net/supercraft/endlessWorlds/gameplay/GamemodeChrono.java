package net.supercraft.endlessWorlds.gameplay;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import net.supercraft.endlessWorlds.EndlessWorlds;
import net.supercraft.endlessWorlds.display.ModuleMenuNewGame;
import net.supercraft.jojoleproUtils.NameValuePair;

public class GamemodeChrono extends Gamemode{
	private static final String recordFilePath = "assets/saves/chrono.xml";
	private long oldLevelTime = -1;
	private long newLevelTime;
	public GamemodeChrono(EndlessWorlds ed) {
		super(ed);
		this.name = "Chrono";
	}
	public void onGameStart() {
		oldLevelTime = getLevelTime();
	}
	public void onLevelChangeRequest() {
		oldLevelTime = getLevelTime();
		newLevelTime = ed.getEngine().getCurrentTime();
		if(oldLevelTime>newLevelTime || oldLevelTime==-1){//OldLevelTime<NewLevelTime so we export the record
			this.exportNewRecord(new NameValuePair(ed.getWorld().getLevel().getFile().getPath(),newLevelTime));
			//NEW RECORD SET, PRINT MENU
		}
		
		EndlessWorlds.getGame().getDisplay().changeMenu(new ModuleMenuNewGame(EndlessWorlds.getGame().getEngine()));
		EndlessWorlds.getGame().pause();
	}
	private long getLevelTime(){
		System.out.println("Importing best level time from level "+ed.getWorld().getLevel().getFile().getPath());
		ArrayList<NameValuePair> allRecords = importAllRecord();
		if(allRecords==null){
			return -1;
		}else{
			for(int i=0;i<allRecords.size();i++){
				if(allRecords.get(i).getName().equals(ed.getWorld().getLevel().getFile().getPath())){
					return((long)allRecords.get(i).getValue());
				}
			}
		}
		return -1;
	}
	private ArrayList<NameValuePair> importAllRecord(){
		try(InputStream file = new FileInputStream(recordFilePath);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);){
			
			@SuppressWarnings("unchecked")
			ArrayList<NameValuePair> data = (ArrayList<NameValuePair>)input.readObject();
			
			return data;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	private void exportNewRecord(NameValuePair nvp){
		ArrayList<NameValuePair> allRecords = importAllRecord();
		if(allRecords==null){
			allRecords = new ArrayList<NameValuePair>();
		}
		boolean dataReplaced = false;
		for(int i=0;i<allRecords.size();i++){
			if(allRecords.get(i).getName().equals(nvp.getName())){
				allRecords.get(i).setValue(nvp.getValue());
				dataReplaced = true;
				break;
			}
		}
		if(!dataReplaced){
			allRecords.add(new NameValuePair(nvp.getName(),nvp.getValue()));
		}
		
		//export
		try(OutputStream file = new FileOutputStream(recordFilePath);
				OutputStream buffer = new BufferedOutputStream(file);
				ObjectOutput output = new ObjectOutputStream (buffer);){
				
				output.writeObject(allRecords);
			}
			catch(IOException e){
				e.printStackTrace();
			}
	}
	public long getOldLevelTime() {
		return oldLevelTime;
	}
}
