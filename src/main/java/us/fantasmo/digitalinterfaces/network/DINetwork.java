package us.fantasmo.digitalinterfaces.network;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import us.fantasmo.digitalinterfaces.tileentity.TileEntityDigitalController;

public class DINetwork {

	private ArrayList<TileEntity> nodes;

	public DINetwork() {
		nodes = new ArrayList<TileEntity>();
	}

	public void addNode(TileEntity e) {
		if (!nodes.contains(e))
			nodes.add(e);
	}

	public int getNetworkSize() {
		return nodes.size();
	}

	public ArrayList<TileEntity> getNodes() {
		return nodes;
	}

	public static DINetwork getNetworkByController(TileEntityDigitalController controller) {
		DINetwork network = new DINetwork();
		Queue<BlockPos> pos = new ArrayDeque<BlockPos>();
		ArrayList<BlockPos> checked = new ArrayList<BlockPos>();
		pos.add(controller.getPos());

		int[] adjustA = { 0, 0, 1, 0, 0, -1 };
		int[] adjustB = { 0, 1, 0, 0, -1, 0 };
		int[] adjustC = { 1, 0, 0, -1, 0, 0 };

		while (pos.size() > 0) {
			BlockPos check = pos.poll();
			checked.add(check);
			TileEntity cc = controller.getWorld().getTileEntity(check);
			if (cc.getBlockType() instanceof ICableNode) {
				network.addNode(cc);
				System.out.println(check.getX() + " " + check.getY() + " " + check.getZ());
			}
			for (int i = 0; i < 6; i++) {
				BlockPos p = check.add(adjustA[i], adjustB[i], adjustC[i]);
				TileEntity t = controller.getWorld().getTileEntity(p);
				if (t == null)
					continue;
				Block b = t.getBlockType();
				if (b instanceof ICableConnector) {
					boolean good = true;
					for (BlockPos bp : checked)
						if (bp.getX() == p.getX() && bp.getY() == p.getY() && bp.getZ() == p.getZ()) {
							good = false;
							break;
						}
					if (good) {
						pos.add(p);
					}
				}
			}
		}
		return network;
	}

}
