package thegame.block;

import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Block {
	
	public final String ID;
	public final String NAME;
	Spatial s;
	Material m;
	
	public Block(String id, int x, int y, int z) {
		ID = id;
		NAME = x + ":" + y + ":" + z;
	}
	
	public abstract void onUpdate(int x, int y, int z);
	
	public abstract void onBlockDestroyed(int x, int y, int z);
	
	public abstract void onBlockPlaced(int x, int y, int z, int ax, int ay, int az);
	
	public abstract void inActivated(int x, int y, int z, int ax, int ay, int az);
	
	public abstract void onInteracted(int x, int y, int z);
	
	public abstract Block clone(int x, int y, int z);
	
	public Spatial getSpatial() {
		return s;
	}
}
