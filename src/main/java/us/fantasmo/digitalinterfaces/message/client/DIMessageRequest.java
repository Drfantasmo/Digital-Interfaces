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
import us.fantasmo.digitalinterfaces.DigitalInterfacesMod;
import us.fantasmo.digitalinterfaces.message.server.DIMessageSend;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalNode;

public class DIMessageRequest implements IMessage {

	public BlockPos nodePosition;

	public DIMessageRequest() {

	}

	public DIMessageRequest(BlockPos nodePosition) {
		this.nodePosition = nodePosition;
	}

	@Override
	public void fromBytes(ByteBuf arg0) {
		int x = arg0.readInt();
		int y = arg0.readInt();
		int z = arg0.readInt();
		nodePosition = new BlockPos(x, y, z);
	}

	@Override
	public void toBytes(ByteBuf arg0) {
		arg0.writeInt(nodePosition.getX());
		arg0.writeInt(nodePosition.getY());
		arg0.writeInt(nodePosition.getZ());
	}

	public static class Handler implements IMessageHandler<DIMessageRequest, IMessage> {

		@Override
		public IMessage onMessage(final DIMessageRequest arg0, final MessageContext arg1) {
			IThreadListener mainThread = (WorldServer) arg1.getServerHandler().playerEntity.worldObj;
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayerMP player = arg1.getServerHandler().playerEntity;
					BlockPos p = arg0.nodePosition;
					TileEntity te = player.worldObj.getTileEntity(p);
					if (te != null && te instanceof TileEntityDigitalNode) {
						TileEntityDigitalNode node = (TileEntityDigitalNode) te;
						String[] tags = node.getTagArray();
						DigitalInterfacesMod.simpleNetwork.sendTo(new DIMessageSend(p, tags), player);
					}
				}
			});
			return null;
		}

	}

}
