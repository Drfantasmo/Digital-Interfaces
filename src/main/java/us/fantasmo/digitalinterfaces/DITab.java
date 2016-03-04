package us.fantasmo.digitalinterfaces;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import us.fantasmo.digitalinterfaces.init.DIItems;

public class DITab extends CreativeTabs {

	public DITab(String label) {
		super(label);
		this.setBackgroundImageName("digital.png");
	}

	@Override
	public Item getTabIconItem() {
		return DIItems.screwdriver_item;
	}

}
