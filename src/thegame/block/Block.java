package thegame.block;

import com.jme3.scene.Spatial;

public abstract class Block {
	
	public final String ID;
	Spatial s;
	
	public Block(String id) {
		ID = id;
	}
	
	public abstract void onUpdate(int x, int y, int z);
	
	public abstract void onBlockDestroyed(int x, int y, int z);
	
	public abstract void onBlockPlaced(int x, int y, int z, int ax, int ay, int az);
	
	public abstract void inActivated(int x, int y, int z, int ax, int ay, int az);
	
	public abstract void onInteracted(int x, int y, int z);
	
	public abstract Block clone();
	
	public Spatial getSpatial() {
		return s;
	}
}
