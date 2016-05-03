package thegame.world;

import java.util.HashMap;

import thegame.Main;
import thegame.block.Block;
import thegame.block.BlockJME;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class World {
	
	public HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();
	
	private Block selected;
	private Spatial marking;
	
	public World() {
		for (int x = -3; x < 3; x++) {
			for (int y = -3; y < 3; y++) {
				Chunk c = new Chunk(x, y);
				chunks.put(c.getID(), c);
			}
		}
		Chunk c = chunks.get("0:0");
		c.placeBlock(new BlockJME(), 10, 20, 10);
		Box b = new Box(.51f, .51f, .51f);
		Material m = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		m.setColor("Color", ColorRGBA.White);
		marking = new Geometry("selection" , b);
		marking.setMaterial(m);
	}
	
	public void update() {
		int cx = (int) Main.getInstance().getCamera().getLocation().x/16;
		int cy = (int) Main.getInstance().getCamera().getLocation().z/16;
		for (int x = -4; x < 4; x++) {
			for (int y = -4; y < 4; y++) {
				String id = (cx+x) + ":" + (cy+y);
				if (!chunks.containsKey(id)) {
					Chunk c = new Chunk(cx+x, cy+y);
					for (int x1 = 0; x1 < 16; x1++) {
						for (int z = 0; z < 16; z++) {
							c.placeBlock(new BlockJME(), x1, 0, z);
						}
					}
					chunks.put(id, c);
				}
			}
		}
		for (Chunk c : chunks.values()) {
			c.update();
		}
	}
	
	public void updateBlockSelection() {
		Vector3f ct = Main.getInstance().getCamera().getLocation();
		float[] c = {ct.x, ct.y, ct.z};
		Vector3f dt = Main.getInstance().getCamera().getDirection();
		float[] d = {dt.x, dt.y, dt.z};
		int[] k = new int[3];
		if (d[0] < 0) {
			k[0] = (int) Math.floor(c[0]); } else {
			k[0] = (int) Math.floor(c[0]) + 1; }
		if (d[1] < 0) {
			k[1] = (int) Math.floor(c[1]); } else {
			k[1] = (int) Math.floor(c[1]) + 1; }
		if (d[2] < 0) {
			k[2] = (int) Math.floor(c[2]); } else {
			k[2] = (int) Math.floor(c[2]) + 1; }
		float[] f = new float[3];
		if (d[0] == 0) f[0] = 6; else f[0] = (1/d[0])*(k[0] - c[0]);
		if (d[1] == 0) f[1] = 6; else f[1] = (1/d[1])*(k[1] - c[1]);
		if (d[2] == 0) f[2] = 6; else f[2] = (1/d[2])*(k[2] - c[2]);
		float fc = 0;
		int bx = 0, by = 0, bz = 0;
		while (fc < 5) {
			for (int i = 0; i < 3; i++) {
				if (f[i] == fc) {
					if (d[i] < 0) k[i]--; else k[i]++;
					if (d[i] == 0) f[i] = 6; else f[i] = (1/d[i])*(k[i] - c[i]);
				}
			}
			float fmin = f[0];
			for (int i = 1; i < 3; i++) {
				if (f[i] < fmin) {
					fmin = f[i];
				}
			}
			fc = fmin;
			Vector3f tmp = ct.add(dt.mult(fc));
			bx = (int) Math.floor(tmp.x);
			by = (int) Math.floor(tmp.y+.5);
			bz = (int) Math.floor(tmp.z);
			Chunk chunk = chunks.get(bx / 16 + ":" + bz / 16);
			selected = chunk.blocks[Math.abs(bx % 16)][Math.abs(by)][Math.abs(bz % 16)];
			if (selected != null) {
				break;
			}
		}
		if (fc < 5 && selected != null) {
			if (!Main.getInstance().getRootNode().hasChild(marking))
				Main.getInstance().getRootNode().attachChild(marking);
			marking.setLocalTranslation((float) bx, (float) by, (float) bz);
		} else {
			if (Main.getInstance().getRootNode().hasChild(marking)) {
				Main.getInstance().getRootNode().detachChild(marking);
			}
		}
	}

	public World generate() {
		World w = new World();
		for (Chunk c : chunks.values()) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					c.placeBlock(new BlockJME(), x, 0, z);
				}
			}
		}
		return w;
	}

}
