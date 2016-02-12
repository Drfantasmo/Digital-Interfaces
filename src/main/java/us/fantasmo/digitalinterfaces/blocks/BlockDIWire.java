package us.fantasmo.digitalinterfaces.blocks;

import java.util.List;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.fantasmo.digitalinterfaces.items.ItemScrewdriver;
import us.fantasmo.digitalinterfaces.network.ICableConnector;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDIWire;

public class BlockDIWire extends BlockFrame implements ICableConnector, ITileEntityProvider {

	private final float pipeRadius;

	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");

	public BlockDIWire(Material materialIn) {
		super(materialIn);
		this.pipeRadius = (float) 0.1275;
		this.setDefaultState(this.blockState.getBaseState().withProperty(WEST, false).withProperty(DOWN, false)
				.withProperty(SOUTH, false).withProperty(EAST, false).withProperty(UP, false)
				.withProperty(NORTH, false));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			ItemStack stack = playerIn.getCurrentEquippedItem();
			if (stack != null) {
				if (stack.getItem() instanceof ItemScrewdriver) {
					TileEntityDIWire t = (TileEntityDIWire) worldIn.getTileEntity(pos);
					t.setEnabledOnSide(side, !t.isEnabledOnSide(side));
					playerIn.addChatMessage(new ChatComponentText("Digital Wire: "
							+ TileEntityDIWire.SIDES[side.ordinal()] + " : " + t.isEnabledOnSide(side)));
				}
			}
		}
		return false;
	}

	protected boolean canConnectTo(IBlockAccess w, BlockPos thisBlock, IBlockState bs, EnumFacing face,
			BlockPos otherBlock) {
		IBlockState other = w.getBlockState(otherBlock);
		if (other.getBlock() instanceof ICableConnector) {
			return true;
		} else if (w.getTileEntity(otherBlock) != null) {
			try {
				TileEntity o = w.getTileEntity(otherBlock);

				TileEntityDIWire t = (TileEntityDIWire) w.getTileEntity(thisBlock);
				if (o instanceof IInventory && t.isEnabledOnSide(face)) {
					return true;
				}
			} catch (Exception e) {

			}
			return false;
		} else {
			return false;
		}
	}

	public IBlockState getActualState(final IBlockState bs, final IBlockAccess world, final BlockPos coord) {
		return bs.withProperty(WEST, this.canConnectTo(world, coord, bs, EnumFacing.WEST, coord.west()))
				.withProperty(DOWN, this.canConnectTo(world, coord, bs, EnumFacing.DOWN, coord.down()))
				.withProperty(SOUTH, this.canConnectTo(world, coord, bs, EnumFacing.SOUTH, coord.south()))
				.withProperty(EAST, this.canConnectTo(world, coord, bs, EnumFacing.EAST, coord.east()))
				.withProperty(UP, this.canConnectTo(world, coord, bs, EnumFacing.UP, coord.up()))
				.withProperty(NORTH, this.canConnectTo(world, coord, bs, EnumFacing.NORTH, coord.north()));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { WEST, DOWN, SOUTH, EAST, UP, NORTH });
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess world, final BlockPos coord) {
		final IBlockState bs = world.getBlockState(coord);
		IBlockState oldBS = bs;
		final boolean connectNorth = this.canConnectTo(world, coord, oldBS, EnumFacing.NORTH, coord.north());
		final boolean connectSouth = this.canConnectTo(world, coord, oldBS, EnumFacing.SOUTH, coord.south());
		final boolean connectWest = this.canConnectTo(world, coord, oldBS, EnumFacing.WEST, coord.west());
		final boolean connectEast = this.canConnectTo(world, coord, oldBS, EnumFacing.EAST, coord.east());
		final boolean connectUp = this.canConnectTo(world, coord, oldBS, EnumFacing.UP, coord.up());
		final boolean connectDown = this.canConnectTo(world, coord, oldBS, EnumFacing.DOWN, coord.down());

		float radius = pipeRadius;
		float rminus = 0.5f - radius;
		float rplus = 0.5f + radius;

		float x1 = rminus;
		float x2 = rplus;
		float y1 = rminus;
		float y2 = rplus;
		float z1 = rminus;
		float z2 = rplus;
		if (connectNorth) {
			z1 = 0.0f;
		}
		if (connectSouth) {
			z2 = 1.0f;
		}
		if (connectWest) {
			x1 = 0.0f;
		}
		if (connectEast) {
			x2 = 1.0f;
		}
		if (connectDown) {
			y1 = 0.0f;
		}
		if (connectUp) {
			y2 = 1.0f;
		}
		this.setBlockBounds(x1, y1, z1, x2, y2, z2);
	}

	@Override
	public void addCollisionBoxesToList(final World world, final BlockPos coord, final IBlockState bs,
			final AxisAlignedBB box, final List collisionBoxList, final Entity entity) {
		IBlockState oldBS = bs;
		final boolean connectNorth = this.canConnectTo(world, coord, oldBS, EnumFacing.NORTH, coord.north());
		final boolean connectSouth = this.canConnectTo(world, coord, oldBS, EnumFacing.SOUTH, coord.south());
		final boolean connectWest = this.canConnectTo(world, coord, oldBS, EnumFacing.WEST, coord.west());
		final boolean connectEast = this.canConnectTo(world, coord, oldBS, EnumFacing.EAST, coord.east());
		final boolean connectUp = this.canConnectTo(world, coord, oldBS, EnumFacing.UP, coord.up());
		final boolean connectDown = this.canConnectTo(world, coord, oldBS, EnumFacing.DOWN, coord.down());

		float radius = pipeRadius;
		float rminus = 0.5f - radius;
		float rplus = 0.5f + radius;

		this.setBlockBounds(rminus, rminus, rminus, rplus, rplus, rplus);
		super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);

		if (connectUp) {
			this.setBlockBounds(rminus, rminus, rminus, rplus, 1f, rplus);
			super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
		}
		if (connectDown) {
			this.setBlockBounds(rminus, 0f, rminus, rplus, rplus, rplus);
			super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
		}
		if (connectEast) {
			this.setBlockBounds(rminus, rminus, rminus, 1f, rplus, rplus);
			super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
		}
		if (connectWest) {
			this.setBlockBounds(0f, rminus, rminus, rplus, rplus, rplus);
			super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
		}
		if (connectSouth) {
			this.setBlockBounds(rminus, rminus, rminus, rplus, rplus, 1f);
			super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
		}
		if (connectNorth) {
			this.setBlockBounds(rminus, rminus, 0f, rplus, rplus, rplus);
			super.addCollisionBoxesToList(world, coord, bs, box, collisionBoxList, entity);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isPassable(final IBlockAccess world, final BlockPos coord) {
		return false;
	}

	@Override
	public int getMetaFromState(final IBlockState bs) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(final IBlockAccess world, final BlockPos coord, final EnumFacing face) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDIWire();
	}

}
