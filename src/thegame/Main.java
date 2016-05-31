package thegame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import thegame.item.Item;
import thegame.item.ItemJME;
import thegame.util.DeltaTime;
import thegame.util.State;
import thegame.world.World;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

public class Main extends SimpleApplication {

	private static Main instance;
	private static World world;
	
	ConcurrentHashMap<Item, Byte> items = new ConcurrentHashMap<Item, Byte>();
	
	private static Spatial sceneModel;
	private static CharacterControl player;
	private BulletAppState bulletAppState;
	
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();
	
	private Vector3f walkDirection = new Vector3f();
	
	private Control ctrl;

	private long nextChunkUpdate = 0;

	public static State state = State.STARTUP;

	public static boolean isRunning = true;
	
	private boolean left = false, right = false, forward = false, backward = false;
	
	Node n;
	RigidBodyControl c;

	public Main() {
		start();
	}

	@Override
	public void simpleInitApp() {
		
		flyCam.setMoveSpeed(25f);
		getCamera().setLocation(new Vector3f(0, 5, 0));
		initKeys();
		initCrossHairs();
		
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		
		world = new World();
		world.generate();
		
		
//		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(0.5f, 2f);
//	    player = new CharacterControl(capsuleShape, 0.05f);
//	    player.setJumpSpeed(20);
//	    player.setFallSpeed(30);
//	    player.setGravity(30);
//	    player.setPhysicsLocation(new Vector3f(0, 10, 0));
//		
//	    bulletAppState.getPhysicsSpace().add(player);
	}

	protected void initCrossHairs() {
		setDisplayStatView(false);
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+");
		ch.setLocalTranslation(
		settings.getWidth() / 2 - ch.getLineWidth() / 2,
		settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);
	}
	
	protected void initKeys() {
		inputManager.addMapping("leftmouse", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("rightmouse", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));

		inputManager.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("backward", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
		
		inputManager.addListener(l, "leftmouse", "rightmouse", "forward", "left", "backward", "right");
	}
	
	public void updatePhysics() {
		if (ctrl != null) {
			rootNode.removeControl(ctrl);
			ctrl = new RigidBodyControl().cloneForSpatial(rootNode);
			rootNode.addControl(ctrl);
		}
	}
	
	public void updateItems() {
		Vector3f p = cam.getLocation();
		for (Item i : items.keySet()) {
			if (Math.abs(p.x - i.x) < 1.0f && Math.abs(p.y - i.y) < 1.0f && Math.abs(p.y - i.y) < 1.0f) {
				i.onPickedUp();
				items.remove(i);
			}
			i.update();
		}
	}

	@Override
	public void update() {
		super.update();
		updateItems();
//		camDir.set(cam.getDirection()).multLocal(0.6f);
//      camLeft.set(cam.getLeft()).multLocal(0.4f);
		if (System.currentTimeMillis() >= nextChunkUpdate) chunkUpdate();
		world.updateBlockSelection2(getCamera(), rootNode);
//		System.out.println(cam.getLocation());
//		walkDirection.set(0, 0, 0);
//		if (forward) walkDirection.addLocal(camDir);
//		if (backward) walkDirection.subtractLocal(camDir);
//		if (left) walkDirection.addLocal(camLeft);
//		if (right) walkDirection.subtractLocal(camLeft);
//		player.setWalkDirection(walkDirection);
//		cam.setLocation(player.getPhysicsLocation());
	}

	ActionListener l = new ActionListener() {

		@Override
		public void onAction(String name, boolean isPressed, float arg2) {
			switch (name) {
			case "leftmouse":
				if (isPressed) world.destroySelected();
				break;
			case "rightmouse":
				if (isPressed) world.interactWithSelected();
				break;
			case "forward":
				forward = isPressed;
				break;
			case "backward":
				backward = isPressed;
				break;
			case "left":
				left = isPressed;
				break;
			case "right":
				right = isPressed;
				break;
			case "jump":
				player.jump();
				break;
			}
		}

	};
	
	public void addItem(Item i) {
		items.put(i, (byte) 0);
	}
	
	public void addPhysicsObject(RigidBodyControl ctrl) {
		rootNode.addControl(ctrl);
		bulletAppState.getPhysicsSpace().add(ctrl);
	}

	public void removePhysicsObject(RigidBodyControl ctrl) {
		rootNode.removeControl(ctrl);
	}
	
	public void addPhysicsGhost(GhostControl ctrl) {
		bulletAppState.getPhysicsSpace().addCollisionObject(ctrl);
	}
	
	public void removePhysicsGhost(GhostControl ctrl) {
		bulletAppState.getPhysicsSpace().removeCollisionObject(ctrl);
	}
	
	
	public PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}
	

	private void chunkUpdate() {
		nextChunkUpdate = DeltaTime.getTargetTime(500);
		world.update();
	}

	public static void main(String[] args) {
		instance = new Main();
	}

	public static Main getInstance() {
		return instance;
	}

	public static World getWorld() {
		return world;
	}
}
