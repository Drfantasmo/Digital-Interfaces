package us.fantasmo.digitalinterfaces;

import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import us.fantasmo.digitalinterfaces.blocks.BlockPeripheralFrame;
import us.fantasmo.digitalinterfaces.gui.DIGuiHandler;
import us.fantasmo.digitalinterfaces.init.DIBlocks;
import us.fantasmo.digitalinterfaces.init.DIItems;
import us.fantasmo.digitalinterfaces.init.DITileEntities;
import us.fantasmo.digitalinterfaces.message.client.DIMessageRequest;
import us.fantasmo.digitalinterfaces.message.client.DIMessageUpdate;
import us.fantasmo.digitalinterfaces.message.server.DIMessageSend;
import us.fantasmo.digitalinterfaces.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class DigitalInterfacesMod {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper simpleNetwork;

	public static DigitalInterfacesMod instance;

	public static final DITab diTab = new DITab("tabDigitalInterfaces");

	public DigitalInterfacesMod() {
		instance = this;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		DIItems.preInit();
		DIBlocks.preInit();
		DIItems.register();
		DIBlocks.register();
		DITileEntities.register();
		registerPackets();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenders();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new DIGuiHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		initializeComputerCraft();
	}

	private void registerPackets() {
		simpleNetwork = NetworkRegistry.INSTANCE.newSimpleChannel("digitalinterfaces");
		simpleNetwork.registerMessage(new DIMessageRequest.Handler(), DIMessageRequest.class, 0, Side.SERVER);
		simpleNetwork.registerMessage(new DIMessageUpdate.Handler(), DIMessageUpdate.class, 1, Side.SERVER);
		simpleNetwork.registerMessage(new DIMessageSend.Handler(), DIMessageSend.class, 2, Side.CLIENT);
	}

	private void initializeComputerCraft() {
		if (!Loader.isModLoaded(Reference.MODID_COMPUTERCRAFT)) {
			return;
		}
		ComputerCraftAPI.registerPeripheralProvider(BlockPeripheralFrame.instance);
	}

}
