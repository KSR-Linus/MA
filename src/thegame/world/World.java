package thegame.world;

import java.util.HashMap;

import thegame.block.BlockJME;
import thegame.math.Vector2i;

public class World {
	
	public HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();
	
	
	public World() {
		Chunk c = new Chunk(0, 0);
		chunks.put(c.getID(), c);
	}
	
	
	

	public static World generate() {
		World w = new World();
		Chunk c = w.chunks.get("0:0");
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				c.placeBlock(new BlockJME(), x, 0, z);
			}
		}
		return w;
	}

}
