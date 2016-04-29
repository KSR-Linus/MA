package thegame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.shape.Box;

import thegame.util.State;
import thegame.world.World;

public class Main extends SimpleApplication {
	
	private static Main instance;
	private static World world;
	
	public static State state = State.STARTUP;
	
	public static boolean toggle = true;
	
	
	public Main() {
		start();
	}
	
	
	
	
	@Override
	public void simpleInitApp() {
		world = new World();
		world.generate();
		flyCam.setMoveSpeed(25f);
		initKeys();
	}
	
	private void initKeys() {
	    // You can map one or several inputs to one named action
	    inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_SPACE));
	    // Add the names to the action listener.
	    inputManager.addListener(actionListener,"Pause"); 
	 
	  }
	 
	  private ActionListener actionListener = new ActionListener() {
	    public void onAction(String name, boolean keyPressed, float tpf) {
	      if (name.equals("Pause") && !keyPressed) {
	        toggle = !toggle;
	      }
	    }
	  };
	
	@Override
	public void update() {
		world.update();
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
