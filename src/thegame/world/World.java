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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

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
		c.placeBlock(new BlockJME(10, 20, 10), 10, 20, 10);
		Box b = new Box(.51f, .51f, .51f);
		Material m = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/ColoredTextured.j3md");
		m.setColor("Color", new ColorRGBA(255, 255, 255, 25));
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
							c.placeBlockCreation(new BlockJME(x1, 0, z), x1, 0, z);
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
				
				if (c.blocks[tx2][y][tz2] == null) c.placeBlock(new BlockJME(tx2, y, tz2), tx2, y, tz2);
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
					c.placeBlockCreation(new BlockJME(x, 0, z), x, 0, z);
				}
			}
		}
		System.out.println("World generated");
		Main.getInstance().updatePhysics();
		return w;
	}
	
	public String getFocusedBlockCoords() {
		if (marking != null && Main.getInstance().getRootNode().hasChild(marking)) {
			return "" + marking.getWorldTranslation().x + ", " + marking.getWorldTranslation().y + ", " + marking.getWorldTranslation().z;
		} else {
			return "N/A";
		}
	}

}
