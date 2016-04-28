package thegame.world;

import thegame.Main;
import thegame.block.Block;
import thegame.math.Vector2i;

public class Chunk {
	
	public Vector2i pos;
	
	public Block[][][] blocks = new Block[16][128][16];
	
	public Chunk(int x, int y) {
		pos = new Vector2i(x, y);
	}
	
	public void destroyBlock(int x, int y, int z) {
		blocks[x][y][z].onBlockDestroyed(x, y, z);
		Main.getInstance().getRootNode().detachChild(blocks[x][y][z].getSpatial());
		blocks[x][y][z] = null;
	}
	
	public void placeBlock(Block b, int x, int y, int z) {
		blocks[x][y][z] = b.clone();
		b.getSpatial().setLocalTranslation(x, y, z);
		Main.getInstance().getRootNode().attachChild(b.getSpatial());
	}
	
	
	
	public Vector2i getPos() {
		return pos;
	}

	public String getID() {
		return pos.x + ":" + pos.y;
	}
	
}
