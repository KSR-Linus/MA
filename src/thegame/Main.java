package thegame;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.shape.Box;

import thegame.util.State;
import thegame.world.World;

public class Main extends SimpleApplication {
	
	private static Main instance;
	private static World world;
	
	public static State state = State.STARTUP;
	
	
	public Main() {
		start();
	}
	
	
	
	
	@Override
	public void simpleInitApp() {
		world = World.generate();
		flyCam.setMoveSpeed(25f);
	}
	
	@Override
	public void update() {
		
		super.update();
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
