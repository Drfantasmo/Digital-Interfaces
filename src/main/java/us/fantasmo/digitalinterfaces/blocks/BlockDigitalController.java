package us.fantasmo.digitalinterfaces.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.fantasmo.digitalinterfaces.network.ICableConnector;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalController;

public class BlockDigitalController extends BlockPeripheralFrame implements ICableConnector, ITileEntityProvider{

	public BlockDigitalController(Material materialIn) {
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityDigitalController();
	}

}
