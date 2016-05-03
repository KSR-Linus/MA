package thegame.block;

import java.util.Random;

import thegame.Main;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class BlockJME extends Block {

	public BlockJME() {
		super("base:blockjme");
		Box b = new Box(.5f, .5f, .5f);
		s = new Geometry(ID, b);
		m = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		Random r = new Random();
		m.setColor("Color", new ColorRGBA(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1));
		s.setMaterial(m);
	}
	
	@Override
	public void onBlockDestroyed(int x, int y, int z) {
		System.out.println("destroyed at (" + x + "," + y + "," + z + ")");
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
		
	}

	@Override
	public Block clone() {
		return new BlockJME();
	}

}
