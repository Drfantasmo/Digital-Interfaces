package us.fantasmo.digitalinterfaces.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import us.fantasmo.digitalinterfaces.Reference;
import us.fantasmo.digitalinterfaces.blocks.BlockDIWire;
import us.fantasmo.digitalinterfaces.blocks.BlockDigitalController;
import us.fantasmo.digitalinterfaces.blocks.BlockDigitalNode;

public class DIBlocks {

	public static Block di_wire;
	public static Block di_controller;
	public static Block di_node;

	public static void preInit() {
		di_wire = new BlockDIWire(Material.iron).setUnlocalizedName("di_wire");
		di_controller = new BlockDigitalController(Material.iron).setUnlocalizedName("di_controller");
		di_node = new BlockDigitalNode(Material.iron).setUnlocalizedName("di_node");
	}

	public static void register() {
		GameRegistry.registerBlock(di_wire, di_wire.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(di_controller, di_controller.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(di_node, di_node.getUnlocalizedName().substring(5));
	}

	public static void registerRenders() {
		registerRender(di_wire);
		registerRender(di_controller);
		registerRender(di_node);
	}

	public static void registerRender(Block block) {
		Item item = Item.getItemFromBlock(block);
		String name = block.getUnlocalizedName().substring(5);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
				net.minecraft.item.Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory"));
	}

}
