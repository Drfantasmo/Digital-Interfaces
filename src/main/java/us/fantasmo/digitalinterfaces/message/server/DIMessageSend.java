package us.fantasmo.digitalinterfaces.message.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import us.fantasmo.digitalinterfaces.gui.GuiNode;

public class DIMessageSend implements IMessage {

	public BlockPos nodePosition;
	public String[] tags;

	public DIMessageSend() {

	}

	public DIMessageSend(BlockPos pos, String[] tags) {
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

	public static class Handler implements IMessageHandler<DIMessageSend, IMessage> {

		@Override
		public IMessage onMessage(final DIMessageSend arg0, final MessageContext arg1) {
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayerSP playerIn = Minecraft.getMinecraft().thePlayer;
					BlockPos pos = arg0.nodePosition;
					String[] tags = arg0.tags;
					Minecraft.getMinecraft().displayGuiScreen(new GuiNode(playerIn.worldObj.getTileEntity(pos), tags));
				}
			});
			return null;
		}

	}
}
