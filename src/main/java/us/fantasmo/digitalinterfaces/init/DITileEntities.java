package us.fantasmo.digitalinterfaces.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDIWire;

public class DITileEntities {

	public static void register() {
		GameRegistry.registerTileEntity(TileEntityDIWire.class, "digitalinterfacesDIWire");
	}

}
