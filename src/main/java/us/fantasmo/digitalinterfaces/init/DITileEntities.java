package us.fantasmo.digitalinterfaces.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDIWire;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalController;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalNode;

public class DITileEntities {

	public static void register() {
		GameRegistry.registerTileEntity(TileEntityDIWire.class, "digitalinterfacesDIWire");
		GameRegistry.registerTileEntity(TileEntityDigitalController.class, "digitalinterfacesDigitalController");
		GameRegistry.registerTileEntity(TileEntityDigitalNode.class, "digitalinterfacesDigitalNode");
	}

}
