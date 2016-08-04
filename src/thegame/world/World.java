package thegame.world;

import java.util.HashMap;

import thegame.Main;
import thegame.block.Block;
import thegame.block.BlockJME;

import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;

public class World {
	
	public HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();
	
	private Chunk chunk;
	private Block selected;
	private Vector3f contact = new Vector3f();
	
	private int x = 0, y = 0, z = 0;
	
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
		
		Mesh mesh = new Mesh();
		mesh.setBuffer(VertexBuffer.Type.Position, 3, new float[] {
				 .5f,  .5f,  .5f, //0
				-.5f,  .5f,  .5f, //1
				 .5f, -.5f,  .5f, //2
				-.5f, -.5f,  .5f, //3
				 .5f,  .5f, -.5f, //4
				-.5f,  .5f, -.5f, //5
				 .5f, -.5f, -.5f, //6
				-.5f, -.5f, -.5f, //7
		});
		
		mesh.setBuffer(VertexBuffer.Type.Index, 2, new short[] {
				 0, 1,
				 0, 2,
				 0, 4,
				 1, 5,
				 1, 3,
				 5, 7,
				 5, 4,
				 4, 6,
				 3, 2,
				 7, 3,
				 6, 7,
				 2, 6,
		});
		
		mesh.setMode(Mesh.Mode.Lines);
		
		Material m = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		m.setColor("Color", new ColorRGBA(0, 0, 0, 1));
		marking = new Geometry("selection" , mesh);
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
							c.placeBlockCreation(Main.blockReg.get("base:block_jme"), x1, 0, z);
						}
					}
					Main.getInstance().updatePhysics();
					chunks.put(id, c);
				}
			}
		}
		for (Chunk c : chunks.values()) {
			c.update();
		}
	}
	
	public void updateBlockSelection2(Camera c, Node root) {
		CollisionResults res = new CollisionResults();
		Ray r = new Ray(c.getLocation(), c.getDirection());
		root.collideWith(r, res);
		if (res.size() > 0) {
			contact = res.getClosestCollision().getContactPoint();
			x = (int) Math.floor(res.getClosestCollision().getGeometry().getWorldTranslation().x);
			y = (int) Math.floor(res.getClosestCollision().getGeometry().getWorldTranslation().y);
			z = (int) Math.floor(res.getClosestCollision().getGeometry().getWorldTranslation().z);
			if (x <= 0) x -= 15;
			if (z >= 0) z += 15;
			chunk = chunks.get(x / 16 + ":" + z / 16);
			selected = chunk.blocks[Math.abs(x%16)][Math.abs(y)][Math.abs(z%16)];
			if (!root.hasChild(marking) && c.getLocation().distance(res.getClosestCollision().getContactPoint()) < 5 && selected instanceof Block) {
				root.attachChild(marking);
			}
			if (root.hasChild(marking) && c.getLocation().distance(res.getClosestCollision().getContactPoint()) > 5) {
				root.detachChild(marking);
				selected = null;
			}
			marking.setLocalTranslation(res.getClosestCollision().getGeometry().getWorldTranslation());
		} else {
			if (root.hasChild(marking)) {
				root.detachChild(marking);
				selected = null;
			}
		}
		//if (selected != null)System.out.println(selected.ID);
	}
	
	
	public void destroySelected() {
		int tx = Math.abs(x%16), tz = Math.abs(z%16);
		
		if (x < 0) tx = 15 - tx;
		if (z > 0) tz = 15 - tz;
		
		if (selected != null) {
			chunk.destroyBlock(tx, Math.abs(y), tz);
			marking.removeFromParent();
		//	Main.getInstance().updatePhysics();
			System.out.println(selected.toString());
		}
	}
	
	public void interactWithSelected() {
		int tx = Math.abs(x%16), tz = Math.abs(z%16);
		
		if (x < 0) tx = 15 - tx;
		if (z > 0) tz = 15 - tz;
		if (selected != null) {
			if (selected.isInteractable()) {
				selected.onInteracted(Math.abs(tx%16), Math.abs(y), Math.abs(tz%16));
			} else {
				int x = Math.round(contact.x), y = Math.round(contact.y), z = Math.round(contact.z);
				if (x <= 0) x -= 15;
				if (z >= 0) z += 15;
				Chunk c = chunks.get(x / 16 + ":" + z / 16);
				int tx2 = x % 16;
				int tz2 = z % 16;

				if (tx2 < 0) tx2 = 15 + tx2;
				if (tz2 > 0) tz2 = 15 - tz2;
				
				if (c.blocks[tx2][y][tz2] == null) c.placeBlock(new BlockJME(), tx2, y, tz2);
				System.out.println(x + ":" + y + ":" + z);
			}
		}
	}

	public World generate() {
		World w = new World();
		System.out.println("Initialtinng world generation");
		for (Chunk c : chunks.values()) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					c.placeBlockCreation(Main.blockReg.get("base:block_jme"), x, 0, z);
				}
			}
		}
		System.out.println("World generated");
		Main.getInstance().updatePhysics();
		return w;
	}
	
	public void placeBlockAt(int x, int y, int z, Block b) {
		int cx = x/16;
		int cy = z/16;
		chunks.get("0:0").placeBlock(b, x, y, z);
	}
	
	public void destroyAt(int x, int y, int z) {
		if (x <= 0) x -= 15;
		if (z >= 0) z += 15;
		Chunk chunk = chunks.get(x / 16 + ":" + z / 16);
		
		int tx = Math.abs(x%16), tz = Math.abs(z%16);
		
		if (selected != null) {
			chunk.destroyBlock(tx, Math.abs(y), tz);
			marking.removeFromParent();
		//	Main.getInstance().updatePhysics();
			System.out.println(selected.toString());
		}
	}
	
	public String getFocusedBlockCoords() {
		if (marking != null && Main.getInstance().getRootNode().hasChild(marking)) {
			return "" + marking.getWorldTranslation().x + ", " + marking.getWorldTranslation().y + ", " + marking.getWorldTranslation().z;
		} else {
			return "N/A";
		}
	}

}
