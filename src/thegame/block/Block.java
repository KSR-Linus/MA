package thegame.block;

import thegame.Main;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public abstract class Block {
	
	public final String ID;
	public String NAME;
	public static final Vector3f cubeSize = new Vector3f(.5f, .5f, .5f);
	Spatial s;
	Material m;
	RigidBodyControl rbc;
	
	public Block() {
		ID = this.getClass().getAnnotation(BlockReg.class).ID();
		Main.blockReg.put(ID, this);
	}
	
	public abstract void onUpdate(int x, int y, int z);
	
	public abstract void onBlockDestroyed(int x, int y, int z);
	
	public void onBlockPlaced(int x, int y, int z, int ax, int ay, int az) {
		NAME = x + ":" + y + ":" + z;
	}
	
	public abstract void inActivated(int x, int y, int z, int ax, int ay, int az);
	
	public abstract void onInteracted(int x, int y, int z);
	
	public abstract Block clone();
	
	public Spatial getSpatial() {
		return s;
	}
	
	public boolean isInteractable() {
		return false;
	}
	
	public RigidBodyControl getRigidBodyControl() {
		return rbc;
	}
	
	public String toString() {
		return ID + "::" + NAME;
	}
}
