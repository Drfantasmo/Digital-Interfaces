package us.fantasmo.digitalinterfaces.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalNode;

public class DIGuiHandler implements IGuiHandler {

	public static enum GUI_ENUM {
		NODE
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// if (ID == GUI_ENUM.NODE.ordinal())
		// return new GuiNode((TileEntityDigitalNode) world.getTileEntity(new
		// BlockPos(x,y,z)));
		return false;
	}

	@Override
	public Object getServerGuiElement(int arg0, EntityPlayer arg1, World arg2, int arg3, int arg4, int arg5) {
		return null;
	}

}
