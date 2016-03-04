package us.fantasmo.digitalinterfaces.tileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import us.fantasmo.digitalinterfaces.blocks.BlockDigitalNode;

public class TileEntityDigitalNode extends TileEntity {

	public static int MAX_TAG_LENGTH = 32;

	private ArrayList<String> tags;
	public int random;
	
	public TileEntityDigitalNode() {
		tags = new ArrayList<String>();
		random=new Random().nextInt();
	}

	public void addTag(String s){
		tags.add(s);
	}
	
	public void setTags(String[] ts) {
		if (tags == null)
			tags = new ArrayList<String>();
		tags.clear();
		for (String s : ts) {
			if (s.length() > MAX_TAG_LENGTH)
				s = s.substring(0, MAX_TAG_LENGTH);
			tags.add(s);
		}
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public int getTagCount() {
		return tags.size();
	}

	public String[] getTagArray() {
		String[] ts = new String[tags.size()];
		Collections.sort(tags);
		for (int i = 0; i < tags.size(); i++)
			ts[i] = tags.get(i);
		return ts;
	}

	public EnumFacing getFacing() {
		IBlockState state = this.getWorld().getBlockState(this.pos);
		return state.getValue(BlockDigitalNode.FACING);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagCompound getTags = (NBTTagCompound) compound.getTag("tags");
		tags.clear();
		for (String s : getTags.getKeySet()) {
			tags.add(s);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagCompound setTags = new NBTTagCompound();
		for (String s : tags)
			setTags.setString(s, s);
		compound.setTag("tags", setTags);
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock() != newState.getBlock());
	}

}
