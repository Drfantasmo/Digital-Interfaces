package us.fantasmo.digitalinterfaces.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import us.fantasmo.digitalinterfaces.DigitalInterfacesMod;

public class BlockFrame extends Block {

	public static BlockFrame instance;

	public BlockFrame(Material materialIn) {
		super(materialIn);
		instance = this;
		setCreativeTab(DigitalInterfacesMod.diTab);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(Block.soundTypeMetal);
	}

}
