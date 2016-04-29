package thegame.world;

import com.jme3.scene.Node;

import thegame.Main;
import thegame.block.Block;
import thegame.math.Vector2i;

public class Chunk {
	
	public Vector2i pos;
	
	public Block[][][] blocks = new Block[16][128][16];
	private Node node = new Node();
	
	public Chunk(int x, int y) {
		pos = new Vector2i(x, y);
		node.setLocalTranslation(pos.x*16, 0, pos.y*16);
	}
	
	public void destroyBlock(int x, int y, int z) {
		blocks[x][y][z].onBlockDestroyed(x, y, z);
		Main.getInstance().getRootNode().detachChild(blocks[x][y][z].getSpatial());
		blocks[x][y][z] = null;
	}
	
	public void placeBlock(Block b, int x, int y, int z) {
		blocks[x][y][z] = b.clone();
		b.getSpatial().setLocalTranslation(x, y, z);
		node.attachChild(b.getSpatial());
	}
	
	public void update() {
		float dx = Main.getInstance().getCamera().getLocation().x - pos.x*16;
		float dy = Main.getInstance().getCamera().getLocation().z - pos.y*16;
		if (Math.sqrt(dx*dx + dy*dy) < 80f) {
			if (!Main.getInstance().getRootNode().hasChild(node)) {
				Main.getInstance().getRootNode().attachChild(node);
			}
		} else {
			if (Main.getInstance().getRootNode().hasChild(node)) {
				Main.getInstance().getRootNode().detachChild(node);
			}
		}
	}
	
	public Vector2i getPos() {
		return pos;
	}

	public String getID() {
		return pos.x + ":" + pos.y;
	}
	
}
