package net.supercraft.endlessWorlds.items;

import net.supercraft.endlessWorlds.EndlessWorlds;

public class Inventory {
	private EndlessWorlds ed;
	private ItemStack slot[] = new ItemStack[15];
	public Inventory(EndlessWorlds ed){
		this.ed = ed;
		
		for(int i=0;i<slot.length;i++){
			slot[i] = new ItemStack(ed,new ItemAir(ed),0);
		}
	}
	public void setSlotCountain(int slotNumber, ItemStack itemStack){
		if(slotNumber>slot.length||slotNumber<1){
			System.err.println("Writing ItemStack in an null Slot(Slot:"+slotNumber+"  Maximum Slot:"+slot.length);
		}else{
			slot[slotNumber-1] = itemStack;
		}
	}
	public ItemStack getSlotCountain(int slotNumber){
		try{
			return slot[slotNumber-1];
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
			return new ItemStack(ed, new ItemAir(ed),0);
		}
	}
}
