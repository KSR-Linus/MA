package thegame.block;

import java.util.Random;

import thegame.Main;
import thegame.item.Item;
import thegame.item.ItemJME;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class BlockJME extends Block {

	public BlockJME(int x, int y, int z) {
		super("base:blockjme", x, y, z);
		Box b = new Box(Block.cubeSize.x, Block.cubeSize.y, Block.cubeSize.z);
		s = new Geometry(ID, b);
		m = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		Random r = new Random();
		m.setColor("Color", new ColorRGBA(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1));
		s.setMaterial(m);
		rbc = new RigidBodyControl(new BoxCollisionShape(Block.cubeSize));
		rbc.setMass(0);
	}
	
	@Override
	public void onBlockDestroyed(int x, int y, int z) {
		Item i = new ItemJME();
		i.onPlaced(s.getWorldTranslation().x, s.getWorldTranslation().y, s.getWorldTranslation().z);
		Main.getInstance().addItem(i);
	}

	@Override
	public void onUpdate(int x, int y, int z) {
		
	}

	@Override
	public void onBlockPlaced(int x, int y, int z, int ax, int ay, int az) {
		
	}

	@Override
	public void inActivated(int x, int y, int z, int ax, int ay, int az) {
		
	}

	@Override
	public void onInteracted(int x, int y, int z) {
		Random r = new Random();
		m.setColor("Color", new ColorRGBA(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1));
		Main.getInstance().getRootNode().updateGeometricState();
	}

	@Override
	public Block clone(int x, int y, int z) {
		return new BlockJME(x, y, z);
	}

}
