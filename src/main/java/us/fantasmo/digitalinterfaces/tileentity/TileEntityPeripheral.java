package us.fantasmo.digitalinterfaces.tileentity;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import us.fantasmo.digitalinterfaces.Reference;

@InterfaceList(value = {
		@Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = Reference.MODID_COMPUTERCRAFT) })
public abstract class TileEntityPeripheral extends TileEntity implements IPeripheral {

}
