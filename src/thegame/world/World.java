package thegame.world;

import java.util.HashMap;

import jogamp.opengl.util.pngj.chunks.ChunksList;
import thegame.block.BlockJME;
import thegame.math.Vector2i;

public class World {
	
	public HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();
	
	
	public World() {
		for (int x = -10; x < 10; x++) {
			for (int y = -10; y < 10; y++) {
				Chunk c = new Chunk(x, y);
				chunks.put(c.getID(), c);
			}
		}
	}
	
	
	
	public void update() {
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
