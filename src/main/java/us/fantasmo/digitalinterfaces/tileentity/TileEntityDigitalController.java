package us.fantasmo.digitalinterfaces.tileentity;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.nbt.NBTTagCompound;
import us.fantasmo.digitalinterfaces.network.DINetwork;

public class TileEntityDigitalController extends TileEntityPeripheral {

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
	}

	@Override
	public String getType() {
		return "di_digital_controller";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "getNetworkSize" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments)
			throws LuaException, InterruptedException {
		if(method==0){
			DINetwork network=DINetwork.getNetworkByController(this);
			int size=network.getNetworkSize();
			return new Object[]{size};
		}
		return null;
	}

	@Override
	public void attach(IComputerAccess computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void detach(IComputerAccess computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(IPeripheral other) {
		return other == this;
	}

}
