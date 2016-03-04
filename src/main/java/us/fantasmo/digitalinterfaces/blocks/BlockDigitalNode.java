package us.fantasmo.digitalinterfaces.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.fantasmo.digitalinterfaces.DigitalInterfacesMod;
import us.fantasmo.digitalinterfaces.items.ItemScrewdriver;
import us.fantasmo.digitalinterfaces.message.client.DIMessageRequest;
import us.fantasmo.digitalinterfaces.network.ICableConnector;
import us.fantasmo.digitalinterfaces.network.ICableNode;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalNode;

public class BlockDigitalNode extends BlockFrame implements ICableConnector, ICableNode, ITileEntityProvider {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockDigitalNode(Material materialIn) {
		super(materialIn);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			System.out.println(((TileEntityDigitalNode) worldIn.getTileEntity(pos)).getTagCount());
			ItemStack stack = playerIn.getCurrentEquippedItem();
			if (stack != null && stack.getItem() instanceof ItemScrewdriver) {
				worldIn.setBlockState(pos, state.withProperty(FACING, side.getOpposite()));
			}
		} else {
			ItemStack stack = playerIn.getCurrentEquippedItem();
			if (stack == null) {
				DigitalInterfacesMod.simpleNetwork.sendToServer(new DIMessageRequest(pos));
			}
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(worldIn, pos, placer)), 2);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, getFacingFromEntity(worldIn, pos, placer));
	}

	public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {

		EnumFacing ret = entityIn.getHorizontalFacing().getOpposite();

		if (MathHelper.abs((float) entityIn.posX - (float) clickedBlock.getX()) < 2.0F
				&& MathHelper.abs((float) entityIn.posZ - (float) clickedBlock.getZ()) < 2.0F) {
			double d0 = entityIn.posY + (double) entityIn.getEyeHeight();

			if (d0 - (double) clickedBlock.getY() > 2.0D) {
				ret = EnumFacing.UP;
			}

			if ((double) clickedBlock.getY() - d0 > 0.0D) {
				ret = EnumFacing.DOWN;
			}
		}
		if (!entityIn.isSneaking())
			ret = ret.getOpposite();
		return ret;
	}

	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING });
	}

	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | ((EnumFacing) state.getValue(FACING)).getIndex();

		return i;
	}

	static final class SwitchEnumFacing {
		static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];
		private static final String __OBFID = "CL_00002037";

		static {
			try {
				FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 1;
			} catch (NoSuchFieldError var6) {
				;
			}

			try {
				FACING_LOOKUP[EnumFacing.UP.ordinal()] = 2;
			} catch (NoSuchFieldError var5) {
				;
			}

			try {
				FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
			} catch (NoSuchFieldError var4) {
				;
			}

			try {
				FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
			} catch (NoSuchFieldError var3) {
				;
			}

			try {
				FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 5;
			} catch (NoSuchFieldError var2) {
				;
			}

			try {
				FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 6;
			} catch (NoSuchFieldError var1) {
				;
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityDigitalNode();
	}

}
