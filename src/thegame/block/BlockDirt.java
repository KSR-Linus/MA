package thegame.block;

import java.util.Random;

import thegame.Main;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

@BlockReg(ID = "base:block_dirt")
public class BlockDirt extends Block {

	public BlockDirt() {
		Box b = new Box(Block.cubeSize.x, Block.cubeSize.y, Block.cubeSize.z);
		s = new Geometry(ID, b);
		m = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		m.setColor("Color", new ColorRGBA(1, 1, 1, 0.2f));
		s.setMaterial(m);
		rbc = new RigidBodyControl(new BoxCollisionShape(Block.cubeSize));
		rbc.setMass(0);
	}

	@Override
	public void onUpdate(int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBlockDestroyed(int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inActivated(int x, int y, int z, int ax, int ay, int az) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInteracted(int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public Block clone() {
		return new BlockDirt();
	}

}
