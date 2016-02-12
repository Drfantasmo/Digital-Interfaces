package us.fantasmo.digitalinterfaces.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import us.fantasmo.digitalinterfaces.DigitalInterfacesMod;
import us.fantasmo.digitalinterfaces.Reference;
import us.fantasmo.digitalinterfaces.items.ItemScrewdriver;

public class DIItems {

	public static Item screwdriver_item;

	public static void preInit() {
		screwdriver_item = new ItemScrewdriver().setUnlocalizedName("screwdriver_item")
				.setCreativeTab(DigitalInterfacesMod.diTab);
	}

	public static void register() {
		GameRegistry.registerItem(screwdriver_item, screwdriver_item.getUnlocalizedName().substring(5));
	}

	public static void registerRenders() {
		registerRender(screwdriver_item);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(
				Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

}
