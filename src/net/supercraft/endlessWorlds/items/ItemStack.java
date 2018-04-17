package net.supercraft.endlessWorlds.items;

import net.supercraft.endlessWorlds.EndlessWorlds;

public class ItemStack {
	private EndlessWorlds ed;
	protected Item itemType;
	protected int itemCount;
	
	public ItemStack(EndlessWorlds ed, Item itemType, int itemCount){
		this.ed = ed;
		this.itemType = itemType;
		this.itemCount = itemCount;
	}

	public Item getItemType() {
		return itemType;
	}

	public void setItemType(Item itemType) {
		this.itemType = itemType;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
}
