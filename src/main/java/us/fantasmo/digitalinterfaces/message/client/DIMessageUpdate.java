package us.fantasmo.digitalinterfaces.message.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalNode;

public class DIMessageUpdate implements IMessage {

	public BlockPos nodePosition;
	public String[] tags;

	public DIMessageUpdate() {

	}

	public DIMessageUpdate(BlockPos pos, String[] tags) {
		this.nodePosition = pos;
		this.tags = tags;
	}

	@Override
	public void fromBytes(ByteBuf arg0) {
		int x = arg0.readInt();
		int y = arg0.readInt();
		int z = arg0.readInt();
		nodePosition = new BlockPos(x, y, z);
		int size = arg0.readInt();
		tags = new String[size];
		for (int i = 0; i < size; i++) {
			int length = arg0.readInt();
			tags[i] = new String(arg0.readBytes(length).array());
		}
	}

	@Override
	public void toBytes(ByteBuf arg0) {
		arg0.writeInt(nodePosition.getX());
		arg0.writeInt(nodePosition.getY());
		arg0.writeInt(nodePosition.getZ());
		arg0.writeInt(tags.length);
		for (int i = 0; i < tags.length; i++) {
			byte[] bytes = tags[i].getBytes();
			arg0.writeInt(bytes.length);
			arg0.writeBytes(bytes);
		}
	}

	public static class Handler implements IMessageHandler<DIMessageUpdate, IMessage> {

		@Override
		public IMessage onMessage(final DIMessageUpdate arg0, final MessageContext arg1) {
			IThreadListener mainThread = (WorldServer) arg1.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayerMP player = arg1.getServerHandler().playerEntity;
					BlockPos p = arg0.nodePosition;
					TileEntity te = player.worldObj.getTileEntity(p);
					if (te != null && te instanceof TileEntityDigitalNode) {
						TileEntityDigitalNode node = (TileEntityDigitalNode) te;
						node.setTags(arg0.tags);
						player.closeScreen();
					}
				}
			});
			return null;
		}

	}
}
