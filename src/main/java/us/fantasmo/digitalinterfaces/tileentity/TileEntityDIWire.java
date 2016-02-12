package us.fantasmo.digitalinterfaces.tileentity;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityDIWire extends TileEntity {

	public static final String[] SIDES = new String[] { "Down", "Up", "North", "South", "West", "East" };

	private boolean[] enabledOnSide;

	public TileEntityDIWire() {
		enabledOnSide = new boolean[] { true, true, true, true, true, true };
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		enabledOnSide = new boolean[6];
		for (int i = 0; i < 6; i++) {
			enabledOnSide[i] = compound.getBoolean(i + "");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		for (int i = 0; i < 6; i++) {
			compound.setBoolean(i + "", enabledOnSide[i]);
		}
	}

	public boolean isEnabledOnSide(EnumFacing face) {
		return enabledOnSide[face.ordinal()];
	}

	public void setEnabledOnSide(EnumFacing face, boolean b) {
		enabledOnSide[face.ordinal()] = b;
	}

}
