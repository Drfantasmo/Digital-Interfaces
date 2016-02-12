package us.fantasmo.digitalinterfaces.proxy;

import us.fantasmo.digitalinterfaces.init.DIItems;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerRenders(){
		DIItems.registerRenders();
	}
	
	
}
