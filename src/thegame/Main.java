package thegame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.shape.Box;

import thegame.util.DeltaTime;
import thegame.util.State;
import thegame.world.World;

public class Main extends SimpleApplication {
	
	private static Main instance;
	private static World world;
	
	private long nextChunkUpdate = 0;
	
	public static State state = State.STARTUP;
	
	public static boolean isRunning = true;
	
	
	public Main() {
		start();
	}
	
	
	
	
	@Override
	public void simpleInitApp() {
		world = new World();
		world.generate();
		flyCam.setMoveSpeed(25f);
	}
	
	@Override
	public void update() {
		super.update();
		if (System.currentTimeMillis() >= nextChunkUpdate) chunkUpdate();
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
