package thegame;

import thegame.util.DeltaTime;
import thegame.util.State;
import thegame.world.World;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;

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
		getCamera().setLocation(new Vector3f(0, 5, 0));
	}
	
	@Override
	public void update() {
		super.update();
		if (System.currentTimeMillis() >= nextChunkUpdate) chunkUpdate();
		world.updateBlockSelection();
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
