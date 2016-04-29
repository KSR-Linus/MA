package thegame.world;

import java.util.HashMap;

import jogamp.opengl.util.pngj.chunks.ChunksList;
import thegame.Main;
import thegame.block.BlockJME;
import thegame.math.Vector2i;

public class World {
	
	public HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();
	
	
	public World() {
		for (int x = -3; x < 3; x++) {
			for (int y = -3; y < 3; y++) {
				Chunk c = new Chunk(x, y);
				chunks.put(c.getID(), c);
			}
		}
	}
	
	public void update() {
		int cx = (int) Main.getInstance().getCamera().getLocation().x/16;
		int cy = (int) Main.getInstance().getCamera().getLocation().z/16;
		boolean b = false;
		for (int x = -4; x < 4; x++) {
			for (int y = -4; y < 4; y++) {
				String id = (cx+x) + ":" + (cy+y);
				if (!chunks.containsKey(id)) {
					b = true;
					Chunk c = new Chunk(cx+x, cy+y);
					for (int x1 = 0; x1 < 16; x1++) {
						for (int z = 0; z < 16; z++) {
							c.placeBlock(new BlockJME(), x1, 0, z);
						}
					}
					chunks.put(id, c);
					System.out.println("pos: " + (int) Main.getInstance().getCamera().getLocation().x +
							", " + (int) Main.getInstance().getCamera().getLocation().z + "   chunk:" +
							c.getPos().x*16 + ", " + c.getPos().y*16);
				}
			}
		}
		if (b) System.out.println('\n');
		for (Chunk c : chunks.values()) {
			c.update();
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
