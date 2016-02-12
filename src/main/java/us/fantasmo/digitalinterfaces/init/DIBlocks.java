package us.fantasmo.digitalinterfaces.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import us.fantasmo.digitalinterfaces.Reference;
import us.fantasmo.digitalinterfaces.blocks.BlockDIWire;

public class DIBlocks {

	public static Block di_wire;

	public static void preInit() {
		di_wire = new BlockDIWire(Material.iron).setUnlocalizedName("di_wire");
	}

	public static void register() {
		GameRegistry.registerBlock(di_wire, di_wire.getUnlocalizedName().substring(5));
	}

	public static void registerRenders() {
		registerRender(di_wire);
	}

	public static void registerRender(Block block) {
		Item item = Item.getItemFromBlock(block);
		String name = block.getUnlocalizedName().substring(5);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				net.minecraft.item.Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory"));
	}

}
