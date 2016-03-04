package us.fantasmo.digitalinterfaces.blocks;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Method;
import us.fantasmo.digitalinterfaces.Reference;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityPeripheral;

public class BlockPeripheralFrame extends BlockFrame implements IPeripheralProvider {

	public static BlockPeripheralFrame instance;
	
	public BlockPeripheralFrame(Material materialIn) {
		super(materialIn);
		instance=this;
	}

	@Override
	@Method(modid = Reference.MODID_COMPUTERCRAFT)
	public IPeripheral getPeripheral(World world, BlockPos pos, EnumFacing side) {
		TileEntity t = world.getTileEntity(pos);

		if (t != null && (t instanceof TileEntityPeripheral)) {
			return (IPeripheral) t;
		}

		return null;
	}

}
