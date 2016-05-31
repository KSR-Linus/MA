package thegame.world;

import java.util.HashMap;

import com.bulletphysics.dynamics.RigidBody;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.PhysicsJoint;
import com.jme3.bullet.objects.PhysicsGhostObject;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import thegame.Main;
import thegame.block.Block;
import thegame.math.Vector2i;

public class Chunk {
	
	public Vector2i pos;
	
	public Block[][][] blocks = new Block[16][128][16];
	private Node node = new Node();
	
	private Control ctrl;
	
	
	private boolean loaded = true;
	
	public Chunk(int x, int y) {
		pos = new Vector2i(x, y);
		node.setLocalTranslation(pos.x*16, 0, pos.y*16);
	}
	
	public void init() {
		ctrl = new RigidBodyControl(CollisionShapeFactory.createMeshShape(node), 0);
	}
	
	public void placeBlockCreation(Block b, int x, int y, int z) {
		Block tmp = b.clone(x, y, z);
		blocks[x][y][z] = tmp;
		tmp.getSpatial().setLocalTranslation(x, y, -z);
		node.attachChild(tmp.getSpatial());
	}
	
	public void destroyBlock(int x, int y, int z) {
		if (blocks[x][y][z] != null) {
			blocks[x][y][z].onBlockDestroyed(x, y, z);
			node.detachChild(blocks[x][y][z].getSpatial());
			ctrl = new RigidBodyControl(CollisionShapeFactory.createMeshShape(node), 0);
			blocks[x][y][z] = null;
		}
	}
	
	public void placeBlock(Block b, int x, int y, int z) {
		Block tmp = b.clone(x, y, z);
		blocks[x][y][z] = tmp;
		tmp.getSpatial().setLocalTranslation(x, y, -z);
		node.attachChild(tmp.getSpatial());
		ctrl = new RigidBodyControl(CollisionShapeFactory.createMeshShape(node), 0);
	}
	
	public static String name(int x, int y, int z) {
		return x + ":" + y + ":" + z;
	}
	
	public void update() {
		float dx = Main.getInstance().getCamera().getLocation().x - pos.x*16;
		float dy = Main.getInstance().getCamera().getLocation().z - pos.y*16;
		loaded = Math.sqrt(dx*dx + dy*dy) < 80f;
		if (loaded) {
			if (!Main.getInstance().getRootNode().hasChild(node)) {
				Main.getInstance().getRootNode().attachChild(node);
			}
		} else {
			if (Main.getInstance().getRootNode().hasChild(node)) {
				Main.getInstance().getRootNode().detachChild(node);
			}
		}
	}
	
	
	
	public Node getNode() {
		return node;
	}
	
	public Vector2i getPos() {
		return pos;
	}

	public String getID() {
		return pos.x + ":" + pos.y;
	}
	
}
