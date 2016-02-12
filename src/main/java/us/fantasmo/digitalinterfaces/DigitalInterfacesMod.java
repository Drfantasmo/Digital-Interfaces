package us.fantasmo.digitalinterfaces;

import java.lang.reflect.Method;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import us.fantasmo.digitalinterfaces.init.DIItems;
import us.fantasmo.digitalinterfaces.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class DigitalInterfacesMod {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static DigitalInterfacesMod instance;
	
	public static final DITab diTab = new DITab("tabDigitalInterfaces");

	public DigitalInterfacesMod() {
		instance = this;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		DIItems.preInit();
		DIItems.register();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenders();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		initializeComputerCraft();
	}

	private void initializeComputerCraft() {
		if (!Loader.isModLoaded(Reference.MODID_COMPUTERCRAFT)) {
			return;
		}

		try {
			Class computerCraft = Class.forName("dan200.computercraft.ComputerCraft");
			Method computerCraft_registerPeripheralProvider = computerCraft.getMethod("registerPeripheralProvider",
					new Class[] { Class.forName("dan200.computercraft.api.peripheral.IPeripheralProvider") });
			// computerCraft_registerPeripheralProvider.invoke(null,
			// BlockFrame.instance);
		} catch (Exception e) {

		}
	}

}
